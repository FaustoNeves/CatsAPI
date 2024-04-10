package com.fausto.breeddetails.base.viewmodel.base_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.breeddetails.base.tracking.base.trackScreenView
import com.fausto.breeddetails.base.viewmodel.base_info.interact.BreedDetailInteract
import com.fausto.breeddetails.base.viewmodel.base_info.viewstate.BreedDetailViewState
import com.fausto.common.result.ResultWrapper
import com.fausto.common.result.getResult
import com.fausto.datastore.querybreed.BreedIdsManager
import com.fausto.domain.usecase.GetBreedByIdUseCase
import com.fausto.tracking.analytics.Analytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BreedDetailViewModel @Inject constructor(
    private val getBreedByIdUseCase: GetBreedByIdUseCase,
    private val breedIdsManager: BreedIdsManager,
    val analytics: Analytics
) : ViewModel() {

    private val _breedDetailViewState = MutableLiveData<BreedDetailViewState>()
    val breedDetailViewState: LiveData<BreedDetailViewState> get() = _breedDetailViewState

    fun interpret(interaction: BreedDetailInteract) {
        when (interaction) {
            is BreedDetailInteract.BaseViewCreated -> getReferenceImageId()
            is BreedDetailInteract.BaseHandleDeeplink -> getBreedDetail(interaction.breedQueryId)
            is BreedDetailInteract.BaseOnErrorAction -> getReferenceImageId()
        }
    }

    private fun getBreedDetail(breedId: String) {
        viewModelScope.launch {
            trackScreenView()
            when (val response = getResult {
                getBreedByIdUseCase.getBreedById(breedId)
            }) {
                is ResultWrapper.Success -> {
                    _breedDetailViewState.value = BreedDetailViewState.Success(response.data)
                }
                is ResultWrapper.Error -> _breedDetailViewState.value =
                    BreedDetailViewState.Error(response.exception?.message.toString())
            }
        }
    }

    private fun getReferenceImageId() {
        viewModelScope.launch {
            _breedDetailViewState.value = BreedDetailViewState.Loading
            breedIdsManager.getReferenceImageId().catch { exception ->
                BreedDetailViewState.Error(exception.message.toString())
            }.collect { referenceImageid ->
                referenceImageid?.let { getBreedDetail(it) }
            }
        }
    }
}
package com.fausto.breeddetails.viewmodel.base_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.breeddetails.tracking.base_info.trackScreenView
import com.fausto.breeddetails.viewmodel.base_info.viewstate.BreedDetailViewState
import com.fausto.common.result.ResultWrapper
import com.fausto.datastore.querybreed.BreedIdsManager
import com.fausto.domain.usecase.GetBreedByIdUseCase
import com.fausto.tracking.analytics.Analytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(
    private val getBreedByIdUseCase: GetBreedByIdUseCase,
    private val breedIdsManager: BreedIdsManager,
    val analytics: Analytics
) : ViewModel() {

    private val _breedDetailViewState = MutableLiveData<BreedDetailViewState>()
    val breedDetailViewState: LiveData<BreedDetailViewState> get() = _breedDetailViewState

    init {
        _breedDetailViewState.value = BreedDetailViewState.Loading
    }

    fun getBreedDetail(breedId: String) {
        trackScreenView()
        viewModelScope.launch {
            _breedDetailViewState.value = BreedDetailViewState.InitialState
            when (val response = getBreedByIdUseCase.getBreedById(breedId)) {
                is ResultWrapper.Success -> {
                    _breedDetailViewState.value = BreedDetailViewState.Success(response.data)
                }

                is ResultWrapper.Error -> {
                    _breedDetailViewState.value =
                        BreedDetailViewState.Error(response.exception?.message.toString())
                }
            }
        }
    }
}
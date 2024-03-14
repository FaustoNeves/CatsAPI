package com.fausto.cats.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.cats.domain.usecase.GetBreedByIdUseCase
import com.fausto.common.result.ResultWrapper
import com.fausto.common.result.getResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BreedDetailViewModel @Inject constructor(private val getBreedByIdUseCase: GetBreedByIdUseCase) :
    ViewModel() {

    private val _breedDetailViewState = MutableLiveData<BreedDetailViewState>()
    val breedDetailViewState: LiveData<BreedDetailViewState> get() = _breedDetailViewState

    fun interpret(interaction: BreedDetailInteract) {
        when (interaction) {
            is BreedDetailInteract.ViewCreated -> getBreedDetail(interaction.breedId)
            is BreedDetailInteract.OnErrorAction -> getBreedDetail(interaction.breedId)
        }
    }

    private fun getBreedDetail(breedId: String) {
        viewModelScope.launch {
            _breedDetailViewState.value = BreedDetailViewState.Loading
            when (val response = getResult {
                getBreedByIdUseCase.getBreedById(breedId)
            }) {
                is ResultWrapper.Success -> _breedDetailViewState.value =
                    BreedDetailViewState.Success(response.data)

                is ResultWrapper.Error -> _breedDetailViewState.value =
                    BreedDetailViewState.Error(response.exception?.message.toString())
            }
        }
    }
}
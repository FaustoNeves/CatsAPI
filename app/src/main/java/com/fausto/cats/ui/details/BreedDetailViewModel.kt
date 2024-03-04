package com.fausto.cats.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.cats.domain.usecase.GetBreedByIdUseCase
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
        }
    }

    private fun getBreedDetail(breedId: String) {
        viewModelScope.launch {
            try {
                val response = getBreedByIdUseCase.getBreedById(breedId)
                _breedDetailViewState.value = BreedDetailViewState.Success(response)
            } catch (e: Exception) {
                Log.e("exception", e.message.toString())
                _breedDetailViewState.value = BreedDetailViewState.Error(e.message.toString())
            }
        }
    }
}
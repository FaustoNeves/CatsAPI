package com.fausto.cats.ui.breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.cats.domain.model.SectionModel
import com.fausto.cats.domain.usecase.GetBreedsBySearchUseCase
import com.fausto.cats.domain.usecase.GetBreedsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BreedsViewModel @Inject constructor(
    private val getBreedsUseCase: GetBreedsUseCase,
    private val getBreedBySearchUseCase: GetBreedsBySearchUseCase,
) : ViewModel() {

    private val _breedsViewState = MutableLiveData<BreedsViewState>()
    val breedsViewState: LiveData<BreedsViewState> get() = _breedsViewState

    fun interpret(interaction: BreedsInteract) {
        when (interaction) {
            is BreedsInteract.ViewCreated -> getBreeds()
            is BreedsInteract.OnRefreshAction -> getBreeds()
            is BreedsInteract.OnErrorAction -> getBreeds()
            is BreedsInteract.OnSearchBreedAction -> getBreedsBySearch(interaction.breedQuery)
        }
    }

    private fun getBreeds() {
        _breedsViewState.value = BreedsViewState.Loading
        viewModelScope.launch {
            try {
                val response = getBreedsUseCase.getBreeds()
                val sectionModelsList = SectionModel(breedsList = response).buildSections(response)
                _breedsViewState.value = BreedsViewState.Success(sectionModelsList)
            } catch (e: Exception) {
                _breedsViewState.value = BreedsViewState.Error("An error occurred: ${e.message}")
            }
        }
    }

    private fun getBreedsBySearch(breedQuery: String) {
        _breedsViewState.value = BreedsViewState.Loading
        viewModelScope.launch {
            try {
                val response = getBreedBySearchUseCase.getBreedsBySearch(breedQuery)
                val sectionModelsList = SectionModel(breedsList = response).buildSections(response)
                _breedsViewState.value = BreedsViewState.Success(sectionModelsList)
            } catch (e: Exception) {
                _breedsViewState.value = BreedsViewState.Error("An error occurred: ${e.message}")
            }
        }
    }
}
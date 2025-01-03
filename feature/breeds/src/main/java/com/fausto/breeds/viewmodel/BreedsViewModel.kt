package com.fausto.breeds.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.breeds.tracking.trackScreenView
import com.fausto.breeds.tracking.trackSelectedItem
import com.fausto.breeds.viewmodel.interact.BreedsInteract
import com.fausto.breeds.viewmodel.viewstate.BreedsViewState
import com.fausto.common.result.ResultWrapper
import com.fausto.datastore.querybreed.BreedIdsManager
import com.fausto.domain.usecase.GetBreedsBySearchUseCase
import com.fausto.domain.usecase.GetBreedsUseCase
import com.fausto.model.SectionModel
import com.fausto.tracking.analytics.Analytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BreedsViewModel @Inject constructor(
    private val getBreedsUseCase: GetBreedsUseCase,
    private val getBreedBySearchUseCase: GetBreedsBySearchUseCase,
    private val breedIdsManager: BreedIdsManager,
    val analytics: Analytics
) : ViewModel() {

    private val _breedsViewState = MutableLiveData<BreedsViewState>()
    val breedsViewState: LiveData<BreedsViewState> get() = _breedsViewState

    fun interpret(interaction: BreedsInteract) {
        when (interaction) {
            is BreedsInteract.ViewCreated -> getBreeds()
            is BreedsInteract.OnRefreshAction -> getBreeds()
            is BreedsInteract.OnErrorAction -> getBreeds()
            is BreedsInteract.OnSearchBreedAction -> getBreedsBySearch(interaction.breedQuery)
            is BreedsInteract.OnBreedClickAction -> saveReferenceImageId(
                interaction.referenceImageId, interaction.queryBreedId
            )
        }
    }

    private fun getBreeds() {
        trackScreenView()
        viewModelScope.launch {
            _breedsViewState.value = BreedsViewState.Loading
            when (val response = getBreedsUseCase.getBreeds()) {
                is ResultWrapper.Success -> {
                    val sectionModelsList =
                        SectionModel(breedsList = response.data).buildSections()
                    _breedsViewState.value = BreedsViewState.Success(sectionModelsList)
                }

                is ResultWrapper.Error -> {
                    _breedsViewState.value =
                        BreedsViewState.Error(response.exception?.message.toString())
                }
            }
        }
    }

    private fun getBreedsBySearch(breedQuery: String) {
        viewModelScope.launch {
            _breedsViewState.value = BreedsViewState.Loading
            when (val response = getBreedBySearchUseCase.getBreedsBySearch(breedQuery)) {
                is ResultWrapper.Success -> {
                    val sectionModelsList =
                        SectionModel(breedsList = response.data).buildSections(breedQuery)
                    _breedsViewState.value = BreedsViewState.Success(sectionModelsList)
                }

                is ResultWrapper.Error -> _breedsViewState.value =
                    BreedsViewState.Error(response.exception?.message.toString())
            }
        }
    }

    private fun saveReferenceImageId(referenceImageId: String, queryBreedId: String) {
        trackSelectedItem(queryBreedId)
        viewModelScope.launch(Dispatchers.IO) {
            when (val result =
                breedIdsManager.saveReferenceImageId(referenceImageId, queryBreedId)) {
                is ResultWrapper.Success -> {
                    _breedsViewState.postValue(BreedsViewState.SaveReferenceImageIdSuccess)
                }

                is ResultWrapper.Error -> {
                    BreedsViewState.Error(result.exception?.message.toString())
                }
            }
        }
    }
}
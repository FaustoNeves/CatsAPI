package com.fausto.breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.common.result.ResultWrapper
import com.fausto.common.result.getResult
import com.fausto.datastore.querybreed.QueryBreedIdManager
import com.fausto.domain.usecase.GetBreedsBySearchUseCase
import com.fausto.domain.usecase.GetBreedsUseCase
import com.fausto.model.SectionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BreedsViewModel @Inject constructor(
    private val getBreedsUseCase: GetBreedsUseCase,
    private val getBreedBySearchUseCase: GetBreedsBySearchUseCase,
    private val queryBreedIdManager: QueryBreedIdManager,
) : ViewModel() {

    private val _breedsViewState = MutableLiveData<BreedsViewState>()
    val breedsViewState: LiveData<BreedsViewState> get() = _breedsViewState

    fun interpret(interaction: BreedsInteract) {
        when (interaction) {
            is BreedsInteract.ViewCreated -> getBreeds()
            is BreedsInteract.OnRefreshAction -> getBreeds()
            is BreedsInteract.OnErrorAction -> getBreeds()
            is BreedsInteract.OnSearchBreedAction -> getBreedsBySearch(interaction.breedQuery)
            is BreedsInteract.OnBreedClickAction -> saveBreedQueryId(interaction.breedQueryId)
        }
    }

    private fun getBreeds() {
        viewModelScope.launch {
            _breedsViewState.value = BreedsViewState.Loading
            when (val response = getResult { getBreedsUseCase.getBreeds() }) {
                is ResultWrapper.Success -> {
                    val sectionModelsList =
                        SectionModel(breedsList = response.data).buildSections(response.data)
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
            when (val response =
                getResult { getBreedBySearchUseCase.getBreedsBySearch(breedQuery) }) {
                is ResultWrapper.Success -> {
                    val sectionModelsList =
                        SectionModel(breedsList = response.data).buildSections(response.data)
                    _breedsViewState.value = BreedsViewState.Success(sectionModelsList)
                }

                is ResultWrapper.Error -> _breedsViewState.value =
                    BreedsViewState.Error(response.exception?.message.toString())
            }
        }
    }

    private fun saveBreedQueryId(breedQueryId: String) {
        viewModelScope.launch {
            queryBreedIdManager.saveQueryBreedId(breedQueryId)
        }
//            .invokeOnCompletion {
//
//        }
    }
}
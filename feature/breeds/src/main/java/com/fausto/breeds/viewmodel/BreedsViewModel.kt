package com.fausto.breeds.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fausto.breeds.viewmodel.viewstate.BreedsViewState
import com.fausto.common.result.ResultWrapper
import com.fausto.domain.usecase.GetBreedsBySearchUseCase
import com.fausto.domain.usecase.GetBreedsUseCase
import com.fausto.model.SectionModel
import com.fausto.tracking.analytics.Analytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val getBreedsUseCase: GetBreedsUseCase,
    private val getBreedBySearchUseCase: GetBreedsBySearchUseCase,
    val analytics: Analytics
) : ViewModel() {

    var userInput by mutableStateOf("")
        private set

    fun updateUserInput(input: String) {
        userInput = input
    }

    private var searchBreedJob: Job? = null

    private val _breedsViewState = MutableLiveData<BreedsViewState>()
    val breedsViewState: LiveData<BreedsViewState> get() = _breedsViewState

    init {
        getBreedsWithinCoroutines()
    }

    private fun getBreedsWithinCoroutines() {
        viewModelScope.launch {
            _breedsViewState.value = BreedsViewState.Loading
            when (val response = getBreedsUseCase.getBreedsWithinCoroutines()) {
                is ResultWrapper.Success -> {
                    val sectionModelsList =
                        SectionModel(breedsList = response.data).buildAllSections()
                    _breedsViewState.value = BreedsViewState.Success(sectionModelsList)
                }

                is ResultWrapper.Error -> {
                    _breedsViewState.value =
                        BreedsViewState.Error(response.exception?.message.toString())
                }
            }
        }
    }

    internal fun getBreeds() {
        if (searchBreedJob?.isActive == true) searchBreedJob?.cancel()
        searchBreedJob = viewModelScope.launch {
            _breedsViewState.value = BreedsViewState.Loading
            if (userInput.isNotBlank()) {
                when (val response = getBreedBySearchUseCase.getBreedsBySearch(userInput)) {
                    is ResultWrapper.Success -> {
                        if (response.data.isNotEmpty()) {
                            val sectionModelsList =
                                SectionModel(breedsList = response.data).buildAllSections(
                                    userInput
                                )
                            _breedsViewState.value = BreedsViewState.Success(sectionModelsList)
                        } else {
                            _breedsViewState.value = BreedsViewState.NoResultsFound
                        }
                    }

                    is ResultWrapper.Error -> _breedsViewState.value =
                        BreedsViewState.Error(response.exception?.message.toString())
                }
            } else {
                getBreedsWithinCoroutines()
            }
        }
    }

    private var _breedFlowViewState: MutableStateFlow<Int> = MutableStateFlow(0)
    var breedFlowViewState: StateFlow<BreedsViewState> = _breedFlowViewState.flatMapLatest {
        flow {
            emit(BreedsViewState.Loading)
            runCatching {
                getBreedsUseCase.getStateFlowBreeds()
            }.onSuccess { response ->
                when (response) {
                    is ResultWrapper.Success -> {
                        val sectionModelsList =
                            SectionModel(breedsList = response.data).buildAllSections()
                        emit(BreedsViewState.Success(sectionModelsList))

                    }

                    is ResultWrapper.Error -> {
                        emit(BreedsViewState.Loading)
                        emit(BreedsViewState.Error(response.exception?.message.toString()))
                    }
                }
            }.onFailure { exception ->
                emit(BreedsViewState.Loading)
                emit(BreedsViewState.Error(exception.message.toString()))
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = BreedsViewState.Loading
    )
}
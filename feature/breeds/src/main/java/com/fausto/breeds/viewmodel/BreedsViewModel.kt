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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(
    private val getBreedsUseCase: GetBreedsUseCase,
    private val getBreedBySearchUseCase: GetBreedsBySearchUseCase,
    val analytics: Analytics
) : ViewModel() {

    private val _breedsViewState = MutableLiveData<BreedsViewState>()
    val breedsViewState: LiveData<BreedsViewState> get() = _breedsViewState
    private val retryAction = MutableStateFlow(false)

    lateinit var breedFlowViewState: SharedFlow<BreedsViewState>

    init {
        viewModelScope.launch {
            breedFlowViewState = flow {
                emit(BreedsViewState.Loading)
                when (val response = getBreedsUseCase.getStateFlowBreeds()) {
                    is ResultWrapper.Error -> {
                        emit(BreedsViewState.Error(response.exception?.message.toString()))
                    }

                    is ResultWrapper.Success -> {
                        val sectionModelsList =
                            SectionModel(breedsList = response.data).buildSections()
                        emit(BreedsViewState.Success(sectionModelsList))
                    }
                }
            }.retryWhen { cause, attempt ->
                retryAction.value
            }.catch { exception ->
                emit(BreedsViewState.Error(exception.message.toString()))
            }.shareIn(
                scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_0000)
            )
        }
    }
//    var breedFlowViewState: SharedFlow<BreedsViewState> =
//        getBreedsUseCase.getStateFlowBreeds().flatMapConcat { result ->
//            when (result) {
//                is ResultWrapper.Success -> {
//                    val sectionModelsList =
//                        SectionModel(breedsList = result.data).buildSections()
//                    flowOf(BreedsViewState.Success(sectionModelsList))
//                }
//
//                is ResultWrapper.Error -> {
//                    flowOf(BreedsViewState.Error(result.exception?.message.toString()))
//                }
//            }
//        }
//            .retryWhen { cause, attempt ->
//                retryAction.value
//            }
//            .catch { exception ->
//                emit(BreedsViewState.Error(exception.message ?: "Null"))
//            }
//            .shareIn(
//                scope = viewModelScope,
//                started = SharingStarted.WhileSubscribed(5_000),
//            )

    var userInput by mutableStateOf("")
        private set

    fun updateUserInput(input: String) {
        userInput = input
    }

//    init {
//        getStateFlowBreeds()
//    }

    internal fun getStateFlowBreeds() {
        retryAction.value = true
        /**
         * Não utilizar live data para este escopo. O live data, muito provavelmente, é o que está causando o bug de estado
         * */
//        trackScreenView()
//        viewModelScope.launch {
//            _breedsViewState.value = BreedsViewState.Loading
//            val response = getBreedsUseCase.getStateFlowBreeds()
//            response.catch { exception ->
//                _breedsViewState.value = BreedsViewState.Error(exception.message.toString())
//            }.map { result ->
//                when (result) {
//                    is ResultWrapper.Success -> {
//                        val sectionModelsList =
//                            SectionModel(breedsList = result.data).buildSections()
//                        _breedsViewState.value = BreedsViewState.Success(sectionModelsList)
//                    }
//
//                    is ResultWrapper.Error -> {
//                        _breedsViewState.value =
//                            BreedsViewState.Error(result.exception?.message.toString())
//                    }
//                }
//            }.collect { result ->
//                Log.e("result", "result: ")
//
//            }
//        }
    }

    internal fun getBreedsWithinCoroutines() {
        viewModelScope.launch {
            _breedsViewState.value = BreedsViewState.Loading
            when (val response = getBreedsUseCase.getBreedsWithinCoroutines()) {
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

    internal fun getBreedsBySearch(breeQuery: String) {
        viewModelScope.launch {
            _breedsViewState.value = BreedsViewState.Loading
            when (val response = getBreedBySearchUseCase.getBreedsBySearch(breeQuery)) {
                is ResultWrapper.Success -> {
                    val sectionModelsList =
                        SectionModel(breedsList = response.data).buildSections(breeQuery)
                    _breedsViewState.value = BreedsViewState.Success(sectionModelsList)
                }

                is ResultWrapper.Error -> _breedsViewState.value =
                    BreedsViewState.Error(response.exception?.message.toString())
            }
        }
    }
}
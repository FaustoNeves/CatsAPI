package com.fausto.breeds.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fausto.breeds.viewmodel.interact.BreedsInteract
import com.fausto.breeds.viewmodel.viewstate.BreedsViewState
import com.fausto.common.result.ResultWrapper
import com.fausto.datastore.querybreed.BreedIdsManagerImpl
import com.fausto.domain.usecase.GetBreedsBySearchUseCase
import com.fausto.domain.usecase.GetBreedsUseCase
import com.fausto.model.BreedsModel
import com.fausto.model.SectionModel
import com.fausto.tracking.analytics.Analytics
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
internal class BreedsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var breedsViewModel: BreedsViewModel

    @MockK
    private lateinit var getBreedsUseCase: GetBreedsUseCase

    @MockK
    private lateinit var getBreedBySearchUseCase: GetBreedsBySearchUseCase

    @MockK
    private lateinit var breedIdsManager: BreedIdsManagerImpl

    @RelaxedMockK
    private lateinit var analytics: Analytics

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        breedsViewModel =
            BreedsViewModel(getBreedsUseCase, getBreedBySearchUseCase, breedIdsManager, analytics)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getBreeds success`() {
        val breedModelMock1 = BreedsModel("A", "a", "a", "a")
        val breedsModelListMock = listOf(breedModelMock1)
        coEvery { getBreedsUseCase.getBreeds() } returns ResultWrapper.Success(breedsModelListMock)

        breedsViewModel.interpret(BreedsInteract.ViewCreated)

        val sectionMock = "A"
        val sectionsListMock = breedsModelListMock.map {
            SectionModel(
                sectionMock, listOf(breedModelMock1)
            )
        }
        val expectedViewState = BreedsViewState.Success(sectionsListMock)
        assertEquals(breedsViewModel.breedsViewState.value, expectedViewState)
    }

    @Test
    fun `test getBreeds error`() {
        val errorMessage = "Failed to retrieve breeds"
        val errorResult = ResultWrapper.Error(Exception(errorMessage))
        coEvery { getBreedsUseCase.getBreeds() } returns errorResult

        breedsViewModel.interpret(BreedsInteract.ViewCreated)

        val expectedViewState = BreedsViewState.Error(errorMessage)
        assertEquals(expectedViewState, breedsViewModel.breedsViewState.value)
    }

    @Test
    fun `test searchBreeds success`() {
        val breedQuery = "abys"
        val searchedBreedModelMock = BreedsModel("A", "abys", "referenceImageId", "name")
        val searchedBreedsModelMockList = listOf(searchedBreedModelMock)
        val sectionMock = "A"
        coEvery { getBreedBySearchUseCase.getBreedsBySearch(breedQuery) } returns ResultWrapper.Success(
            searchedBreedsModelMockList
        )
        breedsViewModel.interpret(BreedsInteract.OnSearchBreedAction(breedQuery))
        val sectionsListMock = searchedBreedsModelMockList.map {
            SectionModel(sectionMock, listOf(it))
        }
        val expectedViewState = BreedsViewState.Success(sectionsListMock)
        assertEquals(expectedViewState, breedsViewModel.breedsViewState.value)
    }

    @Test
    fun `test searchBreeds error`() {
        val breedQuery = "abys"
        val errorMessage = "Failed to retrieve breeds"
        val errorResult = ResultWrapper.Error(Exception(errorMessage))
        coEvery { getBreedBySearchUseCase.getBreedsBySearch(breedQuery) } returns errorResult
        breedsViewModel.interpret(BreedsInteract.OnSearchBreedAction(breedQuery))
        val expectedViewState = BreedsViewState.Error(errorMessage)
        assertEquals(expectedViewState, breedsViewModel.breedsViewState.value)
    }

    @Test
    fun `test breedItemClick`() {
        val referenceImageId = "123"
        val queryBreedId = "abys"
        coEvery { breedIdsManager.saveReferenceImageId(referenceImageId) } just Runs
        coEvery { breedIdsManager.saveQueryBreedId(queryBreedId) } just Runs

        breedsViewModel.interpret(BreedsInteract.OnBreedClickAction(referenceImageId, queryBreedId))

        coVerify { breedIdsManager.saveReferenceImageId(referenceImageId) }
        coVerify { breedIdsManager.saveQueryBreedId(queryBreedId) }
    }
}
package com.fausto.breeddetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fausto.breeddetails.viewmodel.base_info.viewstate.BreedDetailViewState
import com.fausto.breeddetails.viewmodel.base_info.BreedDetailViewModel
import com.fausto.breeddetails.viewmodel.base_info.interact.BreedDetailInteract
import com.fausto.common.result.ResultWrapper
import com.fausto.datastore.querybreed.BreedIdsManagerImpl
import com.fausto.domain.usecase.GetBreedByIdUseCase
import com.fausto.model.BreedDetailModel
import com.fausto.model.BreedModel
import com.fausto.model.WeightModel
import com.fausto.tracking.analytics.Analytics
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
internal class BreedDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var breedDetailViewModel: BreedDetailViewModel

    @MockK
    private lateinit var getBreedByIdUseCase: GetBreedByIdUseCase

    @MockK
    private lateinit var breedIdsManager: BreedIdsManagerImpl

    @RelaxedMockK
    private lateinit var analytics: Analytics

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        breedDetailViewModel = BreedDetailViewModel(getBreedByIdUseCase, breedIdsManager, analytics)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getBreedDetail success`() {
        val breedIdMock = "breedIdMock"
        val listOfBreedDetailModelMock = listOf(
            BreedDetailModel(
                WeightModel("imperial", "metric"),
                "id",
                "name",
                "temperament",
                "origin",
                "description",
                "lifespan"
            )
        )
        val breedModelMock = BreedModel("id", "url", listOfBreedDetailModelMock)
        coEvery { getBreedByIdUseCase.getBreedById(breedIdMock) } returns ResultWrapper.Success(
            breedModelMock
        )
        breedDetailViewModel.interpret(BreedDetailInteract.BaseHandleDeeplink(breedIdMock))
        val expectedViewState = BreedDetailViewState.Success(breedModelMock)
        assertEquals(breedDetailViewModel.breedDetailViewState.value, expectedViewState)
    }

    @Test
    fun `test getBreedDetail error`() {
        val breedIdMock = "breedIdMock"
        val errorMessage = "Failed to retrieve breed details"
        val errorResult = ResultWrapper.Error(Exception(errorMessage))
        coEvery { getBreedByIdUseCase.getBreedById(breedIdMock) } returns errorResult
        breedDetailViewModel.interpret(BreedDetailInteract.BaseHandleDeeplink(breedIdMock))
        val expectedViewState = BreedDetailViewState.Error(errorMessage)
        assertEquals(breedDetailViewModel.breedDetailViewState.value, expectedViewState)
    }

    @Test
    fun `test getReferenceImageId success`() = runTest {
        val referenceImageIdMock = "referenceImageIdMock"
        val listOfBreedDetailModelMock = listOf(
            BreedDetailModel(
                WeightModel("imperial", "metric"),
                "referenceImageIdMock",
                "name",
                "temperament",
                "origin",
                "description",
                "lifespan"
            )
        )
        val breedModelMock = BreedModel("referenceImageIdMock", "url", listOfBreedDetailModelMock)

        coEvery { breedIdsManager.getReferenceImageId() } returns flow { emit(referenceImageIdMock) }

        coEvery { getBreedByIdUseCase.getBreedById(referenceImageIdMock) } returns ResultWrapper.Success(
            breedModelMock
        )

        breedDetailViewModel.interpret(BreedDetailInteract.BaseViewCreated)

        dispatcher.scheduler.advanceUntilIdle()

        val expectedSuccessState = BreedDetailViewState.Success(
            breedModelMock
        )
        assertEquals(expectedSuccessState, breedDetailViewModel.breedDetailViewState.value)
    }
}
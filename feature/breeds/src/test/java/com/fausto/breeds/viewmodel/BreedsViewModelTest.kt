package com.fausto.breeds.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fausto.breeds.viewmodel.interact.BreedsInteract
import com.fausto.breeds.viewmodel.viewstate.BreedsViewState
import com.fausto.common.result.ResultWrapper
import com.fausto.datastore.querybreed.BreedIdsManager
import com.fausto.domain.usecase.GetBreedsBySearchUseCase
import com.fausto.domain.usecase.GetBreedsUseCase
import com.fausto.model.BreedsModel
import com.fausto.model.SectionModel
import com.fausto.tracking.analytics.Analytics
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
internal class BreedsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var breedsViewModel: BreedsViewModel

    @MockK
    private lateinit var getBreedsUseCase: GetBreedsUseCase

    @MockK
    private lateinit var getBreedBySearchUseCase: GetBreedsBySearchUseCase

    @MockK
    private lateinit var breedIdsManager: BreedIdsManager

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
    fun `test getBreeds success`() = runTest {
        val breedModelMock1 = BreedsModel("A", "a", "a", "a")
        val breedsModelListMock = listOf(breedModelMock1)
        coEvery { getBreedsUseCase.getBreeds() } returns ResultWrapper.Success(breedsModelListMock).data

        breedsViewModel.interpret(BreedsInteract.ViewCreated)

        val sectionMock = "A"
        val sectionModelMock = SectionModel(sectionMock, breedsModelListMock)
        val expectedViewState =
            BreedsViewState.Success(breedsModelListMock.map { sectionModelMock })
        assertEquals(breedsViewModel.breedsViewState.value, expectedViewState)
    }
}
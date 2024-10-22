package com.fausto.breeddetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fausto.breeddetails.viewmodel.gallery.GalleryViewModel
import com.fausto.breeddetails.viewmodel.gallery.interact.GalleryInteract
import com.fausto.breeddetails.viewmodel.gallery.viewstate.GalleryViewState
import com.fausto.common.result.ResultWrapper
import com.fausto.datastore.querybreed.BreedIdsManagerImpl
import com.fausto.domain.usecase.GetImagesByIdUseCase
import com.fausto.model.BreedImageModel
import com.fausto.tracking.analytics.Analytics
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
internal class GalleryViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var galleryViewModel: GalleryViewModel

    @MockK
    private lateinit var getImagesByIdUseCase: GetImagesByIdUseCase

    @MockK
    private lateinit var breedIdsManager: BreedIdsManagerImpl

    @RelaxedMockK
    private lateinit var analytics: Analytics

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        galleryViewModel = GalleryViewModel(getImagesByIdUseCase, breedIdsManager, analytics)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getImages success`() {
        val queryBreedId = "queryBreedId"
        coEvery { breedIdsManager.getQueryBreedId() } returns flowOf(queryBreedId)

        val listOfBreedImageModelMock = listOf(BreedImageModel("url"))

        coEvery { getImagesByIdUseCase.getImagesById(queryBreedId) } returns ResultWrapper.Success(
            listOfBreedImageModelMock
        )

        galleryViewModel.interpret(GalleryInteract.ViewCrated)
        val expectedViewState = GalleryViewState.Success(listOfBreedImageModelMock)
        assertEquals(expectedViewState, galleryViewModel.galleryViewState.value)
    }

    @Test
    fun `test getImages error`() {
        val queryBreedId = "queryBreedId"
        val errorMessage = "Failed to retrieve breed images"
        val errorResult = ResultWrapper.Error(Exception(errorMessage))

        coEvery { breedIdsManager.getQueryBreedId() } returns flowOf(queryBreedId)

        coEvery { getImagesByIdUseCase.getImagesById(queryBreedId) } returns errorResult
        galleryViewModel.interpret(GalleryInteract.ViewCrated)

        val expectedViewState = GalleryViewState.Error(errorMessage)
        assertEquals(galleryViewModel.galleryViewState.value, expectedViewState)
    }
}
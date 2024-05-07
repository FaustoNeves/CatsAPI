package com.fausto.breeddetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fausto.breeddetails.viewmodel.gallery.GalleryViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
internal class GalleryViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var galleryViewModel: GalleryViewModel

    private val dispatcher = UnconfinedTestDispatcher()
}
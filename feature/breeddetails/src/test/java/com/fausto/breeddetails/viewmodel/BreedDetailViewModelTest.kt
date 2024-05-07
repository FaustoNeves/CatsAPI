package com.fausto.breeddetails.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fausto.breeddetails.viewmodel.base_info.BreedDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
internal class BreedDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var breedDetailViewModel: BreedDetailViewModel

    private val dispatcher = UnconfinedTestDispatcher()
}
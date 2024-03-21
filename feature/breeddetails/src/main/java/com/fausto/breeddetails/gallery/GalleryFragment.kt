package com.fausto.breeddetails.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fausto.breeddetails.BreedDetailViewModel
import com.fausto.breeddetails.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class GalleryFragment : Fragment() {

    private val viewModel: BreedDetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }
}
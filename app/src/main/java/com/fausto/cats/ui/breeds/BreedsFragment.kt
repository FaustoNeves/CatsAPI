package com.fausto.cats.ui.breeds

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.fausto.cats.BuildConfig
import com.fausto.cats.databinding.FragmentBreedsBinding
import com.fausto.cats.ui.ErrorScreen
import com.fausto.cats.ui.details.BreedDetailViewState
import com.fausto.cats.ui.details.ImageViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedsFragment : Fragment() {

    private var _binding: FragmentBreedsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: BreedsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBreedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        viewModel.getBreeds()
//        viewModel.getBreedDetail()
//        viewModel.getImage("beng")
    }

    private fun setupObservers() {
        viewModel.breedsViewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BreedsViewState.Loading -> {
                    binding.loadingScreen.root.isVisible = true
                }

                is BreedsViewState.Success -> {
                    binding.loadingScreen.root.isVisible = false
                    Log.e("success data", state.breeds.toString())
                    Log.e("success data token", BuildConfig.API_KEY)
                }

                is BreedsViewState.Error -> {
                    binding.loadingScreen.root.isVisible = false
                    ErrorScreen { viewModel.getBreeds() }.apply {
                        isCancelable = false
                    }.show(parentFragmentManager, "")
                    Log.e("error data", state.errorMessage)
                }
            }
        }

//        viewModel.breedDetailViewState.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is BreedDetailViewState.Loading -> {
//
//                }
//
//                is BreedDetailViewState.Success -> {
//                    Log.e("success data", state.breeds.toString())
//                }
//
//                is BreedDetailViewState.Error -> {
//
//                }
//            }
//        }
//
//        viewModel.imageViewState.observe(viewLifecycleOwner) { state ->
//            when (state) {
//                is ImageViewState.Loading -> {
//
//                }
//
//                is ImageViewState.Success -> {
//                    Log.e("images", state.images.toString())
//                }
//
//                is ImageViewState.Error -> {
//
//                }
//            }
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
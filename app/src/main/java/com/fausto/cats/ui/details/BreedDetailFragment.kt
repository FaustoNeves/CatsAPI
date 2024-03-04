package com.fausto.cats.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.fausto.cats.databinding.FragmentBreedDetailBinding
import com.fausto.cats.domain.model.BreedModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class BreedDetailFragment : Fragment() {

    private var _binding: FragmentBreedDetailBinding? = null
    private val args: BreedDetailFragmentArgs by navArgs()
    private val viewModel: BreedDetailViewModel by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentBreedDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        initViewActions()
    }

    private fun setupObservers() {
        viewModel.breedDetailViewState.observe(viewLifecycleOwner) { breedDetailViewState ->
            when (breedDetailViewState) {
                is BreedDetailViewState.Loading -> {
                    setupLoadingView()
                }

                is BreedDetailViewState.Success -> {
                    setupSuccessView(breedDetailViewState.breeds)
                }

                is BreedDetailViewState.Error -> {
                    setupErrorView()
                }
            }
        }
    }

    private fun setupErrorView() {
        with(binding) {
            loadingScreen.root.isVisible = true
        }
    }

    private fun setupSuccessView(breedModel: BreedModel) {
        with(binding) {
            loadingScreen.root.isVisible = false
//            catBanner.setImageBitmap(state.)
        }
    }

    private fun setupLoadingView() {
        with(binding) {
            loadingScreen.root.isVisible = true
        }
    }

    private fun initViewActions() {
        viewModel.interpret(BreedDetailInteract.ViewCreated(args.breedQuery))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.fausto.cats.ui.breeds

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.fausto.cats.databinding.FragmentBreedsBinding
import com.fausto.cats.domain.model.BreedsModel
import com.fausto.cats.domain.model.SectionModel
import com.fausto.cats.ui.ErrorScreen
import com.fausto.cats.ui.breeds.adapter.SectionAdapter
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
                is BreedsViewState.Loading -> setupLoadingView()

                is BreedsViewState.Success -> setupSuccessView(state)

                is BreedsViewState.Error -> setupErrorView()
            }
        }
    }

    private fun setupLoadingView() {
        binding.loadingScreen.root.isVisible = true
    }

    private fun setupSuccessView(state: BreedsViewState.Success) {
        with(binding) {
            state.breeds.forEach {
                Log.e("success data section", it.section.toString())
                Log.e("success data", it.breedsList.toString())
            }
            loadingScreen.root.isVisible = false
            sectionRv.isVisible = true
            sectionRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            sectionRv.addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
            sectionRv.adapter = SectionAdapter(state.breeds)
        }
    }

    private fun setupErrorView() {
        binding.loadingScreen.root.isVisible = false
        ErrorScreen { viewModel.getBreeds() }.apply {
            isCancelable = false
        }.show(parentFragmentManager, "")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
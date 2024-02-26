package com.fausto.cats.ui.breeds

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.fausto.cats.databinding.FragmentBreedsBinding
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
            loadingScreen.root.isVisible = false
            sectionRv.isVisible = true
            sectionRv.addItemDecoration(
                DividerItemDecoration(
                    requireContext(), DividerItemDecoration.VERTICAL
                )
            )
            sectionRv.adapter = SectionAdapter(state.breeds) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupErrorView() {
        binding.loadingScreen.root.isVisible = false
        ErrorScreen { viewModel.getBreeds() }.apply {
            isCancelable = false
        }.show(parentFragmentManager, "")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.fausto.breeddetails.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fausto.breeddetails.ui.gallery.adapter.GalleryAdapter
import com.fausto.breeddetails.databinding.FragmentGalleryBinding
import com.fausto.breeddetails.viewmodel.gallery.GalleryViewModel
import com.fausto.breeddetails.viewmodel.gallery.interact.GalleryInteract
import com.fausto.breeddetails.viewmodel.gallery.viewstate.GalleryViewState
import com.fausto.designsystem.utils.ErrorView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GalleryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return FragmentGalleryBinding.inflate(inflater, container, false).run {
            _binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        viewModel.interpret(GalleryInteract.ViewCrated)
    }

    private fun setupObservers() {
        viewModel.galleryViewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GalleryViewState.Loading -> setupLoadingView()

                is GalleryViewState.Success -> setupSuccessView(state)

                is GalleryViewState.Error -> setupErrorView(state)
            }
        }
    }

    private fun setupLoadingView() {
        with(binding) {
            successView.isVisible = false
            loadingView.root.isVisible = true
        }
    }

    private fun setupSuccessView(state: GalleryViewState.Success) {
        with(binding) {
            loadingView.root.isVisible = false
            successView.isVisible = true
            galleryRv.adapter = GalleryAdapter(state.imagesList)
        }
    }

    private fun setupErrorView(state: GalleryViewState.Error) {
        with(binding) {
            loadingView.root.isVisible = false
            successView.isVisible = false
        }
        ErrorView(state.errorMessage) {
            viewModel.interpret(GalleryInteract.OnErrorAction)
        }.show(parentFragmentManager, "")
    }
}
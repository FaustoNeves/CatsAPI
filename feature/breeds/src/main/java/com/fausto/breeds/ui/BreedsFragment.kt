package com.fausto.breeds.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.fausto.breeds.databinding.FragmentBreedsBinding
import com.fausto.breeds.ui.adapter.SectionAdapter
import com.fausto.breeds.viewmodel.BreedsViewModel
import com.fausto.breeds.viewmodel.interact.BreedsInteract
import com.fausto.breeds.viewmodel.viewstate.BreedsViewState
import com.fausto.designsystem.utils.ErrorScreen
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
        setupSwipeRefresh()
        setupSearchAction()
        initViewActions()
    }

    private fun setupObservers() {
        viewModel.breedsViewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is BreedsViewState.Loading -> setupLoadingView()

                is BreedsViewState.Success -> setupSuccessView(state)

                is BreedsViewState.Error -> setupErrorView(state)

                is BreedsViewState.SaveReferenceImageIdSuccess -> navigateToBreedDetailsFragment()
            }
        }
    }

    private fun setupSwipeRefresh() {
        binding.breedsSwipeRefreshLayout.apply {
            setOnRefreshListener {
                onRefreshAction()
            }
        }
    }

    private fun SwipeRefreshLayout.onRefreshAction() {
        viewModel.interpret(BreedsInteract.OnRefreshAction)
        isRefreshing = false
    }

    private fun setupSearchAction() {
        with(binding) {
            var hasUserTyped = false
            searchTextInputEditText.addTextChangedListener(object : TextWatcher {
                private val DELAY_MS = 600L
                private val handler = Handler(Looper.getMainLooper())

                val breedsRunnable = Runnable {
                    if (hasUserTyped) viewModel.interpret(BreedsInteract.OnRefreshAction)
                }

                val searchBreedsRunnable = Runnable {
                    viewModel.interpret(
                        BreedsInteract.OnSearchBreedAction(
                            searchTextInputEditText.text.toString()
                        )
                    )
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0.toString().isBlank()) {
                        handler.removeCallbacks(breedsRunnable)
                        handler.postDelayed(breedsRunnable, DELAY_MS)
                    } else {
                        hasUserTyped = true
                        handler.removeCallbacks(searchBreedsRunnable)
                        handler.postDelayed(searchBreedsRunnable, DELAY_MS)
                    }
                }
            })
        }
    }

    private fun initViewActions() {
        viewModel.interpret(BreedsInteract.ViewCreated)
    }

    private fun setupLoadingView() {
        with(binding) {
            loadingScreen.root.isVisible = true
            noDataAvailableScreen.root.isVisible = false
            searchTextInputLayout.isVisible = false
            sectionRv.isVisible = false
        }
    }

    private fun setupSuccessView(state: BreedsViewState.Success) {
        with(binding) {
            loadingScreen.root.isVisible = false
            sectionRv.isVisible = true
            noDataAvailableScreen.root.isVisible = false
            searchTextInputLayout.isVisible = true
            if (state.sections.isNotEmpty()) {
                sectionRv.addItemDecoration(
                    DividerItemDecoration(
                        requireContext(), DividerItemDecoration.VERTICAL
                    )
                )
                sectionRv.adapter =
                    SectionAdapter(state.sections) { referenceImageId, queryBreedId ->
                        viewModel.interpret(
                            BreedsInteract.OnBreedClickAction(
                                referenceImageId,
                                queryBreedId
                            )
                        )
                    }
            } else {
                noDataAvailableScreen.root.isVisible = true
                sectionRv.isVisible = false
            }
        }
    }

    private fun setupErrorView(state: BreedsViewState.Error) {
        with(binding) {
            loadingScreen.root.isVisible = false
            noDataAvailableScreen.root.isVisible = false
        }
        ErrorScreen(state.errorMessage) {
            viewModel.interpret(BreedsInteract.OnErrorAction)
        }.show(parentFragmentManager, "")
    }

    private fun navigateToBreedDetailsFragment() {
        //adb shell am start -a android.intent.action.VIEW -d "cats://catsapp/details?breedquery=0XYvRd7o"
        val request =
            NavDeepLinkRequest.Builder.fromUri(BreedsFragmentConstants.BREED_DETAILS_BASE_FRAGMENT_DEEPLINK.toUri())
                .build()
        findNavController().navigate(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    object BreedsFragmentConstants {
        const val BREED_DETAILS_BASE_FRAGMENT_DEEPLINK = "cats://catsapp/details"
    }
}
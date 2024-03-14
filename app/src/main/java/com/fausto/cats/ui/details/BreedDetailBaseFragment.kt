package com.fausto.cats.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.fausto.cats.R
import com.fausto.cats.databinding.FragmentBreedDetailBinding
import com.fausto.cats.ui.base.ErrorScreen
import com.fausto.cats.ui.util.GradientTransformation
import com.fausto.cats.ui.util.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class BreedDetailBaseFragment : Fragment() {

    private var _binding: FragmentBreedDetailBinding? = null
    private val args: BreedDetailBaseFragmentArgs by navArgs()
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
                    setupSuccessView(breedDetailViewState)
                }

                is BreedDetailViewState.Error -> {
                    setupErrorView()
                }
            }
        }
    }

    private fun initViewActions() {
        viewModel.interpret(BreedDetailInteract.ViewCreated(args.breedQuery))
    }

    private fun setupErrorView() {
        with(binding) {
            loadingScreen.root.isVisible = false
            loadingScreen.loadingAnimation.isVisible = false
            tabLayout.isVisible = false
            viewPager.isVisible = false
            ErrorScreen {
                viewModel.interpret(BreedDetailInteract.OnErrorAction(args.breedQuery))
            }.apply {
                isCancelable = false
            }.show(parentFragmentManager, "")
        }
    }

    private fun setupSuccessView(state: BreedDetailViewState.Success) {
        with(binding) {
            tabLayout.isVisible = true
            viewPager.isVisible = true
            loadingScreen.root.isVisible = false
            loadingScreen.loadingAnimation.isVisible = false
            setCatBanner(state.breed.url)
            catNamme.text = state.breed.breeds[0].name
        }
        setupLayout()
    }

    private fun setupLoadingView() {
        with(binding) {
            loadingScreen.root.isVisible = true
            loadingScreen.loadingAnimation.isVisible = true
            tabLayout.isVisible = false
            viewPager.isVisible = false
        }
    }

    private fun setupLayout() {
        with(binding) {
            val tabs = BreedDetailTabItem.values()
            val fragments = BreedDetailTabItem.values().map {
                it.createFragmentInstance.invoke()
            }
            viewPager.adapter = ViewPagerAdapter(requireActivity(), fragments)
            TabLayoutMediator(
                tabLayout, viewPager
            ) { tab: TabLayout.Tab, position: Int ->
                tab.text = requireContext().getString(tabs[position].title)
            }.attach()
        }
    }

    private fun FragmentBreedDetailBinding.setCatBanner(url: String) {
        Picasso.get().load(url).error(R.drawable.baseline_error_24)
            .transform(GradientTransformation()).into(catBanner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.fausto.breeddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fausto.breeddetails.databinding.FragmentBreedDetailBinding
import com.fausto.designsystem.utils.ErrorScreen
import com.fausto.designsystem.utils.GradientTransformation
import com.fausto.designsystem.utils.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedDetailBaseFragment : Fragment() {

    private var _binding: FragmentBreedDetailBinding? = null
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

        setupLayout()
        setupObservers()
        handleDeeplink()
        initViewActions()
    }

    private fun handleDeeplink() {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("breedquery")
            ?.observe(viewLifecycleOwner) { breedQuery ->
                Toast.makeText(requireContext(), breedQuery, Toast.LENGTH_SHORT).show()
                // Handle the breedQuery
            }

        val deeplinkUri = requireActivity().intent.data
        deeplinkUri?.let {
            val breedQuery = it.getQueryParameter("breedquery")
            breedQuery?.let { imageId ->
                viewModel.interpret(BreedDetailInteract.HandleDeeplink(imageId))
            }
        }
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
        viewModel.interpret(BreedDetailInteract.ViewCreated)
    }

    private fun setupErrorView() {
        with(binding) {
            successView.isVisible = false
            loadingScreen.root.isVisible = false
            loadingScreen.loadingAnimation.isVisible = false
            ErrorScreen {
                viewModel.interpret(BreedDetailInteract.OnErrorAction)
            }.apply {
                isCancelable = false
            }.show(parentFragmentManager, "")
        }
    }

    private fun setupSuccessView(state: BreedDetailViewState.Success) {
        with(binding) {
            successView.isVisible = true
            loadingScreen.root.isVisible = false
            loadingScreen.loadingAnimation.isVisible = false
            setCatBanner(state.breed.url)
            catName.text = state.breed.breeds[0].name
        }
    }

    private fun setupLoadingView() {
        with(binding) {
            successView.isVisible = false
            loadingScreen.root.isVisible = true
            loadingScreen.loadingAnimation.isVisible = true
        }
    }

    private fun setupLayout() {
        with(binding) {
            val tabs = BreedDetailTabItem.values()
            val fragments = BreedDetailTabItem.values().map {
                it.createFragmentInstance.invoke()
            }
            viewPager.adapter = ViewPagerAdapter(requireActivity(), fragments)

            TabLayoutMediator(tabLayout, viewPager) { tab: TabLayout.Tab, position: Int ->
                tab.text = requireContext().getString(tabs[position].title)
            }.attach()
        }
    }

    private fun FragmentBreedDetailBinding.setCatBanner(url: String) {
        Picasso.get().load(url).error(com.fausto.designsystem.R.drawable.baseline_error_24)
            .transform(GradientTransformation()).into(catBanner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

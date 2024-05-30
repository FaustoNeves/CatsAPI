package com.fausto.breeddetails.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fausto.breeddetails.viewmodel.base_info.viewstate.BreedDetailViewState
import com.fausto.breeddetails.databinding.FragmentInfoBinding
import com.fausto.breeddetails.viewmodel.base_info.BreedDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class InfoFragment : Fragment() {

    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BreedDetailViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return FragmentInfoBinding.inflate(inflater, container, false).run {
            _binding = this
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.breedDetailViewState.observe(viewLifecycleOwner) { state ->
            if (state is BreedDetailViewState.Success) bindData(state)
        }
    }

    private fun bindData(state: BreedDetailViewState.Success) {
        state.breed.breeds[0].apply {
            with(binding) {
                catDescription.text = description
                catTemperament.text = getString(com.fausto.texts.R.string.temperament, temperament)
                catOrigin.text = getString(com.fausto.texts.R.string.origin, origin)
                catWeightMetric.text =
                    getString(com.fausto.texts.R.string.weight_metric, weight?.metric)
                catWeightImperial.text =
                    getString(com.fausto.texts.R.string.weight_imperial, weight?.imperial)
                catLifespan.text = getString(com.fausto.texts.R.string.lifespan, lifeSpan)
            }
        }
    }
}
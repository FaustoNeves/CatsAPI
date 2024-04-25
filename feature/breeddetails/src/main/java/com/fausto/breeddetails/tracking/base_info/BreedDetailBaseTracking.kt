package com.fausto.breeddetails.tracking.base_info

import com.fausto.breeddetails.viewmodel.base_info.BreedDetailViewModel
import com.fausto.tracking.constants.breeddetail.base_info.BreedDetailsAnalyticsValues

internal fun BreedDetailViewModel.trackScreenView() {
    analytics.trackScreenView(
        BreedDetailsAnalyticsValues.run {
            analytics.setBundle(SCREEN_TYPE, SCREEN_NAME)
        }
    )
}
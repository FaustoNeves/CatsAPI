package com.fausto.breeddetails.tracking

import com.fausto.breeddetails.viewmodel.BreedDetailViewModel
import com.fausto.tracking.constants.breeddetail.base_info.BreedDetailsAnalyticsValues

internal fun BreedDetailViewModel.trackScreenView() {
    analytics.trackScreenView(
        BreedDetailsAnalyticsValues.run {
            analytics.setBundle(SCREEN_TYPE, SCREEN_NAME)
        }
    )
}
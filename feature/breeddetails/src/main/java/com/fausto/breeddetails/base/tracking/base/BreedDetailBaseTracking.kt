package com.fausto.breeddetails.base.tracking.base

import com.fausto.breeddetails.base.viewmodel.base_info.BreedDetailViewModel
import com.fausto.tracking.constants.breeddetail.base.BreedDetailsAnalyticsValues

internal fun BreedDetailViewModel.trackScreenView() {
    analytics.trackScreenView(
        BreedDetailsAnalyticsValues.run {
            analytics.setBundle(SCREEN_TYPE, SCREEN_NAME)
        }
    )
}
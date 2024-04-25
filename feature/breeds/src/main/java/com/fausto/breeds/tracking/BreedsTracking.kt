package com.fausto.breeds.tracking

import com.fausto.breeds.viewmodel.BreedsViewModel
import com.fausto.tracking.constants.breeds.BreedsAnalyticsValues

internal fun BreedsViewModel.trackScreenView() {
    analytics.trackScreenView(BreedsAnalyticsValues.run {
        analytics.setBundle(SCREEN_TYPE, SCREEN_NAME)
    })
}

internal fun BreedsViewModel.trackSelectedItem(catBreed: String) {
    analytics.trackSelectItem(BreedsAnalyticsValues.run {
        analytics.setBundle(SCREEN_TYPE, catBreed)
    })
}

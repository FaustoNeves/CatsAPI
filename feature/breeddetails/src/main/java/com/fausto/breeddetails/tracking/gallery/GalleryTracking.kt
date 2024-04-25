package com.fausto.breeddetails.tracking.gallery

import com.fausto.breeddetails.viewmodel.gallery.GalleryViewModel
import com.fausto.tracking.constants.breeddetail.gallery.GalleryAnalyticsValues

internal fun GalleryViewModel.trackScreenView() {
    analytics.trackScreenView(GalleryAnalyticsValues.run {
        analytics.setBundle(SCREEN_TYPE, SCREEN_NAME)
    })
}
package com.fausto.breeddetails.ui.base

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.fausto.breeddetails.ui.gallery.GalleryFragment
import com.fausto.breeddetails.ui.info.InfoFragment

internal enum class BreedDetailTabItem(
    @StringRes val title: Int, val createFragmentInstance: () -> Fragment
) {
    INFO(title = com.fausto.texts.R.string.info_tab_title, createFragmentInstance = { InfoFragment() }),
    GALLERY(title = com.fausto.texts.R.string.gallery_tab_title, createFragmentInstance = { GalleryFragment() })
}
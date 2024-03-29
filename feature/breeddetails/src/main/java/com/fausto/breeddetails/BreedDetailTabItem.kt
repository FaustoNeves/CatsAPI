package com.fausto.breeddetails

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.fausto.breeddetails.gallery.GalleryFragment
import com.fausto.breeddetails.info.InfoFragment

internal enum class BreedDetailTabItem(
    @StringRes val title: Int, val createFragmentInstance: () -> Fragment
) {
    INFO(title = R.string.info_tab_title, createFragmentInstance = { InfoFragment() }),
    GALLERY(title = R.string.gallery_tab_title, createFragmentInstance = { GalleryFragment() })
}
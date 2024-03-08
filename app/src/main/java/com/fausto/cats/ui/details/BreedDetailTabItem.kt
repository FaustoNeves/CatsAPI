package com.fausto.cats.ui.details

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.fausto.cats.R
import com.fausto.cats.ui.details.gallery.GalleryFragment
import com.fausto.cats.ui.details.info.InfoFragment

internal enum class BreedDetailTabItem(
    @StringRes val title: Int, val createFragmentInstance: () -> Fragment
) {
    INFO(title = R.string.info_tab_title, createFragmentInstance = { InfoFragment() }),
    GALLERY(title = R.string.gallery_tab_title, createFragmentInstance = { GalleryFragment() })
}
package com.fausto.tracking.analytics

import android.os.Bundle

interface Analytics {
    fun trackScreenView(bundle: Bundle)
    fun trackSelectItem(bundle: Bundle)
    fun setBundle(keyParam: String, valueParam: String): Bundle
}
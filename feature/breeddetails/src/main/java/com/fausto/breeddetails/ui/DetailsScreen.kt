package com.fausto.breeddetails.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun DetailsRoute(breedId: String, imageQueryId: String) {
    DetailsScreen(breedId, imageQueryId)
}

@Composable
private fun DetailsScreen(breedId: String, imageQueryId: String) {
    Text("breedId: $breedId, imageQueryId: $imageQueryId")
}
package com.fausto.breeddetails.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DetailsRoute() {

}

@Composable
fun DetailsScreen(breedId: String, imageQueryId: String) {
    Text("breedId: $breedId, imageQueryId: $imageQueryId")
}
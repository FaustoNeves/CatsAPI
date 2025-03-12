package com.fausto.cats.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fausto.breeddetails.navigation.detailsScreen
import com.fausto.breeddetails.navigation.navigateToDetailsScreen
import com.fausto.breeds.navigation.BreedsRoute
import com.fausto.breeds.navigation.breedsScreen

@Composable
fun CatsNavHost(
    modifier: Modifier = Modifier.padding(8.dp), navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = BreedsRoute
    ) {
        breedsScreen(modifier = modifier) { breedId, imageQueryId ->
            navController.navigateToDetailsScreen(breedId, imageQueryId)
        }
        detailsScreen(modifier = modifier)
    }
}
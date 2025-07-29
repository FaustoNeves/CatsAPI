package com.fausto.cats.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fausto.breeddetails.route.detailsScreen
import com.fausto.breeddetails.route.navigateToDetailsScreen
import com.fausto.breeds.route.BreedsRoute
import com.fausto.breeds.route.breedsScreen

@Composable
fun CatsNavGraph(
    modifier: Modifier = Modifier.padding(8.dp), navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = BreedsRoute
    ) {
        breedsScreen(modifier = modifier, onBreedClick = { breedId, referenceImageId ->
            navController.navigateToDetailsScreen(breedId, referenceImageId)
        })
        detailsScreen(modifier = modifier) {
            navController.navigateUp()
        }
    }
}
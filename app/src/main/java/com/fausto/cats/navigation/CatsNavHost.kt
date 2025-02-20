package com.fausto.cats.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.fausto.breeddetails.navigation.detailsScreen
import com.fausto.breeddetails.navigation.navigateToDetailsScreen
import com.fausto.breeds.navigation.BreedsRoute
import com.fausto.breeds.navigation.breedsScreen

@Composable
fun CatsNavHost(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = BreedsRoute
    ) {
        breedsScreen { breedId, imageQueryId ->
            navController.navigateToDetailsScreen(breedId, imageQueryId)
        }
        detailsScreen()
    }
}
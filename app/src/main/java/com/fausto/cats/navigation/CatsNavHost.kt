package com.fausto.cats.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.fausto.breeddetails.ui.DetailsScreen
import com.fausto.breeds.ui.BreedsRoute
import kotlinx.serialization.Serializable

@Serializable
object Breeds

@Serializable
data class Details(val breedId: String, val imageQueryId: String)

@Composable
fun CatsNavHost(
    modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Breeds
    ) {
        composable<Breeds> {
            BreedsRoute(onBreedClick = { breedId: String, imageQueryId: String ->
                navController.navigate(
                    route = Details(
                        breedId = breedId,
                        imageQueryId = imageQueryId
                    )
                )
            })
        }

        composable<Details> { backStackEntry ->
            val detailsRoute = backStackEntry.toRoute<Details>()
            DetailsScreen(detailsRoute.breedId, detailsRoute.imageQueryId)
        }
    }
}
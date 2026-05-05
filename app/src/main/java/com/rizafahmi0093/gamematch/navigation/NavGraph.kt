package com.rizafahmi0093.gamematch.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.*

import com.rizafahmi0093.gamematch.ui.screen.MainScreen
import com.rizafahmi0093.gamematch.ui.screen.DetailScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(Screen.Home.route) {
            MainScreen(navController)
        }

        composable(Screen.FormBaru.route) {
            DetailScreen(navController)
        }

        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(
                navArgument(KEY_ID_MATCH) {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong(KEY_ID_MATCH)
            DetailScreen(navController, id)
        }
    }
}
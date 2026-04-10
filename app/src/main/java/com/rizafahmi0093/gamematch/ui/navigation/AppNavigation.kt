package com.rizafahmi0093.gamematch.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rizafahmi0093.gamematch.ui.screen.HomeScreen
import com.rizafahmi0093.gamematch.ui.screen.ResultScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable( "home") {
            HomeScreen(navController)
        }
        composable("result/{genre}/{mood}") { backStackEntry ->

            val genre = backStackEntry.arguments?.getString("genre") ?: ""
            val mood = backStackEntry.arguments?.getString("mood") ?: ""

            ResultScreen(navController, genre, mood)
        }
    }
}
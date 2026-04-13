package com.rizafahmi0093.gamematch.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rizafahmi0093.gamematch.ui.screen.HomeScreen
import com.rizafahmi0093.gamematch.ui.screen.InputScreen
import com.rizafahmi0093.gamematch.ui.screen.ResultScreen
import com.rizafahmi0093.gamematch.ui.screen.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("input") {
            InputScreen(navController)
        }

        composable("home/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            HomeScreen(navController, name)
        }

        composable(
            "result/{genre}/{mood}/{platform}/{rating}/{mode}"
        ) { backStackEntry ->

            val genre = backStackEntry.arguments?.getString("genre") ?: ""
            val mood = backStackEntry.arguments?.getString("mood") ?: ""
            val platform = backStackEntry.arguments?.getString("platform") ?: ""
            val rating = backStackEntry.arguments?.getString("rating") ?: ""
            val mode = backStackEntry.arguments?.getString("mode") ?: ""

            ResultScreen(
                genre,
                mood,
                platform,
                rating,
                mode,
                navController
            )
        }
    }
}
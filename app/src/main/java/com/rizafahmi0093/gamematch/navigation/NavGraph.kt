    package com.rizafahmi0093.gamematch.navigation

    import androidx.compose.runtime.Composable
    import androidx.navigation.NavType
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import androidx.navigation.navArgument
    import com.rizafahmi0093.gamematch.ui.screen.CreatePostScreen
    import com.rizafahmi0093.gamematch.ui.screen.DetailScreen
    import com.rizafahmi0093.gamematch.ui.screen.FeedScreen
    import com.rizafahmi0093.gamematch.ui.screen.HomeScreen
    import com.rizafahmi0093.gamematch.ui.screen.InputScreen
    import com.rizafahmi0093.gamematch.ui.screen.MainScreen
    import com.rizafahmi0093.gamematch.ui.screen.ProfileScreen
    import com.rizafahmi0093.gamematch.ui.screen.ResultScreen
    import com.rizafahmi0093.gamematch.ui.screen.SplashScreen
    import com.rizafahmi0093.gamematch.ui.screen.TrendingScreen
    import com.rizafahmi0093.gamematch.ui.screen.WishlistScreen

    const val KEY_ID_MATCH = "idMatch"

    @Composable
    fun SetupNavGraph() {

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route
        ) {

            composable(Screen.Splash.route) {
                SplashScreen(navController)
            }

            composable(Screen.Input.route) {
                InputScreen(navController)
            }

            composable(Screen.Home.route) {
                HomeScreen(navController)
            }

            composable(
                route = Screen.Result.route,
                arguments = listOf(
                    navArgument("genre") {
                        type = NavType.StringType
                    },
                    navArgument("mood") {
                        type = NavType.StringType
                    },
                    navArgument("platform") {
                        type = NavType.StringType
                    },
                    navArgument("rating") {
                        type = NavType.StringType
                    },
                    navArgument("mode") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val genre =
                    backStackEntry.arguments?.getString("genre") ?: ""

                val mood =
                    backStackEntry.arguments?.getString("mood") ?: ""

                val platform =
                    backStackEntry.arguments?.getString("platform") ?: ""

                val rating =
                    backStackEntry.arguments?.getString("rating") ?: ""

                val mode =
                    backStackEntry.arguments?.getString("mode") ?: ""

                ResultScreen(
                    genre = genre,
                    mood = mood,
                    platform = platform,
                    rating = rating,
                    mode = mode,
                    navController = navController
                )
            }


            composable(Screen.Main.route) {
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

                val id = backStackEntry.arguments
                    ?.getLong(KEY_ID_MATCH)

                DetailScreen(navController, id)
            }

            composable(Screen.Trending.route) {
                TrendingScreen(navController)
            }
            composable(Screen.Wishlist.route) {
                WishlistScreen(navController)
            }

            composable(Screen.Feed.route) {
                FeedScreen(navController)
            }
            composable(Screen.CreatePost.route) {
                CreatePostScreen(navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }

        }
    }
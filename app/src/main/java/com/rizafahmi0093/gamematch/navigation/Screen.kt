package com.rizafahmi0093.gamematch.navigation


sealed class Screen(val route: String) {

    data object Splash : Screen("splash")

    data object Input : Screen("input")

    data object Home : Screen("home/{name}") {
        fun withName(name: String) = "home/$name"
    }

    data object Result :
        Screen("result/{genre}/{mood}/{platform}/{rating}/{mode}") {

        fun withArgs(
            genre: String,
            mood: String,
            platform: String,
            rating: String,
            mode: String
        ) =
            "result/$genre/$mood/$platform/$rating/$mode"
    }

    data object Main : Screen("main")

    data object FormBaru : Screen("formBaru")

    data object FormUbah : Screen("formUbah/{idMatch}") {
        fun withId(id: Long) = "formUbah/$id"
    }

    object Trending : Screen("trending")
}
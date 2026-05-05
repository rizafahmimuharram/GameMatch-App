package com.rizafahmi0093.gamematch.navigation

const val KEY_ID_MATCH = "idMatch"

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object FormBaru : Screen("form_match")

    data object FormUbah : Screen("form_match/{$KEY_ID_MATCH}") {
        fun withId(id: Long) = "form_match/$id"
    }
}
package com.rizafahmi0093.gamematch.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "GameMatch",
            style = MaterialTheme.typography.headlineLarge
        )

        Text(
            text = "Temukan game terbaik sesuai mood kamu ",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(
            onClick = {
                navController.navigate("input")
            }
        ) {
            Text("Mulai")
        }
    }
}
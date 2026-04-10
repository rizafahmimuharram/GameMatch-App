package com.rizafahmi0093.gamematch.ui.screen

import android.R.attr.onClick
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ResultScreen(
    navController: NavController,
    genre: String,
    mood: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text ("Genre: $genre")
        Text ("Genre: $mood")

        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Kembali")
        }
    }
}
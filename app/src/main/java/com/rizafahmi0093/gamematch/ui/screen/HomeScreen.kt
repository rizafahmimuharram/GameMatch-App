package com.rizafahmi0093.gamematch.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {

    var selectedGenres by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("GameMatch")


        Text("Pilih genre: ")
        Row {
            Button(onClick = { selectedGenres = "Action" }) {
                Text("Action")
            }
            Button(onClick = { selectedGenres = "Horror" }) {
                Text("Horror")
            }
        }

        Text("Pilih Mood:")
        Row {
            Button(onClick = { selectedMood = "Santai" }) {
                Text("Santai")
            }
            Button(onClick = { selectedMood = "Tegang" }) {
                Text("Tegang")
            }
        }


        if (errorMessage.isNotEmpty()) {
            Text(errorMessage)
        }

        Button(onClick = {
            if (selectedGenres.isNotEmpty() || selectedMood.isEmpty()) {
                errorMessage = "Harap pilih semua opsi!"
            } else {
                navController.navigate("result/$selectedGenres/$selectedMood")
            }
        }) {
            Text("Cari Game")
        }
    }
}
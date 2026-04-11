package com.rizafahmi0093.gamematch.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rizafahmi0093.gamematch.data.GameRepository
import com.rizafahmi0093.gamematch.model.Game

@Composable
fun ResultScreen(
    genre: String,
    mood: String,
    platform: String,
    rating: String,
    mode: String,
    navController: NavController
) {

    val games = GameRepository.getRecommendedGames(
        genre,
        mood,
        platform,
        rating,
        mode
    )


    LazyColumn (
        modifier  = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                "Hasil Rekomendasi",
                style = MaterialTheme.typography.headlineMedium
            )

            Text("Genre: $genre")
            Text("Mood: $mood")
            Text("Platform: $platform")
            Text("Rating: $rating")
            Text("Mode: $mode")

            Spacer(modifier = Modifier.height(16.dp))
        }

        items(games) { game ->
            GameCard(game)
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                navController.popBackStack()
            }) {
                Text("Kembali")
            }
        }
    }
}

@Composable
fun GameCard(game: Game) {

    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = game.imageResId),
                contentDescription = game.name,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(game.name, style = MaterialTheme.typography.titleMedium)
            Text("Genre: ${game.genre}")
            Text("Rating: ${game.rating}")
        }
    }
}
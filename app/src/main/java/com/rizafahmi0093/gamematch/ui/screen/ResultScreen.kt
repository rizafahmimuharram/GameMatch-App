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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rizafahmi0093.gamematch.data.GameRepository
import com.rizafahmi0093.gamematch.model.Game
import com.rizafahmi0093.gamematch.viewmodel.GameViewModel

@Composable
fun ResultScreen(
    genre: String,
    mood: String,
    platform: String,
    rating: String,
    mode: String,
    navController: NavController
) {
    val viewModel: GameViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    // 🔥 Load data sekali
    LaunchedEffect(Unit) {
        viewModel.loadGames(genre, mood, platform, rating, mode)
    }

    val games = viewModel.recommendedGames.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // 🔥 HEADER
        item {
            Text(
                "Hasil Rekomendasi",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Genre: $genre")
            Text("Mood: $mood")
            Text("Platform: $platform")
            Text("Rating: $rating")
            Text("Mode: $mode")

            Spacer(modifier = Modifier.height(16.dp))
        }

        // 🔥 EMPTY STATE
        if (games.isEmpty()) {
            item {
                Text(
                    "Tidak ada game yang cocok",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // 🔥 LIST GAME
        items(games) { game ->
            GameCard(game)
        }

        // 🔥 BUTTON BACK
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
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
            .padding(vertical = 6.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(4.dp)
    ) {
        Column {

            // 🔥 IMAGE
            Image(
                painter = painterResource(id = game.imageResId),
                contentDescription = game.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {

                Text(
                    game.name,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text("Genre: ${game.genre}")
                Text("Mood: ${game.mood}")
                Text("Platform: ${game.platforms.joinToString()}")
                Text("Mode: ${game.modes.joinToString()}")

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    "⭐ ${game.rating}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
package com.rizafahmi0093.gamematch.ui.screen

import android.content.Intent
import com.rizafahmi0093.gamematch.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rizafahmi0093.gamematch.model.Game
import com.rizafahmi0093.gamematch.navigation.Screen
import com.rizafahmi0093.gamematch.viewmodel.GameViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ResultScreen(
    genre: String,
    mood: String,
    platform: String,
    rating: String,
    mode: String,
    navController: NavController
) {

    val viewModel: GameViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadGames(
            genre,
            mood,
            platform,
            rating,
            mode
        )
    }
    val games by viewModel.recommendedGames
        .collectAsStateWithLifecycle()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.recommendations
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = Color.Unspecified,
                    navigationIconContentColor = Color.Unspecified,
                    titleContentColor = Color.Unspecified,
                    actionIconContentColor = Color.Unspecified
                )

            )
        }

    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            item {

                Text(
                    text = "Rekomendasi Game Untuk Kamu",
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

            if (games.isEmpty()) {

                item {

                    Text(
                        text = "Tidak ada game yang cocok",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            items(games) { game ->

                GameCard(game)
            }

            item {

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {

                        val shareText = buildString {

                            append("Halo! these recommendation games for you \n\n")

                            append("Genre: $genre\n")
                            append("Mood: $mood\n")
                            append("Platform: $platform\n")
                            append("Rating: $rating\n")
                            append("Mode: $mode\n\n")

                            append("Game Rekomendasi:\n")

                            games.forEach {

                                append("- ${it.name} ⭐${it.rating}\n")
                            }
                        }

                        val intent = Intent(Intent.ACTION_SEND).apply {

                            type = "text/plain"

                            putExtra(
                                Intent.EXTRA_TEXT,
                                shareText
                            )
                        }

                        context.startActivity(
                            Intent.createChooser(
                                intent,
                                "Bagikan ke"
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("Share")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController.navigate(
                            Screen.Home.withName("reset")
                        ) {
                            popUpTo(Screen.Home.route) {
                                inclusive = true
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("Back")
                }
            }
        }
    }
}

@Composable
fun GameCard(game: Game) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column {

            Image(
                painter = painterResource(
                    id = game.imageResId
                ),

                contentDescription = game.name,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),

                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                Text(
                    text = game.name,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text("Genre: ${game.genre}")
                Text("Mood: ${game.mood}")
                Text("Platform: ${game.platforms.joinToString()}")
                Text("Mode: ${game.modes.joinToString()}")

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "⭐ ${game.rating}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
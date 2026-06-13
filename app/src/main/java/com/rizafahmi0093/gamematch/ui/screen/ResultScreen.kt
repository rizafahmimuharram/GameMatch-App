package com.rizafahmi0093.gamematch.ui.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.rizafahmi0093.gamematch.R
import com.rizafahmi0093.gamematch.model.Game
import com.rizafahmi0093.gamematch.model.Review
import com.rizafahmi0093.gamematch.model.User
import com.rizafahmi0093.gamematch.model.Wishlist
import com.rizafahmi0093.gamematch.navigation.Screen
import com.rizafahmi0093.gamematch.network.UserDataStore
import com.rizafahmi0093.gamematch.ui.components.GameMatchTopBar
import com.rizafahmi0093.gamematch.ui.components.signOut
import com.rizafahmi0093.gamematch.viewmodel.GameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.rizafahmi0093.gamematch.util.ViewModelFactory
import com.rizafahmi0093.gamematch.viewmodel.ReviewViewModel
import com.rizafahmi0093.gamematch.viewmodel.WishlistViewModel

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
    val context = LocalContext.current
    val viewModel: GameViewModel = viewModel()
    val wishlistViewModel: WishlistViewModel = viewModel(
        factory = ViewModelFactory(context)
    )
    val reviewViewModel: ReviewViewModel = viewModel(
        factory = ViewModelFactory(context)
    )
    val userDataStore = UserDataStore(context)
    val user by userDataStore.userFlow.collectAsState(initial = User())
    var showDialog by remember { mutableStateOf(false) }
    var showEditName by remember { mutableStateOf(false) }


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


    Scaffold(
        topBar = {
            GameMatchTopBar(
                title = stringResource(R.string.recommendations),
                showBackButton = true,
                onBackClick = { navController.popBackStack() },
                onProfilClick = { showDialog = true }
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
                GameCard(
                    game = game,
                    wishlistViewModel = wishlistViewModel,
                    reviewViewModel = reviewViewModel,
                    userName = user.name
                )
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


    if (showDialog) {
        ProfilDialog(
            user = user,
            onDismissRequest = { showDialog = false },
            onConfirmation = {
                CoroutineScope(Dispatchers.IO).launch {
                    signOut(context, userDataStore)
                }
                navController.navigate(Screen.Splash.route) {
                    popUpTo(0) { inclusive = true }
                }
                showDialog = false
            },
            onProfileClick = { navController.navigate(Screen.Profile.route) }
        )
    }
    if (showEditName) {
        EditNameDialog(
            currentName = user.name,
            onDismiss = { showEditName = false },
            onSave = { newName ->
                CoroutineScope(Dispatchers.IO).launch {
                    userDataStore.updateName(newName)
                }
                showEditName = false
            }
        )
    }
}

@Composable
fun GameCard(
    game: Game,
    wishlistViewModel: WishlistViewModel,
    reviewViewModel: ReviewViewModel,
    userName: String
) {
    val isWishlisted by wishlistViewModel.isWishlisted(game.id)
        .collectAsState(initial = false)
    val reviews by reviewViewModel.getReviewsByGame(game.id)
        .collectAsState(initial = emptyList())

    var showReviewDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = game.imageResId),
                contentDescription = game.name,
                modifier = Modifier.fillMaxWidth().height(180.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = game.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Genre: ${game.genre}")
                Text("Mood: ${game.mood}")
                Text("Platform: ${game.platforms.joinToString()}")
                Text("Mode: ${game.modes.joinToString()}")
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "⭐ ${game.rating}", style = MaterialTheme.typography.bodyMedium)

                // tampilkan jumlah review
                if (reviews.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${reviews.size} review • Avg: ${"%.1f".format(reviews.map { it.rating }.average())}⭐",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                    OutlinedButton(
                        onClick = {
                            if (isWishlisted) wishlistViewModel.removeWishlist(game.id)
                            else wishlistViewModel.addWishlist(
                                Wishlist(
                                    gameId = game.id,
                                    gameName = game.name,
                                    genre = game.genre,
                                    rating = game.rating,
                                    imageResId = game.imageResId
                                )
                            )
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isWishlisted) "❤️ Wishlisted" else "Wishlist")
                    }


                    OutlinedButton(
                        onClick = { showReviewDialog = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Review")
                    }
                }
            }
        }
    }

    if (showReviewDialog) {
        ReviewDialog(
            gameName = game.name,
            onDismiss = { showReviewDialog = false },
            onSubmit = { reviewText, rating ->
                reviewViewModel.addReview(
                    Review(
                        gameId = game.id,
                        gameName = game.name,
                        userName = userName,
                        reviewText = reviewText,
                        rating = rating
                    )
                )
                showReviewDialog = false
            }
        )
    }
}
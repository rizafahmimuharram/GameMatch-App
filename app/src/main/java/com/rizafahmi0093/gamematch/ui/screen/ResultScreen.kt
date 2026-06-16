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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow


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
        viewModel.loadGames(genre, mood, platform, rating, mode)
    }
    val games by viewModel.recommendedGames.collectAsStateWithLifecycle()

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
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                // Header Section yang Lebih Bersih
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(
                        text = "Rekomendasi Game Untukmu",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Berdasarkan preferensi dan mood gaming kamu saat ini.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Kategori Filter dalam Bentuk FlowRow yang Modern (Bukan Text Menumpuk Biasa)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    SuggestionChip(onClick = {}, label = { Text("Genre: $genre") })
                    SuggestionChip(onClick = {}, label = { Text("Mood: $mood") })
                    SuggestionChip(onClick = {}, label = { Text("Platform: $platform") })
                    SuggestionChip(onClick = {}, label = { Text("Rating: ≥$rating") })
                    SuggestionChip(onClick = {}, label = { Text("Mode: $mode") })
                }

                Spacer(modifier = Modifier.height(6.dp))
            }

            if (games.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Tidak ada game yang cocok dengan kriteria",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }

            items(games) { game ->
                GameCard(
                    game = game,
                    wishlistViewModel = wishlistViewModel,
                    reviewViewModel = reviewViewModel,
                    userEmail = user.email
                )
            }


            item {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Kembali", fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            val shareText = buildString {
                                append("Halo! Ini rekomendasi game seru untuk kamu \n\n")
                                append("🎯 Genre: $genre\n")
                                append("🎭 Mood: $mood\n")
                                append("🎮 Platform: $platform\n")
                                append("⭐ Rating: $rating\n")
                                append("👥 Mode: $mode\n\n")
                                append("Rekomendasi Teratas:\n")
                                games.forEach { append("- ${it.name} (⭐${it.rating})\n") }
                            }
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, shareText)
                            }
                            context.startActivity(Intent.createChooser(intent, "Bagikan ke"))
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(vertical = 12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Bagikan", fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
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
    userEmail: String
)
{
    val isWishlisted by wishlistViewModel.isWishlisted(game.id).collectAsState(initial = false)
    val reviews by reviewViewModel.getReviewsByGame(game.id).collectAsState(initial = emptyList())
    var showReviewDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            // Gambar Game dengan Clip Corner Atas & Aspek Rasio proporsional
            Image(
                painter = painterResource(id = game.imageResId),
                contentDescription = game.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(16.dp)) {
                // Judul Game & Rating Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = game.name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    // Rating Bagian Atas
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = game.rating.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                // Deskripsi singkat game jika ada (opsional, tapi menambahkan sentuhan modern)
                Text(
                    text = game.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Divider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                // Detail Atribut Informasi Game (Compact Layout)
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Genre & Mood: ${game.genre} • ${game.mood}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Platform: ${game.platforms.joinToString()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Mode: ${game.modes.joinToString()}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Info Komunitas / Review User
                if (reviews.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "💬 ${reviews.size} Ulasan Pengguna • Rata-rata: ${"%.1f".format(reviews.map { it.rating }.average())}★",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Medium),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Kolom Button Aksi yang Lebih Responsif & Indah
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
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
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = if (isWishlisted) ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)) else ButtonDefaults.outlinedButtonColors()
                    ) {
                        Icon(
                            imageVector = if (isWishlisted) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = if (isWishlisted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isWishlisted) "Disukai" else "Wishlist",
                            color = if (isWishlisted) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }

                    Button(
                        onClick = { showReviewDialog = true },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.RateReview,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Review",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                        )
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
                        userName = userEmail.substringBefore("@"),
                        userEmail = userEmail,
                        reviewText = reviewText,
                        rating = rating
                    )
                )
                showReviewDialog = false
            }
        )
    }
}
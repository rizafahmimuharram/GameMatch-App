package com.rizafahmi0093.gamematch.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rizafahmi0093.gamematch.R
import com.rizafahmi0093.gamematch.model.TrendingGame
import com.rizafahmi0093.gamematch.network.ApiStatus
import com.rizafahmi0093.gamematch.viewmodel.TrendingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingScreen(navController: NavController) {
    val viewModel: TrendingViewModel = viewModel()
    val data by viewModel.data.collectAsState()
    val status by viewModel.status.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(

                title = {
                    Text(
                        text = "Trending Games",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { padding ->
        when (status) {
            ApiStatus.LOADING -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            ApiStatus.SUCCESS -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(data) { TrendingItem(it) }
                }
            }

            ApiStatus.FAILED -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Gagal memuat data.. Koneksi error.")
                    Button(
                        onClick = { viewModel.retrieveData() },
                        modifier = Modifier.padding(top = 16.dp),
                        contentPadding = PaddingValues(
                            horizontal = 32.dp,
                            vertical = 16.dp
                        )
                    ) {
                        Text("Coba lagi")
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingItem(game: TrendingGame) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = game.thumbnail,
                contentDescription = game.title,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.baseline_broken_image_24),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = game.title,         // ← ganti dari name
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2
                )
                Text(
                    text = game.genre,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1
                )
                Text(
                    text = game.platform,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
        }
    }
}
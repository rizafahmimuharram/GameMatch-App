package com.rizafahmi0093.gamematch.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rizafahmi0093.gamematch.R
import com.rizafahmi0093.gamematch.model.PostResponse
import com.rizafahmi0093.gamematch.model.Review
import com.rizafahmi0093.gamematch.model.User
import com.rizafahmi0093.gamematch.navigation.Screen
import com.rizafahmi0093.gamematch.network.UserDataStore
import com.rizafahmi0093.gamematch.ui.components.GameMatchTopBar
import com.rizafahmi0093.gamematch.ui.components.signOut
import com.rizafahmi0093.gamematch.util.ViewModelFactory
import com.rizafahmi0093.gamematch.viewmodel.PostViewModel
import com.rizafahmi0093.gamematch.viewmodel.ReviewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val userDataStore = UserDataStore(context)
    val user by userDataStore.userFlow.collectAsState(initial = User())
    var showDialog by remember { mutableStateOf(false) }

    val postViewModel: PostViewModel = viewModel(factory = ViewModelFactory(context))
    val reviewViewModel: ReviewViewModel = viewModel(factory = ViewModelFactory(context))

    val allPosts by postViewModel.posts.collectAsState()
    val myPosts = allPosts.filter { it.userEmail == user.email }
    val myReviews by reviewViewModel.getReviewsByUser(user.name)
        .collectAsState(initial = emptyList())

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Postingan", "Review")

    Scaffold(
        topBar = {
            GameMatchTopBar(
                title = "Profil",
                showBackButton = true,
                onBackClick = { navController.popBackStack() },
                onProfilClick = { showDialog = true }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Header profil
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(user.photoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.loading_img),
                        error = painterResource(R.drawable.baseline_broken_image_24),
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${myPosts.size}",
                                fontWeight = FontWeight.Bold
                            )
                            Text("Postingan", style = MaterialTheme.typography.bodySmall)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${myReviews.size}",
                                fontWeight = FontWeight.Bold
                            )
                            Text("Review", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
                HorizontalDivider()
            }

            // Tab Postingan / Review
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab])
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }
            }


            if (selectedTab == 0) {
                if (myPosts.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Belum ada postingan")
                        }
                    }
                } else {
                    items(myPosts) { post ->
                        ProfilePostItem(post = post)
                    }
                }
            } else {
                // Tab Review
                if (myReviews.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Belum ada review")
                        }
                    }
                } else {
                    items(myReviews) { review ->
                        ProfileReviewItem(review = review)
                    }
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
            }
        )
    }
}

@Composable
fun ProfilePostItem(post: PostResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            if (post.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = post.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Text(text = post.caption, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = post.createdAt.take(10),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun ProfileReviewItem(review: Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = review.gameName,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = "${"⭐".repeat(review.rating)}")
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = review.reviewText)
        }
    }
}
package com.rizafahmi0093.gamematch.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rizafahmi0093.gamematch.model.Post
import com.rizafahmi0093.gamematch.model.PostResponse
import com.rizafahmi0093.gamematch.model.User
import com.rizafahmi0093.gamematch.navigation.Screen
import com.rizafahmi0093.gamematch.network.UserDataStore
import com.rizafahmi0093.gamematch.ui.components.GameMatchTopBar
import com.rizafahmi0093.gamematch.ui.components.signOut
import com.rizafahmi0093.gamematch.util.ViewModelFactory
import com.rizafahmi0093.gamematch.viewmodel.PostViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import com.rizafahmi0093.gamematch.network.ApiStatus
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import com.rizafahmi0093.gamematch.model.Comment
import com.rizafahmi0093.gamematch.viewmodel.CommentViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(navController: NavController) {
    val context = LocalContext.current
    val userDataStore = UserDataStore(context)
    val user by userDataStore.userFlow.collectAsState(initial = User())
    var showDialog by remember { mutableStateOf(false) }
    var showEditName by remember { mutableStateOf(false) }

    val postViewModel: PostViewModel = viewModel(factory = ViewModelFactory(context))
    val posts by postViewModel.posts.collectAsState()
    val status by postViewModel.status.collectAsState()

    Scaffold(
        topBar = {
            GameMatchTopBar(
                title = "Feed Komunitas",
                showBackButton = true,
                onBackClick = { navController.popBackStack() },
                onProfilClick = { showDialog = true }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.CreatePost.route) }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Buat Postingan"
                )
            }
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
            ApiStatus.FAILED -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Gagal memuat feed")
                    Button(
                        onClick = { postViewModel.loadPosts() },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Coba lagi")
                    }
                }
            }
            ApiStatus.SUCCESS -> {
                if (posts.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Belum ada postingan")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Jadilah yang pertama posting!",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                } else {
                    PullToRefreshBox(

                        isRefreshing = status == ApiStatus.LOADING,
                        onRefresh = { postViewModel.loadPosts() }
                    ) {
                        LazyColumn(

                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding),
                            contentPadding = PaddingValues(bottom = 80.dp)
                        )
                        {
                            items(posts) { post ->
                                PostCard(
                                    post = post,
                                    currentUserEmail = user.email,
                                    onDelete = {
                                        postViewModel.deletePost(post.id, user.email)
                                    }
                                )
                            }
                        }
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
fun PostCard(
    post: PostResponse,
    currentUserEmail: String,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val commentViewModel: CommentViewModel = viewModel(factory = ViewModelFactory(context))
    val comments by commentViewModel.getCommentsByPost(post.id)
        .collectAsState(initial = emptyList())
    val userDataStore = UserDataStore(context)
    val user by userDataStore.userFlow.collectAsState(initial = User())

    var showConfirmDelete by remember { mutableStateOf(false) }
    var showComments by remember { mutableStateOf(false) }
    var commentText by remember { mutableStateOf("") }
    var commentError by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            if (post.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = post.imageUrl,
                    contentDescription = "Screenshot gameplay",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = post.userName,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (post.userEmail == currentUserEmail) {
                        IconButton(onClick = { showConfirmDelete = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Hapus",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = post.caption)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = post.createdAt.take(10),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )

                // tombol komentar
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = { showComments = !showComments }
                ) {
                    Text(
                        if (showComments) "Sembunyikan komentar"
                        else "💬 ${comments.size} Komentar"
                    )
                }

                // section komentar
                if (showComments) {
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))

                    // list komentar
                    comments.forEach { comment ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = comment.userName,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = comment.commentText,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // input komentar baru
                    OutlinedTextField(
                        value = commentText,
                        onValueChange = {
                            commentText = it
                            commentError = false
                        },
                        label = { Text("Tulis komentar") },
                        placeholder = { Text("Game ini bagus...") },
                        isError = commentError,
                        supportingText = {
                            if (commentError) Text("Komentar tidak boleh kosong")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    if (commentText.isEmpty()) {
                                        commentError = true
                                        return@IconButton
                                    }
                                    commentViewModel.addComment(
                                        Comment(
                                            postId = post.id,
                                            userName = user.name,
                                            commentText = commentText
                                        )
                                    )
                                    commentText = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Kirim"
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    if (showConfirmDelete) {
        AlertDialog(
            onDismissRequest = { showConfirmDelete = false },
            title = { Text("Hapus Postingan") },
            text = { Text("Yakin ingin menghapus postingan ini?") },
            confirmButton = {
                TextButton(onClick = { onDelete(); showConfirmDelete = false }) {
                    Text("Hapus", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDelete = false }) {
                    Text("Batal")
                }
            }
        )
    }
}
package com.rizafahmi0093.gamematch.ui.screen

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.rizafahmi0093.gamematch.R
import com.rizafahmi0093.gamematch.model.User
import com.rizafahmi0093.gamematch.navigation.Screen
import com.rizafahmi0093.gamematch.network.UserDataStore
import com.rizafahmi0093.gamematch.ui.components.GameMatchTopBar
import com.rizafahmi0093.gamematch.util.ViewModelFactory
import com.rizafahmi0093.gamematch.viewmodel.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(navController: NavController) {
    val context = LocalContext.current
    val dataStore = UserDataStore(context)
    val user by dataStore.userFlow.collectAsState(initial = User())


    val postViewModel: PostViewModel = viewModel(factory = ViewModelFactory(context))

    var caption by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var captionError by remember { mutableStateOf(false) }
    var isUploading by remember { mutableStateOf(false) }          // ← state loading
    var uploadProgress by remember { mutableStateOf("") }         // ← pesan progress

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    // observe uploadSuccess dari ViewModel → navigate kalau berhasil
    val uploadSuccess by postViewModel.uploadSuccess.collectAsState()
    LaunchedEffect(uploadSuccess) {
        if (uploadSuccess) {
            postViewModel.resetUploadSuccess()
            navController.navigate(Screen.Feed.route) {
                popUpTo(Screen.Feed.route) { inclusive = true }
            }
        }
    }

    val errorMessage by postViewModel.errorMessage
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            postViewModel.clearError()
            isUploading = false   // reset loading state
        }
    }

    Scaffold(
        topBar = {
            GameMatchTopBar(
                title = "Buat Postingan",
                showBackButton = !isUploading,  // nonaktifkan back saat upload
                onBackClick = { navController.popBackStack() },
                onProfilClick = {}
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // area upload gambar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
                        .clickable(enabled = !isUploading) { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Screenshot gameplay",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.baseline_broken_image_24),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = "Tap untuk upload screenshot gameplay",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = caption,
                    onValueChange = {
                        caption = it
                        captionError = false
                    },
                    label = { Text("Caption") },
                    placeholder = { Text("Ceritakan pengalaman bermain kamu...") },
                    isError = captionError,
                    supportingText = {
                        if (captionError) Text("Caption tidak boleh kosong")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4,
                    enabled = !isUploading
                )

                Button(
                    onClick = {
                        if (caption.isEmpty()) {
                            captionError = true
                            return@Button
                        }
                        isUploading = true
                        uploadProgress = if (imageUri != null)
                            "Mengupload gambar..." else "Menyimpan postingan..."

                        postViewModel.createPost(
                            userEmail = user.email,
                            userName = user.name,
                            caption = caption,
                            imageUri = imageUri
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isUploading
                ) {
                    if (isUploading) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(uploadProgress)
                        }
                    } else {
                        Text("Posting")
                    }
                }
            }
        }
    }
}
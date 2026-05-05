package com.rizafahmi0093.gamematch.ui.screen

import android.widget.Toast
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.rizafahmi0093.gamematch.util.ViewModelFactory
import com.rizafahmi0093.gamematch.ui.theme.GameMatchTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavHostController,
    id: Long? = null
) {
    val context = LocalContext.current
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var player1 by remember { mutableStateOf("") }
    var player2 by remember { mutableStateOf("") }
    var score by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getById(id) ?: return@LaunchedEffect
        player1 = data.player1
        player2 = data.player2
        score = data.score
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(if (id == null) "Tambah Match" else "Edit Match")
                },
                actions = {

                    IconButton(onClick = {
                        if (player1.isEmpty() || player2.isEmpty() || score.isEmpty()) {
                            Toast.makeText(context, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        if (id == null) {
                            viewModel.insert(player1, player2, score)
                        } else {
                            viewModel.update(id, player1, player2, score)
                        }

                        navController.popBackStack()
                    }) {
                        Icon(Icons.Outlined.Check, contentDescription = "Save")
                    }


                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = player1,
                onValueChange = { player1 = it },
                label = { Text("Player 1") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = player2,
                onValueChange = { player2 = it },
                label = { Text("Player 2") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = score,
                onValueChange = { score = it },
                label = { Text("Score (contoh: 2-1)") },
                modifier = Modifier.fillMaxWidth()
            )
        }


        if (id != null && showDialog) {
            DisplayAlertDialog(
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    showDialog = false
                    viewModel.delete(id)
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Filled.MoreVert, contentDescription = "Menu")

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Hapus") },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun DisplayAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    AlertDialog(
        text = { Text("Yakin ingin menghapus data ini?") },
        confirmButton = {
            TextButton(onClick = onConfirmation) {
                Text("Hapus")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Batal")
            }
        },
        onDismissRequest = onDismissRequest
    )
}
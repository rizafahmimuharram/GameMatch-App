package com.rizafahmi0093.gamematch.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rizafahmi0093.gamematch.R
import com.rizafahmi0093.gamematch.model.User
import com.rizafahmi0093.gamematch.navigation.Screen
import com.rizafahmi0093.gamematch.network.UserDataStore
import com.rizafahmi0093.gamematch.ui.components.GameMatchTopBar
import com.rizafahmi0093.gamematch.ui.components.signOut
import com.rizafahmi0093.gamematch.util.SettingsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("LocalContextGetResourceValueCall")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val userDataStore = UserDataStore(context)
    val user by userDataStore.userFlow.collectAsState(initial = User())
    var showDialog by remember { mutableStateOf(false) }
    var showEditName by remember { mutableStateOf(false) }

    var selectedGenres by rememberSaveable {
        mutableStateOf("")
    }
    var selectedMood by rememberSaveable {
        mutableStateOf("")
    }
    var selectedPlatform by rememberSaveable {
        mutableStateOf("")
    }
    var selectedRating by rememberSaveable {
        mutableStateOf("")
    }
    var selectedMode by rememberSaveable {
        mutableStateOf("")
    }
    var errorMessage by rememberSaveable {
        mutableStateOf("")
    }
    var currentStep by remember {
        mutableIntStateOf(0)
    }
    val genres = listOf(
        "Action",
        "RPG",
        "Simulation",
        "Horror",
        "Sports"
    )
    val moods = listOf(
        "Relaxed",
        "Tense",
        "Competitive",
        "Casual",
        "Serious"
    )
    val platforms = listOf(
        "PC",
        "Mobile",
        "Console"
    )
    val ratings = listOf(
        "4.5",
        "4.0",
        "Any"
    )
    val modes = listOf(
        "Single",
        "Multi",
        "Coop"
    )
    val scope = rememberCoroutineScope()


    val dataStore = SettingsDataStore(context)

    val isDarkMode by
    dataStore.themeFlow.collectAsState(
        initial = false
    )
    val emptyError =
        stringResource(R.string.error_empty)

    Scaffold(
        topBar = {
            GameMatchTopBar(
                title = stringResource(R.string.title_step),
                showBackButton = currentStep > 0,
                onBackClick = { currentStep-- },
                onProfilClick = { showDialog = true }
            )
        }


    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Top
        ) {
            if (currentStep == 0) {
                Card(
                    Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    RoundedCornerShape(16.dp),
                    CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Selamat datang kembali",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Hello, ${user.name} 👾",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

            }
            when (currentStep) {
                0 -> {
                    SectionLabel(
                        stringResource(R.string.choose_genre)
                    )
                    genres.forEach { item ->

                        SelectionCard(
                            label = item,
                            isSelected =
                                selectedGenres == item
                        ) {

                            selectedGenres = item

                            scope.launch {
                                delay(150)
                                currentStep++
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))


                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.height(180.dp)
                    ) {
                        item {
                            MenuCard(icon = Icons.Default.Whatshot, label = "Trending", sub = "Game terpopuler") { navController.navigate(Screen.Trending.route) }
                        }
                        item {
                            MenuCard(icon = Icons.Default.Favorite, label = "Wishlist", sub = "Simpanan kamu") { navController.navigate(Screen.Wishlist.route) }
                        }
                        item {
                            MenuCard(icon = Icons.Default.People, label = "Feed", sub = "Komunitas") { navController.navigate(Screen.Feed.route) }
                        }
                        item {
                            MenuCard(icon = Icons.Default.History, label = "Riwayat", sub = "pertandingan lalu") { navController.navigate(Screen.Main.route) }
                        }
                    }

                    Button(
                        onClick = { navController.navigate(Screen.Profile.route) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Profil")
                    }

                }

                1 -> {

                    SectionLabel(
                        stringResource(R.string.choose_mood)
                    )
                    moods.forEach { item ->

                        SelectionCard(
                            label = item,
                            isSelected =
                                selectedMood == item
                        ) {

                            selectedMood = item

                            scope.launch {
                                delay(150)
                                currentStep++
                            }
                        }
                    }
                }

                2 -> {

                    SectionLabel(
                        stringResource(R.string.choose_platform)
                    )

                    platforms.forEach { item ->

                        SelectionCard(
                            label = item,
                            isSelected =
                                selectedPlatform == item
                        ) {

                            selectedPlatform = item

                            scope.launch {
                                delay(150)
                                currentStep++

                            }
                        }
                    }
                }

                3 -> {

                    SectionLabel(
                        stringResource(R.string.choose_rating)
                    )

                    ratings.forEach { item ->

                        SelectionCard(
                            label = item,
                            isSelected =
                                selectedRating == item
                        ) {

                            selectedRating = item

                            scope.launch {
                                delay(150)
                                currentStep++
                            }
                        }
                    }
                }

                4 -> { SectionLabel(
                        stringResource(R.string.choose_mode)
                    )
                    modes.forEach { item ->
                        SelectionCard(
                            label = item,
                            isSelected =
                                selectedMode == item
                        ) {

                            selectedMode = item
                        }
                    }
                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )


                    Button(
                        onClick = {
                            if (
                                selectedGenres.isEmpty() ||
                                selectedMood.isEmpty() ||
                                selectedPlatform.isEmpty() ||
                                selectedRating.isEmpty() ||
                                selectedMode.isEmpty()
                            ) {
                                errorMessage = emptyError
                            } else {
                                errorMessage = ""
                                navController.navigate(
                                    Screen.Result.withArgs(
                                        selectedGenres,
                                        selectedMood,
                                        selectedPlatform,
                                        selectedRating,
                                        selectedMode
                                    )
                                )
                            }
                        },

                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            stringResource(
                                R.string.see_result
                            )
                        )
                    }
                    if (errorMessage.isNotEmpty()) {

                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(top = 8.dp)
                        )
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
fun SectionLabel(text: String) {
    Text(text, style = MaterialTheme.typography.labelLarge, modifier = Modifier.padding(top = 4.dp))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionCard(label: String, isSelected: Boolean, onClick: () -> Unit) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
        ),
        onClick = onClick
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(16.dp),
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun MenuCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    sub: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(text = label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                Text(text = sub, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
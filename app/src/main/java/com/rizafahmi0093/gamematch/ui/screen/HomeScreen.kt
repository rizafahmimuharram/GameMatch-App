package com.rizafahmi0093.gamematch.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
    navController: NavController,
    name: String
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
                title = stringResource(R.string.title_step, currentStep + 1),
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

                Text(
                    text = stringResource(
                        R.string.greeting,
                        name
                    ),
                    style =
                        MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

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

                    OutlinedButton(
                        onClick = {
                            navController.navigate(Screen.Main.route)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.history)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            navController.navigate(Screen.Trending.route)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Trending Games")
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
            onChangeName = {
                showDialog = false
                showEditName = true
            },
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
            currentName = if (user.customName.isNotEmpty()) user.customName else user.name,
            onDismiss = { showEditName = false },
            onSave = { newName ->
                CoroutineScope(Dispatchers.IO).launch {
                    userDataStore.updateCustomName(newName)
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
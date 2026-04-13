package com.rizafahmi0093.gamematch.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource
import com.rizafahmi0093.gamematch.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(navController: NavController, name: String) {

    var selectedGenres by rememberSaveable  { mutableStateOf("") }
    var selectedMood by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    var selectedPlatform by rememberSaveable { mutableStateOf("") }
    var selectedRating by rememberSaveable { mutableStateOf("") }
    var selectedMode by rememberSaveable { mutableStateOf("") }

    var currentStep by remember { mutableIntStateOf(0) }

    val genres = listOf("Action", "RPG", "Simulation", "Horror", "Sports")
    val moods = listOf("Santai", "Tegang", "Competitive", "Casual", "Serius")
    val platforms = listOf("PC", "Mobile", "Console")
    val ratings = listOf("4.5", "4.0", "Any")
    val modes = listOf("Single", "Multi", "Coop")

    Scaffold(
        topBar = {

            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "${stringResource(R.string.title_step, currentStep + 1)},/5)",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },

                navigationIcon = {
                    if (currentStep > 0) {
                        IconButton(onClick = { currentStep-- }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )


        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (currentStep == 0) {
                Text(
                    text = stringResource(R.string.greeting, name),
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(8.dp))
            when (currentStep) {
                0 -> {
                    SectionLabel(stringResource(R.string.choose_genre))
                    genres.forEach { item ->
                        SelectionCard(item, selectedGenres == item) {
                            selectedGenres = item
                            currentStep++
                        }
                    }
                }

                1 -> {
                    SectionLabel(stringResource(R.string.choose_mood))
                    moods.forEach { item ->
                        SelectionCard(item, selectedMood == item) {
                            selectedMood = item
                            currentStep++
                        }
                    }
                }

                2 -> {
                    SectionLabel(stringResource(R.string.choose_platform))
                    platforms.forEach { item ->
                        SelectionCard(item, selectedPlatform == item) {
                            selectedPlatform = item
                            currentStep++
                        }
                    }
                }

                3 -> {
                    SectionLabel(stringResource(R.string.choose_rating))
                    ratings.forEach { item ->
                        SelectionCard(item, selectedRating == item) {
                            selectedRating = item
                            currentStep++
                        }
                    }
                }

                4 -> {
                    SectionLabel(stringResource(R.string.choose_mode))
                    modes.forEach { item ->
                        SelectionCard(item, selectedMode == item) {
                            selectedMode = item
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    val context = LocalContext.current

                    Button(
                        onClick = {
                            if (selectedGenres.isEmpty() ||
                                selectedMood.isEmpty() ||
                                selectedPlatform.isEmpty() ||
                                selectedRating.isEmpty() ||
                                selectedMode.isEmpty()
                            ) {
                                errorMessage = context.getString(R.string.error_empty)
                            } else {
                                errorMessage = ""

                                navController.navigate(
                                    "result/$selectedGenres/$selectedMood/$selectedPlatform/$selectedRating/$selectedMode"
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.see_result))
                    }
                    if (errorMessage.isNotEmpty()) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    }
                }
            }


            if (currentStep > 0) {
                androidx.compose.material3.TextButton(
                    onClick = { currentStep-- },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("< Kembali")
                }
            }
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
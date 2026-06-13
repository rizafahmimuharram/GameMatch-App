package com.rizafahmi0093.gamematch.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun ReviewDialog(
    gameName: String,
    onDismiss: () -> Unit,
    onSubmit: (reviewText: String, rating: Int) -> Unit
) {
    var reviewText by remember { mutableStateOf("") }
    var selectedRating by remember { mutableStateOf(0) }
    var reviewError by remember { mutableStateOf(false) }
    var ratingError by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Review: $gameName",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                // Rating bintang 1-5
                Text("Rating:")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    (1..5).forEach { star ->
                        TextButton(
                            onClick = {
                                selectedRating = star
                                ratingError = false
                            },
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            Text(
                                text = if (star <= selectedRating) "⭐" else "☆",
                                fontSize = 24.sp
                            )
                        }
                    }
                }
                if (ratingError) {
                    Text(
                        text = "Pilih rating dulu",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Input review
                OutlinedTextField(
                    value = reviewText,
                    onValueChange = {
                        reviewText = it
                        reviewError = false
                    },
                    label = { Text("Tulis review kamu") },
                    placeholder = { Text("Gameplay sangat seru...") },
                    isError = reviewError,
                    supportingText = {
                        if (reviewError) Text("Review tidak boleh kosong")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )

                // Tombol
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(onClick = onDismiss) {
                        Text("Batal")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            var valid = true
                            if (reviewText.isEmpty()) {
                                reviewError = true
                                valid = false
                            }
                            if (selectedRating == 0) {
                                ratingError = true
                                valid = false
                            }
                            if (valid) onSubmit(reviewText, selectedRating)
                        }
                    ) {
                        Text("Simpan")
                    }
                }
            }
        }
    }
}
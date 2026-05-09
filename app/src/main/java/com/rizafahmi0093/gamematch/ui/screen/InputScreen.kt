package com.rizafahmi0093.gamematch.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource
import com.rizafahmi0093.gamematch.R

@Composable
fun InputScreen(navController: NavController) {

    var name by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.input_title),
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text(
                    stringResource(R.string.input_label_name)
                )
            }
        )

        Button(
            onClick = {

                if (name.isEmpty()) {

                    Toast.makeText(
                        context,
                        "Name is required",
                        Toast.LENGTH_LONG
                    ).show()

                    return@Button
                }

                navController.navigate(
                    "home/$name"
                )
            },

            modifier = Modifier.padding(top = 16.dp)
        ) {

            Text(
                stringResource(R.string.input_button_next)
            )
        }
    }
}
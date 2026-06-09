package com.rizafahmi0093.gamematch.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rizafahmi0093.gamematch.R
import androidx.compose.runtime.collectAsState
import com.rizafahmi0093.gamematch.model.User
import com.rizafahmi0093.gamematch.network.UserDataStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputScreen(navController: NavController) {

    val context = LocalContext.current
    val dataStore = UserDataStore(context)
    val user by dataStore.userFlow.collectAsState(initial = User())
    var name by rememberSaveable(user.name) { mutableStateOf(user.name) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(
            text = stringResource(R.string.input_title).uppercase(),
            fontSize = 18.sp,
            color = Color(0xFF6015EB),
            textAlign = TextAlign.Center,
            letterSpacing = 2.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )


        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
            label = {
                Text(
                    text = stringResource(R.string.input_label_name),
                    fontSize = 10.sp,
                    color = Color(0xFFE040FB)
                )
            },

            shape = CutCornerShape(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),

            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6015EB),
                unfocusedBorderColor = Color(0xFF444444),
                focusedLabelColor = Color(0xFFE040FB),
                unfocusedLabelColor = Color(0xFF444444),
                cursorColor = Color(0xFF00E5FF)
            )
        )

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = {
                if (name.isEmpty()) {
                    Toast.makeText(context, "NAME IS REQUIRED!", Toast.LENGTH_LONG).show()
                    return@Button
                }
                navController.navigate("home/$name")
            },

            shape = CutCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6015EB),
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .border(2.dp, Color(0xFF00E5FF), CutCornerShape(0.dp))
        ) {
            Text(
                text = stringResource(R.string.input_button_next).uppercase(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}
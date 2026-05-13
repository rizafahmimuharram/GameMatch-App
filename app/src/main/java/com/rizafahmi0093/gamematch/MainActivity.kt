    package com.rizafahmi0093.gamematch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.rizafahmi0093.gamematch.navigation.SetupNavGraph
import com.rizafahmi0093.gamematch.ui.theme.GameMatchTheme
import com.rizafahmi0093.gamematch.util.SettingsDataStore

    class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context = LocalContext.current
            val dataStore = SettingsDataStore(context)

            val isDarkMode by dataStore.themeFlow
                .collectAsState(initial = false)

            GameMatchTheme(
                darkTheme = isDarkMode
            ) {
                SetupNavGraph()
            }
        }
    }
}






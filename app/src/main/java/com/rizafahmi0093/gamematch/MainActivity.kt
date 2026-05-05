    package com.rizafahmi0093.gamematch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rizafahmi0093.gamematch.navigation.SetupNavGraph
import com.rizafahmi0093.gamematch.ui.theme.GameMatchTheme

    class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GameMatchTheme {
                SetupNavGraph()
            }
        }
    }
}






package com.rizafahmi0093.gamematch.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.rizafahmi0093.gamematch.data.GameRepository
import com.rizafahmi0093.gamematch.model.Game

class GameViewModel : ViewModel() {


    var recommendedGames = mutableStateOf<List<Game>>(emptyList())
        private set


    fun loadGames(
        genre: String,
        mood: String,
        platform: String,
        rating: String,
        mode: String
    ) {
        recommendedGames.value = GameRepository.getRecommendedGames(
            genre,
            mood,
            platform,
            rating,
            mode
        )
    }
}
package com.rizafahmi0093.gamematch.viewmodel

import androidx.lifecycle.ViewModel
import com.rizafahmi0093.gamematch.data.GameRepository
import com.rizafahmi0093.gamematch.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel : ViewModel() {

    private val _recommendedGames =
        MutableStateFlow<List<Game>>(emptyList())

    val recommendedGames =
        _recommendedGames.asStateFlow()

    fun loadGames(
        genre: String,
        mood: String,
        platform: String,
        rating: String,
        mode: String
    ) {

        _recommendedGames.value =
            GameRepository.getRecommendedGames(
                genre,
                mood,
                platform,
                rating,
                mode
            )
    }
}
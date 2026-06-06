package com.rizafahmi0093.gamematch.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizafahmi0093.gamematch.model.TrendingGame
import com.rizafahmi0093.gamematch.network.ApiStatus
import com.rizafahmi0093.gamematch.network.TrendingApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TrendingViewModel : ViewModel() {

    var data = MutableStateFlow<List<TrendingGame>>(emptyList())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    init {
        retrieveData()
    }

    fun retrieveData() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                data.value = TrendingApi.service.getTrendingGames()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.d("TrendingViewModel", "Failure: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }
}
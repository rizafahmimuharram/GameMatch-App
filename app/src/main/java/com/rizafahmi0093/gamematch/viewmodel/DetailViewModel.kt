package com.rizafahmi0093.gamematch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizafahmi0093.gamematch.database.MatchDao
import com.rizafahmi0093.gamematch.model.Match
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(
    private val dao: MatchDao
) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)


    fun insert(player1: String, player2: String, score: String) {
        val data = Match(
            player1 = player1,
            player2 = player2,
            score = score,
            date = formatter.format(Date())
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(data)
        }
    }


    suspend fun getById(id: Long): Match? {
        return dao.getById(id)
    }


    fun update(id: Long, player1: String, player2: String, score: String) {
        val data = Match(
            id = id,
            player1 = player1,
            player2 = player2,
            score = score,
            date = formatter.format(Date())
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(data)
        }
    }


    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}
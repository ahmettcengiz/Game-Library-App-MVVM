package com.ahmethamdicengiz.appcentproject.local

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ahmethamdicengiz.appcentproject.model.FavoriteGameEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteGameViewModel(application: Application) : AndroidViewModel(application) {

    val allFavoriteGameDetail: LiveData<List<FavoriteGameEntity>>

    private val repository: FavoriteGameRepository

    init {
        val favoriteGameDao = LocalDatabase.getAppDatabase(application)?.favoriteGameDao()
        repository = FavoriteGameRepository(favoriteGameDao!!)
        allFavoriteGameDetail = repository.allFavoriteGameDetail
    }

    fun insertFavoriteGameDetail(favoriteGameEntity: FavoriteGameEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteGameDetail(favoriteGameEntity)
        }
    }

    fun deleteFavoriteGameDetail(favoriteGameEntity: FavoriteGameEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteGameDetail(favoriteGameEntity)
        }
    }

}
package com.ahmethamdicengiz.appcentproject.local

import androidx.lifecycle.LiveData
import com.ahmethamdicengiz.appcentproject.model.FavoriteGameEntity

class FavoriteGameRepository(private val favoriteGameDao: FavoriteGameDao) {

    val allFavoriteGameDetail: LiveData<List<FavoriteGameEntity>> = favoriteGameDao.findAll()

    fun insertFavoriteGameDetail(favoriteGameEntity: FavoriteGameEntity) {
        favoriteGameDao.insert(favoriteGameEntity)
    }

    fun deleteFavoriteGameDetail(favoriteGameEntity: FavoriteGameEntity) {
        favoriteGameDao.delete(favoriteGameEntity)
    }
}
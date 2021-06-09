package com.ahmethamdicengiz.appcentproject.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ahmethamdicengiz.appcentproject.model.FavoriteGameEntity

@Dao
interface FavoriteGameDao {
    @Query("SELECT * FROM FavoriteGame ORDER BY ID ASC")
    fun findAll(): LiveData<List<FavoriteGameEntity>>

    @Delete
    fun delete(favoriteGameEntity: FavoriteGameEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteGameEntity: FavoriteGameEntity)
}
package com.ahmethamdicengiz.appcentproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteGame")
data class FavoriteGameEntity(
    @PrimaryKey @ColumnInfo(name = "ID") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "metacritic") val metacritic: String,
    @ColumnInfo(name = "released") val released: String,
    @ColumnInfo(name = "backgroundImage") val backgroundImage: String,
    @ColumnInfo(name = "rating") val rating: Float

)
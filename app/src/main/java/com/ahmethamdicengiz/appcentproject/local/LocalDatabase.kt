package com.ahmethamdicengiz.appcentproject.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahmethamdicengiz.appcentproject.model.FavoriteGameEntity

const val DB_VERSION = 1

@Database(entities = [FavoriteGameEntity::class], version = DB_VERSION, exportSchema = true)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun favoriteGameDao(): FavoriteGameDao

    companion object {
        private var INSTANCE: LocalDatabase? = null
        fun getAppDatabase(context: Context): LocalDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<LocalDatabase>(
                    context.applicationContext, LocalDatabase::class.java, "FavoriteGameDetailDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }
    }
}
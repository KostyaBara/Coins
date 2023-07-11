package com.example.coins.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        CoinEntity::class
    ],
    version = 1
)
abstract class MainDb : RoomDatabase() {
    abstract val coinDao: CoinDao

    companion object {
        fun createDataBase(context: Context): MainDb {
            return Room.databaseBuilder(
                context,
                MainDb::class.java,
                "test.db"
            ).build()
        }
    }
}
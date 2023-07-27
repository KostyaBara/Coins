package com.example.coins.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coin: CoinEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<CoinEntity>)

    @Delete
    suspend fun delete(coinEntity: CoinEntity)

    @Query("SELECT * FROM Coins")
    suspend fun getAll(): List<CoinEntity>

    @Query("SELECT * FROM Coins")
    fun observeAll(): Flow<List<CoinEntity>>

    @Query("SELECT * FROM Coins WHERE isFavorite == 1")
    fun observeFavorite(): Flow<List<CoinEntity>>

    @Query("SELECT * FROM Coins WHERE id == :id")
    suspend fun getCoin(id: String): CoinEntity?

    @Query("SELECT * FROM Coins WHERE id == :id")
    fun observeCoin(id: String): Flow<CoinEntity>

    @Query("UPDATE Coins SET isFavorite = :isFavorite WHERE id == :id")
    suspend fun updateFavorite(id: String, isFavorite: Boolean)

    @Query("SELECT isFavorite FROM Coins WHERE id == :id")
    suspend fun isFavorite(id: String): Boolean?
}
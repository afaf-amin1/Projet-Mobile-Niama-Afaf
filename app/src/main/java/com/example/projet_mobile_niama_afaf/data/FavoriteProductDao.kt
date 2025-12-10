package com.example.projet_mobile_niama_afaf.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteProduct: FavoriteProduct)

    @Delete
    suspend fun delete(favoriteProduct: FavoriteProduct)

    @Query("SELECT * FROM favorite_products")
    fun getAll(): Flow<List<FavoriteProduct>>

    @Query("SELECT * FROM favorite_products WHERE productId = :productId")
    fun getById(productId: String): Flow<FavoriteProduct?>
}

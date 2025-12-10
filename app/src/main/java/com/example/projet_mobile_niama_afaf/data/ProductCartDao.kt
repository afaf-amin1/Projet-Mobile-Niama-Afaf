package com.example.projet_mobile_niama_afaf.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductCartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductCart)

    @Update
    suspend fun update(product: ProductCart)

    @Delete
    suspend fun delete(product: ProductCart)

    @Query("SELECT * FROM product_cart")
    fun getAll(): Flow<List<ProductCart>>

    @Query("DELETE FROM product_cart")
    suspend fun deleteAll()
}

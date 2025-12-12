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
    // Inserts or updates an item. If the item already exists, it replaces it.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductCart)

    @Update
    suspend fun update(product: ProductCart)

    @Delete
    suspend fun delete(product: ProductCart)

    // Gets all cart items for a specific user.
    @Query("SELECT * FROM product_cart WHERE userEmail = :userEmail")
    fun getCartForUser(userEmail: String): Flow<List<ProductCart>>

    // Deletes all cart items for a specific user.
    @Query("DELETE FROM product_cart WHERE userEmail = :userEmail")
    suspend fun clearCartForUser(userEmail: String)
}

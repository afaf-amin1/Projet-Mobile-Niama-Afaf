package com.example.projet_mobile_niama_afaf.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProduct(
    @PrimaryKey
    val productId: String
)

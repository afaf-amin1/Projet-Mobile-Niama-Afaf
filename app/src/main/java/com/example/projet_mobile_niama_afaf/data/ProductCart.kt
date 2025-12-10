package com.example.projet_mobile_niama_afaf.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_cart")
data class ProductCart(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val productId: String,
    val quantity: Int
)

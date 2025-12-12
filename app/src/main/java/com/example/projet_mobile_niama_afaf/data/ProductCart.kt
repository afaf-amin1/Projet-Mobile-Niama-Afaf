package com.example.projet_mobile_niama_afaf.data

import androidx.room.Entity
import androidx.room.ForeignKey

// We define a composite primary key to ensure a user can have only one entry for each product.
@Entity(
    tableName = "product_cart",
    primaryKeys = ["userEmail", "productId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["email"],
            childColumns = ["userEmail"],
            onDelete = ForeignKey.CASCADE // If a user is deleted, their cart items are also deleted.
        )
    ]
)
data class ProductCart(
    val userEmail: String,
    val productId: String,
    var quantity: Int
)

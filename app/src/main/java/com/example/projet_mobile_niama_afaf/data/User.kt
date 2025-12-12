package com.example.projet_mobile_niama_afaf.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

// Add a unique index to the email column to enforce that each email can only be used once.
// This is also required for the foreign key relationship from ProductCart.
@Entity(tableName = "users", indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val name: String
)

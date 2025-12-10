package com.example.projet_mobile_niama_afaf

import android.app.Application
import com.example.projet_mobile_niama_afaf.data.AppDatabase
import com.example.projet_mobile_niama_afaf.data.AppRepository

class MyApplication : Application() {
    // Using by lazy so the database and repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { AppRepository(database.userDao(), database.productCartDao(), database.favoriteProductDao()) }
}

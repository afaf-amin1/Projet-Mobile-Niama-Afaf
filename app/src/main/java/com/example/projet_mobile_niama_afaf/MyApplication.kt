package com.example.projet_mobile_niama_afaf

import android.app.Application
import com.example.projet_mobile_niama_afaf.data.AppDatabase
import com.example.projet_mobile_niama_afaf.data.AppRepository
import com.example.projet_mobile_niama_afaf.data.SessionManager

class MyApplication : Application() {
    // Using by lazy so the database and repository are only created when they're needed
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { AppRepository(database.userDao(), database.productCartDao(), database.favoriteProductDao()) }
    val sessionManager by lazy { SessionManager(this) }
}

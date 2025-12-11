package com.example.projet_mobile_niama_afaf.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log

class InactivityService : Service() {

    private val binder = LocalBinder()
    private var handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var logoutRunnable: Runnable

    companion object {
        const val LOGOUT_ACTION = "com.example.projet_mobile_niama_afaf.LOGOUT"
        // Setting timeout to 2 minutes
        const val INACTIVITY_TIMEOUT_MS: Long = 2 * 60 * 1000
    }

    override fun onCreate() {
        super.onCreate()
        logoutRunnable = Runnable {
            Log.d("InactivityService", "Inactivity timeout reached. Sending logout broadcast.")
            val intent = Intent(LOGOUT_ACTION)
            sendBroadcast(intent)
            stopSelf()
        }
        startTimer()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun startTimer() {
        handler.postDelayed(logoutRunnable, INACTIVITY_TIMEOUT_MS)
        Log.d("InactivityService", "Inactivity timer started.")
    }

    fun resetTimer() {
        handler.removeCallbacks(logoutRunnable)
        handler.postDelayed(logoutRunnable, INACTIVITY_TIMEOUT_MS)
        Log.d("InactivityService", "Inactivity timer reset.")
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(logoutRunnable)
        Log.d("InactivityService", "Service destroyed")
    }

    inner class LocalBinder : Binder() {
        fun getService(): InactivityService = this@InactivityService
    }
}

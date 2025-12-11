package com.example.projet_mobile_niama_afaf

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.projet_mobile_niama_afaf.navigation.AppNavigation
import com.example.projet_mobile_niama_afaf.navigation.Screen
import com.example.projet_mobile_niama_afaf.services.InactivityService
import com.example.projet_mobile_niama_afaf.ui.theme.ProjetMobileNiamaAfafTheme

class MainActivity : ComponentActivity() {

    private var inactivityService: InactivityService? = null
    private var isBound = false
    private lateinit var navController: NavHostController

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as InactivityService.LocalBinder
            inactivityService = binder.getService()
            isBound = true
            Log.d("MainActivity", "Service connected")
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
            Log.d("MainActivity", "Service disconnected")
        }
    }

    private val logoutReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == InactivityService.LOGOUT_ACTION) {
                Log.d("MainActivity", "Logout broadcast received. Navigating to login screen.")
                navController.navigate(Screen.Login.route) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()

            ProjetMobileNiamaAfafTheme {
                AppNavigation(
                    navController = navController,
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            inactivityService?.resetTimer()
                        })
                    }
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, InactivityService::class.java).also { intent ->
            startService(intent)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
        registerReceiver(logoutReceiver, IntentFilter(InactivityService.LOGOUT_ACTION), RECEIVER_EXPORTED)
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
        unregisterReceiver(logoutReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, InactivityService::class.java))
    }
}

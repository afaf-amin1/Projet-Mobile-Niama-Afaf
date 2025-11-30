package com.example.projet_mobile_niama_afaf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.projet_mobile_niama_afaf.navigation.AppNavigation
import com.example.projet_mobile_niama_afaf.ui.theme.ProjetMobileNiamaAfafTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetMobileNiamaAfafTheme {
                AppNavigation()
            }
        }
    }
}

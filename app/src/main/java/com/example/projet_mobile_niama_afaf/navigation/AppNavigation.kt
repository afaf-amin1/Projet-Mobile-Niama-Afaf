package com.example.projet_mobile_niama_afaf.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projet_mobile_niama_afaf.LoginScreen
import com.example.projet_mobile_niama_afaf.PageWelcome
import com.example.projet_mobile_niama_afaf.PerfumesScreen
import com.example.projet_mobile_niama_afaf.ProductDetailsScreen
import com.example.projet_mobile_niama_afaf.SignUpScreen
import com.example.projet_mobile_niama_afaf.screens.CartScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            PageWelcome(onGetStarted = { navController.navigate(Screen.Login.route) })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLogin = { navController.navigate(Screen.Perfumes.route) },      // Navigue vers Page 4
                onSignUp = { navController.navigate(Screen.SignUp.route) },     // Navigue vers Page 3
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.SignUp.route) {
            SignUpScreen(
                onSignUp = { navController.navigate(Screen.Perfumes.route) },
                onSignIn = { navController.navigate(Screen.Login.route) },
                onClose = { navController.popBackStack() }
            )
        }
        composable(Screen.Perfumes.route) {
            PerfumesScreen(navController = navController)
        }
        composable(Screen.ProductDetails.route) {
            ProductDetailsScreen(navController = navController)
        }
        composable(Screen.Cart.route) {
            CartScreen()
        }
        composable(Screen.Search.route) {
            Text("Search Screen")
        }
    }
}

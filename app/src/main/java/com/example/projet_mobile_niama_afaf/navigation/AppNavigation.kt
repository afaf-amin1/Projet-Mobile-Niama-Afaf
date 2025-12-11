package com.example.projet_mobile_niama_afaf.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projet_mobile_niama_afaf.CartScreen
import com.example.projet_mobile_niama_afaf.LoginScreen
import com.example.projet_mobile_niama_afaf.PageWelcome
import com.example.projet_mobile_niama_afaf.ProductDetailsScreen
import com.example.projet_mobile_niama_afaf.SignUpScreen
import com.example.projet_mobile_niama_afaf.screens.PerfumesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(Screen.Welcome.route) {
            PageWelcome(onGetStarted = { navController.navigate(Screen.Login.route) })
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLogin = { navController.navigate(Screen.Perfumes.route) },
                onSignUp = { navController.navigate(Screen.SignUp.route) },
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
        composable(
            route = Screen.ProductDetails.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            productId?.let {
                ProductDetailsScreen(navController = navController, productId = it)
            }
        }
        composable(Screen.Cart.route) {
            CartScreen(navController = navController)
        }
        composable(Screen.Search.route) {
            Text("Search Screen")
        }
    }
}

package com.example.projet_mobile_niama_afaf.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projet_mobile_niama_afaf.LoginScreen
import com.example.projet_mobile_niama_afaf.PageWelcome
import com.example.projet_mobile_niama_afaf.PerfumesScreen
import com.example.projet_mobile_niama_afaf.ProductDetailsScreen
import com.example.projet_mobile_niama_afaf.SignUpScreen
import com.example.projet_mobile_niama_afaf.screens.CartScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        modifier = modifier
    ) {
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
            route = Screen.ProductDetails.route, // Route: "product_details/{productId}"
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extraction de l'ID depuis les arguments de la route
            val productId = backStackEntry.arguments?.getInt("productId")
            requireNotNull(productId) { "productId parameter is missing" }

            // Appel de l'écran avec les bons paramètres
            ProductDetailsScreen(
                navController = navController,
                productId = productId
            )
        }
        composable(Screen.Cart.route) {
            CartScreen()
        }
        composable(Screen.Search.route) {
            Text("Search Screen")
        }
    }
}

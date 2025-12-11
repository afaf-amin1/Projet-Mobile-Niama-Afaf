package com.example.projet_mobile_niama_afaf.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projet_mobile_niama_afaf.CartScreen
import com.example.projet_mobile_niama_afaf.LoginScreen
import com.example.projet_mobile_niama_afaf.PageWelcome
import com.example.projet_mobile_niama_afaf.PerfumesScreen
import com.example.projet_mobile_niama_afaf.ProductDetailsScreen
import com.example.projet_mobile_niama_afaf.SignUpScreen

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
            PerfumesScreen(navController = navController, productViewModel = viewModel())
        }
        composable(
            route = Screen.ProductDetails.route + "/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            requireNotNull(productId) { "Product ID is required" }
            ProductDetailsScreen(navController = navController, productId = productId, productViewModel = viewModel())
        }
        composable(Screen.Cart.route) {
            CartScreen(navController = navController, cartViewModel = viewModel())
        }
        composable(Screen.Search.route) {
            Text("Search Screen")
        }
    }
}

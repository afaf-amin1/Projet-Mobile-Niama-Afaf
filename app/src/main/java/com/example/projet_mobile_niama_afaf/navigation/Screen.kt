package com.example.projet_mobile_niama_afaf.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Perfumes : Screen("perfumes_screen")
    object ProductDetails : Screen("product_details/{productId}") {
        fun createRoute(productId: Int) = "product_details/$productId"
    }
    object Cart : Screen("cart_screen")
    object Profile : Screen("profile_screen")
    object Search : Screen("search_screen")
}

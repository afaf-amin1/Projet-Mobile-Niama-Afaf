package com.example.projet_mobile_niama_afaf

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.projet_mobile_niama_afaf.data.CartRepository
import com.example.projet_mobile_niama_afaf.data.Product
import com.example.projet_mobile_niama_afaf.navigation.Screen
import com.example.projet_mobile_niama_afaf.ui.theme.SelectedItemColor
import com.example.projet_mobile_niama_afaf.ui.theme.appBarColor
import com.example.projet_mobile_niama_afaf.utils.NotificationHelper
import com.example.projet_mobile_niama_afaf.viewmodel.ProductViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun PerfumesScreen(navController: NavController, productViewModel: ProductViewModel = viewModel()) {
    val products by productViewModel.products.collectAsState()
    val context = LocalContext.current
    val notificationHelper = NotificationHelper(context)
    val scope = rememberCoroutineScope()

    // Permission request launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                // Permission Granted
            } else {
                // Permission Denied
            }
        }
    )

    LaunchedEffect(Unit) {
        productViewModel.getProducts()
        // Request notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Scaffold(
        topBar = { PerfumesAppBar(onBack = { navController.popBackStack() }) },
        bottomBar = { CustomBottomBar(navController = navController) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FilterOption(text = "Sort")
                FilterOption(text = "Filter")
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(products) { product ->
                    ParfumItem(
                        parfum = product,
                        onItemClick = { navController.navigate(Screen.ProductDetails.route + "/${product.id}") },
                        onAddToCartClick = {
                            scope.launch {
                                CartRepository.addToCart(product)
                                notificationHelper.showProductAddedNotification(product.title)
                                delay(500) // Laissez le temps à la notification de s'afficher
                                navController.navigate(Screen.Cart.route)
                            }
                        }
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 0.5.dp, modifier = Modifier.padding(vertical = 10.dp))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfumesAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                "Perfumes",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = appBarColor)
    )
}


@Composable
fun FilterOption(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { /* Gérer le clic */ }
    ) {
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.Black.copy(alpha = 0.8f))
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            Icons.Filled.KeyboardArrowDown,
            contentDescription = "Ouvrir options de $text",
            tint = Color.Black.copy(alpha = 0.8f)
        )
    }
}


@Composable
fun ParfumItem(parfum: Product, onItemClick: () -> Unit, onAddToCartClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable { onItemClick() },
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {

            Text(parfum.title, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text("${parfum.price} €", fontSize = 14.sp, color = Color.Black.copy(alpha = 0.9f))
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { onAddToCartClick() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)), // Orange color
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(
                    "Add to Cart",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White // White text
                )
            }
        }

        Image(
            painter = rememberAsyncImagePainter(parfum.image),
            contentDescription = parfum.title,
            modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
                .padding(start = 16.dp)
        )
    }
}


@Composable
fun CustomBottomBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier.height(60.dp),
        containerColor = Color.White,
        contentColor = Color.Gray
    ) {
        val items = mapOf(
            Screen.Welcome.route to Icons.Filled.Home,
            Screen.Search.route to Icons.Filled.Search,
            Screen.Cart.route to Icons.Filled.ShoppingCart,
            Screen.Profile.route to Icons.Filled.Person
        )

        items.forEach { (route, icon) ->
            NavigationBarItem(
                icon = {
                    Icon(
                        icon,
                        contentDescription = route,
                        tint = if (navController.currentDestination?.route == route) SelectedItemColor else Color.Gray
                    )
                },
                label = {
                    Text(
                        route.replace("_screen", "").replaceFirstChar { it.uppercase() },
                        fontSize = 10.sp,
                        color = if (navController.currentDestination?.route == route) SelectedItemColor else Color.Gray
                    )
                },
                selected = navController.currentDestination?.route == route,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}

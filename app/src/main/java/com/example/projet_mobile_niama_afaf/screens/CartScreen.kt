package com.example.projet_mobile_niama_afaf.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.projet_mobile_niama_afaf.MyApplication
import com.example.projet_mobile_niama_afaf.R
import com.example.projet_mobile_niama_afaf.data.ProductCart
import com.example.projet_mobile_niama_afaf.viewmodels.CartViewModel
import com.example.projet_mobile_niama_afaf.viewmodels.ViewModelFactory

@Composable
fun CartScreen(navController: NavController) {
    val application = LocalContext.current.applicationContext as MyApplication
    val viewModel: CartViewModel = viewModel(factory = ViewModelFactory(application.repository))
    val cartItems by viewModel.cartItems.collectAsState()

    CartScreenContent(cartItems, viewModel, navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenContent(cartItems: List<ProductCart>, viewModel: CartViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Button(onClick = {
                val randomId = (0..1000).random()
                viewModel.addToCart(ProductCart(productId = "product_" + randomId, quantity = 1))
            }) {
                Text(stringResource(R.string.add_dummy_product))
            }

            if (cartItems.isEmpty()) {
                Text(stringResource(R.string.cart_is_empty))
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(cartItems) { product ->
                        Text("Product ID: ${product.productId}, Quantity: ${product.quantity}")
                    }
                }
            }
        }
    }
}

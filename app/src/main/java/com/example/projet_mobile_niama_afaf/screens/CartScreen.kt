package com.example.projet_mobile_niama_afaf.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projet_mobile_niama_afaf.MyApplication
import com.example.projet_mobile_niama_afaf.R
import com.example.projet_mobile_niama_afaf.data.ProductCart
import com.example.projet_mobile_niama_afaf.viewmodels.CartViewModel
import com.example.projet_mobile_niama_afaf.viewmodels.ViewModelFactory

@Composable
fun CartScreen() {
    val application = LocalContext.current.applicationContext as MyApplication
    // Provide both the repository and the session manager to the factory
    val viewModel: CartViewModel = viewModel(factory = ViewModelFactory(application.repository, application.sessionManager))

    val cartItems by viewModel.cartItems.collectAsState()

    CartScreenContent(cartItems, viewModel)
}

@Composable
fun CartScreenContent(cartItems: List<ProductCart>, viewModel: CartViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            val randomId = (0..1000).random()
            // The addToCart method signature has changed
            viewModel.addToCart(productId = "product_" + randomId, quantity = 1)
        }) {
            Text(stringResource(R.string.add_dummy_product))
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            if (cartItems.isEmpty()) {
                item {
                    Text(stringResource(R.string.cart_is_empty))
                }
            } else {
                items(cartItems) { product ->
                    Text("Product ID: ${product.productId}, Quantity: ${product.quantity}")
                }
            }
        }
    }
}

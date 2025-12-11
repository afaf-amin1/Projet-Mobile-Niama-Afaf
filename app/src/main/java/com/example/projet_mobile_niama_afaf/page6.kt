package com.example.projet_mobile_niama_afaf

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.projet_mobile_niama_afaf.data.Product
import com.example.projet_mobile_niama_afaf.ui.theme.AccentButtonColor
import com.example.projet_mobile_niama_afaf.viewmodel.CartViewModel
import java.text.DecimalFormat


fun calculateSummary(items: List<Product>): Triple<Double, Double, Double> {
    val subtotal = items.sumOf { it.price }
    val shipping = 0.0
    val total = subtotal + shipping
    return Triple(subtotal, shipping, total)
}

fun formatPrice(price: Double): String {
    val formatter = DecimalFormat("#,##0.00' €'")
    return formatter.format(price)
}

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel = viewModel()) {
    val cartItems by cartViewModel.cartItems.collectAsState(initial = emptyList())
    val (subtotal, shipping, total) = calculateSummary(cartItems)

    Scaffold(
        topBar = { CartAppBar(onBack = { navController.popBackStack() }) },
        bottomBar = { CustomBottomBar(navController = navController) },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cartItems) { item ->
                    CartItemRow(item = item)
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 0.5.dp, modifier = Modifier.padding(vertical = 10.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Summary",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    SummaryRow(label = "Subtotal", value = formatPrice(subtotal).replace(',', ' '))
                    SummaryRow(label = "Shipping", value = if (shipping == 0.0) "Free" else formatPrice(shipping))

                    HorizontalDivider(color = Color.Black.copy(alpha = 0.5f), thickness = 0.5.dp, modifier = Modifier.padding(vertical = 12.dp))

                    SummaryRow(
                        label = "Total",
                        value = formatPrice(total).replace(',', ' '),isBold=true, fontSize = 18.sp
                    )
                }
            }

            CheckoutButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}



@Composable
fun SummaryRow(label: String, value: String, isBold: Boolean = false, fontSize: TextUnit = 16.sp) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal,
            fontSize = fontSize,
            color = Color.Black
        )
        Text(
            value,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.SemiBold,
            fontSize = fontSize,
            color = Color.Black
        )
    }
}
@Composable
fun CheckoutButton(modifier: Modifier = Modifier) {
    Button(
        onClick = { /* Gérer le paiement */ },
        modifier = modifier.height(55.dp),
        colors = ButtonDefaults.buttonColors(containerColor = AccentButtonColor),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
    ) {
        Text(
            "Checkout",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}


@Composable
fun CartItemRow(item: Product) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberAsyncImagePainter(item.image),
                contentDescription = item.title,
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 10.dp)
            )

            Column(
                modifier = Modifier.width(IntrinsicSize.Max)
            ) {
                Text(item.title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.Black)
            }
        }
        Text(
            formatPrice(item.price).replace(',', ' '),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartAppBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Cart",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour",
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

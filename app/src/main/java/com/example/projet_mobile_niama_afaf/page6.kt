package com.example.projet_mobile_niama_afaf

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import com.example.projet_mobile_niama_afaf.navigation.Screen
import com.example.projet_mobile_niama_afaf.ui.theme.AccentButtonColor
import com.example.projet_mobile_niama_afaf.ui.theme.SelectedItemColor
import java.text.DecimalFormat


val appBarColor = Color.Black
val inactiveIconColor = Color.Gray

data class CartItem(
    val name: String,
    val volume: String,
    val price: Double,
    val imageResId: Int,
)

val cartItems = listOf(
    CartItem("Lady Million", "80 ml", 102.00, R.drawable.ladymillion),
    CartItem("Chanel N°5", "100 ml", 149.90, R.drawable.channel),
    CartItem("Yves saint laurent Libre", "50 ml", 130.00, R.drawable.libre),
)

fun calculateSummary(items: List<CartItem>): Triple<Double, Double, Double> {
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
fun CartScreen(navController: NavController) {
    val (subtotal, shipping, total) = calculateSummary(cartItems)

    Scaffold(
        topBar = { CartAppBar(onBack = { navController.popBackStack() }) },
        bottomBar = { CustomBottomBar(navController = navController, selectedIndex = 2) },
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
fun CartItemRow(item: CartItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = item.imageResId),
                contentDescription = item.name,
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 10.dp)
            )

            Column(
                modifier = Modifier.width(IntrinsicSize.Max)
            ) {
                Text(item.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.Black)
                Text(item.volume, fontSize = 14.sp, color = Color.Gray)
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



@Composable
fun CustomBottomBar(navController: NavController, selectedIndex: Int) {
    NavigationBar(
        modifier = Modifier.height(60.dp),
        containerColor = Color.White,
        contentColor = inactiveIconColor
    ) {
        val items = mapOf(
            Screen.Welcome.route to Icons.Filled.Home,
            Screen.Search.route to Icons.Filled.Search,
            Screen.Cart.route to Icons.Filled.ShoppingCart,
            Screen.Profile.route to Icons.Filled.Person
        )

        var i = 0
        items.forEach { (route, icon) ->
            val index = i
            NavigationBarItem(
                icon = {
                    Icon(
                        icon,
                        contentDescription = route,
                        tint = if (index == selectedIndex) SelectedItemColor else inactiveIconColor
                    )
                },
                selected = index == selectedIndex,
                onClick = { 
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
            i++
        }
    }
}


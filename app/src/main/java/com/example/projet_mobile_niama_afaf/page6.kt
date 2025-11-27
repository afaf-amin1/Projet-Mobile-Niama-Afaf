package com.example.projet_mobile_niama_afaf

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.example.projet_mobile_niama_afaf.ui.theme.ProjetMobileNiamaAfafTheme // Garde l'import du Thème
import java.text.DecimalFormat

// --- VARIABLES DE COULEURS (GLOBALES DANS CE FICHIER) ---
val accentuationColor = Color(0xFFDD7B1A)
val appBarColor = Color.Black
val inactiveIconColor = Color.Gray

// --- MODÈLE DE DONNÉES ET LOGIQUE ---

data class CartItem(
    val name: String, // CORRECTION: Ajout de ":"
    val volume: String, // CORRECTION: Ajout de ":"
    val price: Double, // CORRECTION: Ajout de ":"
    val imageResId: Int, // CORRECTION: Ajout de ":"
)

val cartItems = listOf(
    CartItem("Lady Million", "80 ml", 102.00, R.drawable.ladymillion), // CORRECTION: Ajout de "quotes"
    CartItem("Channel N°5", "100 ml", 149.90, R.drawable.channel), // CORRECTION: Ajout de "quotes"
    CartItem("Yves saint laurent Libre", "50 ml", 130.00, R.drawable.libre), // CORRECTION: Ajout de "quotes"
)

fun calculateSummary(items: List<CartItem>): Triple<Double, Double, Double> { // CORRECTION: Ajout de ":" et "<>"
    val subtotal = items.sumOf { it.price }
    val shipping = 0.0
    val total = subtotal + shipping
    return Triple(subtotal, shipping, total)
}

fun formatPrice(price: Double): String { // CORRECTION: Ajout de ":"
    val formatter = DecimalFormat("#,##0.00' €'") // CORRECTION: Ajout de "quotes"
    return formatter.format(price)
}

// --- COMPOSABLE PRINCIPAL ---

@Composable
fun CartScreen() {
    val (subtotal, shipping, total) = calculateSummary(cartItems)

    Scaffold(
        topBar = { CartAppBar() },
        bottomBar = { CustomBottomBar(selectedIndex = 2) },
        containerColor = Color.White
    ) { padding -> // CORRECTION: Ajout de "->" pour la lambda content
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
                items(cartItems) { item -> // CORRECTION: Ajout de "->"
                    CartItemRow(item = item)
                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 0.5.dp, modifier = Modifier.padding(vertical = 10.dp))
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        "Summary", // CORRECTION: Ajout de "quotes"
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    SummaryRow(label = "Subtotal", value = formatPrice(subtotal).replace(',', ' ')) // CORRECTION: Ajout de "quotes"
                    SummaryRow(label = "Shipping", value = if (shipping == 0.0) "Free" else formatPrice(shipping)) // CORRECTION: Ajout de "quotes"

                    HorizontalDivider(color = Color.Black.copy(alpha = 0.5f), thickness = 0.5.dp, modifier = Modifier.padding(vertical = 12.dp))

                    SummaryRow(
                        label = "Total", // CORRECTION: Ajout de "quotes"
                        value = formatPrice(total).replace(',', ' '),isBold=true, fontSize = 18.sp
                    )
                }
            }
            // Bouton Checkout
            CheckoutButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

// --- AUTRES COMPOSABLES ---

@Composable
fun SummaryRow(label: String, value: String, isBold: Boolean = false, fontSize: TextUnit = 16.sp) { // CORRECTION: Ajout de ":"
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
fun CheckoutButton(modifier: Modifier = Modifier) { // CORRECTION: Ajout de ":"
    Button(
        onClick = { /* Gérer le paiement */ }, // CORRECTION: Ajout de "/* */"
        modifier = modifier.height(55.dp),
        colors = ButtonDefaults.buttonColors(containerColor = accentuationColor),
        shape = RoundedCornerShape(8.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
    ) {
        Text(
            "Checkout", // CORRECTION: Ajout de "quotes"
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

@Composable
fun CartItemRow(item: CartItem) { // CORRECTION: Ajout de ":"
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

@OptIn(ExperimentalMaterial3Api::class) // CORRECTION: Changement de syntaxe pour "@OptIn"
@Composable
fun CartAppBar() {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Cart", // CORRECTION: Ajout de "quotes"
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { /* Gérer le retour */ }) { // CORRECTION: Ajout de "/* */"
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Retour", // CORRECTION: Ajout de "quotes"
                    tint = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
    )
}

@Composable
fun CustomBottomBar(selectedIndex: Int) { // CORRECTION: Ajout de ":"
    NavigationBar(
        modifier = Modifier.height(60.dp),
        containerColor = Color.White,
        contentColor = inactiveIconColor
    ) {
        val items = listOf("Home", "Search", "Cart", "Profile") // CORRECTION: Ajout de "quotes"

        val icons = listOf(
            Icons.Filled.Home,
            Icons.Filled.Search,
            Icons.Filled.Email,
            Icons.Filled.Person
        )

        items.forEachIndexed { index, item -> // CORRECTION: Ajout de "->"
            NavigationBarItem(
                icon = {
                    Icon(
                        icons[index],
                        contentDescription = item,
                        tint = if (index == selectedIndex) accentuationColor else inactiveIconColor
                    )
                },
                selected = index == selectedIndex,
                onClick = { /* Naviguer vers l'écran */ }, // CORRECTION: Ajout de "/* */"
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}

@Preview
@Composable
fun NomDeMaFonctionPreview() {
    ProjetMobileNiamaAfafTheme {
        CartScreen()
    }
}
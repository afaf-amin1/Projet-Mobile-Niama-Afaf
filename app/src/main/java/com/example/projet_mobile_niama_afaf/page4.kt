package com.example.projet_mobile_niama_afaf




import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.projet_mobile_niama_afaf.navigation.Screen
import com.example.projet_mobile_niama_afaf.ui.theme.SelectedItemColor


data class Parfum(
    val name: String,
    val price: String,
    val imageResId: Int,
    val isNew: Boolean = false
)


val sampleParfums = listOf(
    Parfum("Chanel N°5", "149,90 €", R.drawable.channel, isNew = true),
    Parfum("Lancôme La Vie Est Belle", "95 €", R.drawable.vie_est_belle),
    Parfum("Yves Saint Laurent Libre", "130 €", R.drawable.libre),
    Parfum("Miss Dior", "82,95 €", R.drawable.missdior),
    Parfum("Lady Million", "102,00 €", R.drawable.ladymillion)
)



@Composable
fun PerfumesScreen(navController: NavController) {
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
                items(sampleParfums) { parfum ->
                    ParfumItem(parfum = parfum, onClick = { navController.navigate(Screen.ProductDetails.route) })
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
fun ParfumItem(parfum: Parfum, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            if (parfum.isNew) {
                Text(
                    "New",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(2.dp))
            }

            Text(parfum.name, fontWeight = FontWeight.SemiBold, fontSize = 17.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(parfum.price, fontSize = 14.sp, color = Color.Black.copy(alpha = 0.9f))
            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { /* Ajouter au panier */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0)),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
                modifier = Modifier.wrapContentWidth()
            ) {
                Text(
                    "Add to Cart",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
        }

        Image(
            painter = painterResource(id = parfum.imageResId),
            contentDescription = parfum.name,
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
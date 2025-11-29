package com.example.projet_mobile_niama_afaf




import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource




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
fun PerfumesScreen() {
    Scaffold(
        topBar = { PerfumesAppBar() },
        bottomBar = { CustomBottomBar() },
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

            // Liste Défilante des Parfums
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(sampleParfums) { parfum ->
                    ParfumItem(parfum = parfum)
                    Divider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 0.5.dp, modifier = Modifier.padding(vertical = 10.dp))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfumesAppBar() {
    TopAppBar(
        title = {
            Text(
                "Perfumes",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* Gérer le retour */ }) {
                Icon(
                    Icons.Filled.ArrowBack,
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
fun ParfumItem(parfum: Parfum) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
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
fun CustomBottomBar() {
    NavigationBar(
        modifier = Modifier.height(60.dp),
        containerColor = Color.White,
        contentColor = Color.Gray
    ) {
        val items = listOf("Home", "Search", "Cart", "Profile")
        val icons = listOf(Icons.Filled.Home, Icons.Filled.Search, Icons.Filled.ShoppingCart, Icons.Filled.Person)
        val selectedIndex = 2

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {

                    Icon(
                        icons[index],
                        contentDescription = item,
                        tint = if (index == selectedIndex) nouvelleCouleurBouton else Color.Gray
                    )
                },
                label = {

                    Text(
                        item,
                        fontSize = 10.sp,
                        color = if (index == selectedIndex) nouvelleCouleurBouton else Color.Gray
                    )
                },
                selected = index == selectedIndex,
                onClick = { /* Naviguer vers l'écran */ },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}



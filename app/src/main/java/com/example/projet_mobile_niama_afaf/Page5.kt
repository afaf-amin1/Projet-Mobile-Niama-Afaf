package com.example.projet_mobile_niama_afaf

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.example.projet_mobile_niama_afaf.data.CartRepository
import com.example.projet_mobile_niama_afaf.data.Product
import com.example.projet_mobile_niama_afaf.navigation.Screen
import com.example.projet_mobile_niama_afaf.ui.theme.AccentButtonColor
import com.example.projet_mobile_niama_afaf.ui.theme.BarBackgroundColor
import com.example.projet_mobile_niama_afaf.ui.theme.PrimaryTextColor
import com.example.projet_mobile_niama_afaf.ui.theme.ScreenBackgroundColor
import com.example.projet_mobile_niama_afaf.ui.theme.SecondaryTextColor
import com.example.projet_mobile_niama_afaf.ui.theme.SelectedItemColor
import com.example.projet_mobile_niama_afaf.ui.theme.UnselectedItemColor
import com.example.projet_mobile_niama_afaf.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(navController: NavController, productId: Int, productViewModel: ProductViewModel = viewModel()) {
    val product by productViewModel.productDetails.collectAsState()

    LaunchedEffect(productId) {
        productViewModel.getProductById(productId)
    }

    val screenBackgroundColor = ScreenBackgroundColor
    Scaffold(
        containerColor = screenBackgroundColor,
        topBar = {
            ProductAppBar(
                onBack = { navController.popBackStack() },
                onSave = { navController.navigate(Screen.Cart.route) }
            )
        },
        bottomBar = { ProductBottomBar(navController = navController) }
    ) { paddingValues ->
        product?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    ProductImageSection(it.image)
                    ProductDescriptionSection(
                        product = it,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    AddToCartButton(
                        onClick = {
                            CartRepository.addToCart(it)
                            navController.navigate(Screen.Cart.route)
                         },
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAppBar(onBack: () -> Unit, onSave: () -> Unit) {
    TopAppBar(
        title = { /* Pas de titre visible */ },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = BarBackgroundColor),

        navigationIcon = {
            IconButton(onClick = onBack) {

                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = SelectedItemColor
                )
            }
        },

        actions = {
            IconButton(onClick = onSave) {
                Icon(
                    Icons.Filled.ShoppingCart,
                    contentDescription = "Cart",
                    tint = SelectedItemColor
                )
            }
        },
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
fun ProductImageSection(imageUrl: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
    ) {
        Image(
            painter = rememberAsyncImagePainter(imageUrl),
            contentDescription = "Product Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        )
    }
}


@Composable
fun ProductDescriptionSection(product: Product, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(top = 24.dp)) {

        Text(
            text = product.title,
            color = PrimaryTextColor,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = product.description,
            color = SecondaryTextColor,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Category: ${product.category}",
            color = SecondaryTextColor,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Price: ${product.price} €",
            color = PrimaryTextColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun AddToCartButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = AccentButtonColor),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(
            text = "Add to Cart",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

data class BottomNavItemData(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun ProductBottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItemData("Home", Icons.Default.Home, Screen.Welcome.route),
        BottomNavItemData("Search", Icons.Default.Search, Screen.Search.route),
        BottomNavItemData("Cart", Icons.Default.ShoppingCart, Screen.Cart.route),
        BottomNavItemData("Profile", Icons.Default.Person, Screen.Profile.route)
    )

    val backStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar(
        containerColor = BarBackgroundColor,
        contentColor = UnselectedItemColor,
        tonalElevation = 4.dp,
        modifier = Modifier.height(60.dp)
    ) {
        items.forEach { item ->
            val isSelected = item.route == backStackEntry?.destination?.route
            BottomNavItem(
                icon = item.icon,
                label = item.label,
                isSelected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun RowScope.BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val tintColor = if (isSelected) SelectedItemColor else UnselectedItemColor

    NavigationBarItem(
        icon = { Icon(icon, contentDescription = label, tint = tintColor, modifier = Modifier.size(24.dp)) },
        label = { null },
        selected = isSelected,
        onClick = onClick,
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent,
            selectedIconColor = SelectedItemColor,
            unselectedIconColor = UnselectedItemColor
        )
    )
}
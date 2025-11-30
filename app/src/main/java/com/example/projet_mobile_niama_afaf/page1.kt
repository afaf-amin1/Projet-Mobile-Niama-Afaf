package com.example.projet_mobile_niama_afaf

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet_mobile_niama_afaf.ui.theme.AccentButtonColor

@Composable
fun PageWelcome(onGetStarted: () -> Unit) {
    val couleurdebut = Color(0xFFE7A070)
    val couleurfin = Color(0xFF3E2723)
    val textColor = Color(0xFFF3EAD3)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(couleurdebut, couleurfin)
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "VIBE FRANGRANCES",
                color = textColor,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 80.dp)
            )
            Text(
                text = "Explore parfum for women collection",
                color = textColor,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 40.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.logo_app),
                contentDescription = "Image d’exemple",
                modifier = Modifier.size(370.dp),
                contentScale = ContentScale.Crop
            )

            Box(modifier = Modifier.weight(1f))
            Button(
                onClick = onGetStarted,
                colors = ButtonDefaults.buttonColors(containerColor = AccentButtonColor),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .height(48.dp)
                    .width(160.dp)
            ) {
                Text(
                    text = "Get Started",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
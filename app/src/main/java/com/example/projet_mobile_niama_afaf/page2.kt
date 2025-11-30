package com.example.projet_mobile_niama_afaf


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet_mobile_niama_afaf.ui.theme.AccentButtonColor

val couleurdebut = Color(0xFFE7A070)
val couleurfin = Color(0xFF4F2C1A)

@Composable
fun LoginScreen(onLogin: () -> Unit, onSignUp: () -> Unit, onBack: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    val gradientBrush = Brush.verticalGradient(
        colors = listOf(couleurdebut, couleurfin)
    )


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
    ) {

        Scaffold(
            topBar = { LoginAppBar(onBack = onBack) },
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))


                LoginInputField(
                    value = email,
                    onValueChange = { email = it },
                    hint = "Email"
                )

                Spacer(modifier = Modifier.height(16.dp))


                LoginInputField(
                    value = password,
                    onValueChange = { password = it },
                    hint = "Password",
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(24.dp))


                Button(
                    onClick = onLogin,
                    colors = ButtonDefaults.buttonColors(containerColor = AccentButtonColor),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        "Login",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))


                SignupLink(onClick = onSignUp)

                Spacer(modifier = Modifier.height(32.dp))
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginAppBar(onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Login",
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        },

        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour",
                    tint = Color.White
                )
            }
        }
    )
}


@Composable
fun LoginInputField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    isPassword: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(hint, color = Color.Gray.copy(alpha = 0.8f)) },
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            cursorColor = couleurfin,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = true
    )
}


@Composable
fun SignupLink(onClick: () -> Unit) {
    Row(
        modifier = Modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val linkColor = Color.White.copy(alpha = 0.8f)

        Text(
            "Don\'t have an account? ",
            color = linkColor,
            fontSize = 14.sp
        )
        Text(
            "Sign up",
            color = linkColor,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}
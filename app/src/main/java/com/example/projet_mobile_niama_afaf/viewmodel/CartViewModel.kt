package com.example.projet_mobile_niama_afaf.viewmodel

import androidx.lifecycle.ViewModel
import com.example.projet_mobile_niama_afaf.data.CartRepository
import com.example.projet_mobile_niama_afaf.data.Product
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    val cartItems: StateFlow<List<Product>> = CartRepository.cartItems

    fun getCartTotal(): Double {
        return CartRepository.getCartTotal()
    }
}

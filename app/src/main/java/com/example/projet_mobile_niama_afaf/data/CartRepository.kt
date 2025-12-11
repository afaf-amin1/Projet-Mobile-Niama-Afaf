package com.example.projet_mobile_niama_afaf.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object CartRepository {
    private val _cartItems = MutableStateFlow<List<Product>>(emptyList())

    fun addToCart(product: Product) {
        val currentItems = _cartItems.value.toMutableList()
        if (!currentItems.any { it.id == product.id }) {
            currentItems.add(product)
            _cartItems.value = currentItems
        }
    }

    fun getCartItems(): StateFlow<List<Product>> = _cartItems

    fun getCartTotal(): Double {
        return _cartItems.value.sumOf { it.price }
    }
}

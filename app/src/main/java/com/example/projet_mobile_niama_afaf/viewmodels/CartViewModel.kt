package com.example.projet_mobile_niama_afaf.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projet_mobile_niama_afaf.data.AppRepository
import com.example.projet_mobile_niama_afaf.data.ProductCart
import com.example.projet_mobile_niama_afaf.data.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(private val repository: AppRepository, private val sessionManager: SessionManager) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<ProductCart>>(emptyList())
    val cartItems: StateFlow<List<ProductCart>> = _cartItems.asStateFlow()

    init {
        loadCartForCurrentUser()
    }

    private fun loadCartForCurrentUser() {
        val userEmail = sessionManager.getUserEmail()
        if (userEmail != null) {
            viewModelScope.launch {
                repository.getCartForUser(userEmail).collect { cart ->
                    _cartItems.value = cart
                }
            }
        }
    }

    fun addToCart(productId: String, quantity: Int) {
        val userEmail = sessionManager.getUserEmail()
        if (userEmail != null) {
            viewModelScope.launch {
                // Check if item already exists to update quantity
                val existingItem = _cartItems.value.find { it.productId == productId }
                if (existingItem != null) {
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity + quantity)
                    repository.updateCartItem(updatedItem)
                } else {
                    repository.addToCart(ProductCart(userEmail = userEmail, productId = productId, quantity = quantity))
                }
            }
        }
    }

    fun removeFromCart(product: ProductCart) {
        viewModelScope.launch {
            repository.removeFromCart(product)
        }
    }

    fun clearCart() {
        val userEmail = sessionManager.getUserEmail()
        if (userEmail != null) {
            viewModelScope.launch {
                repository.clearCartForUser(userEmail)
            }
        }
    }
}

class ViewModelFactory(private val repository: AppRepository, private val sessionManager: SessionManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository, sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

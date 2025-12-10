package com.example.projet_mobile_niama_afaf.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.projet_mobile_niama_afaf.data.AppRepository
import com.example.projet_mobile_niama_afaf.data.ProductCart
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(private val repository: AppRepository) : ViewModel() {

    val cartItems: StateFlow<List<ProductCart>> = repository.cartItems
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addToCart(product: ProductCart) {
        viewModelScope.launch {
            repository.addToCart(product)
        }
    }

    fun removeFromCart(product: ProductCart) {
        viewModelScope.launch {
            repository.removeFromCart(product)
        }
    }

    fun updateCartItem(product: ProductCart) {
        viewModelScope.launch {
            repository.updateCartItem(product)
        }
    }
}

class ViewModelFactory(private val repository: AppRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository) as T
        }
        // Add other ViewModels here
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.example.projet_mobile_niama_afaf.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projet_mobile_niama_afaf.data.Product
import com.example.projet_mobile_niama_afaf.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _productDetails = MutableStateFlow<Product?>(null)
    val productDetails: StateFlow<Product?> = _productDetails

    fun getProducts() {
        viewModelScope.launch {
            try {
                _products.value = repository.getProducts()
            } catch (e: Exception) {
                // Gérer les erreurs, par exemple, en loggant ou en affichant un message
            }
        }
    }

    fun getProductById(id: Int) {
        viewModelScope.launch {
            try {
                _productDetails.value = repository.getProductById(id)
            } catch (e: Exception) {
                // Gérer les erreurs
            }
        }
    }
}

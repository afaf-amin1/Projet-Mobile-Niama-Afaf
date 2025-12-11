package com.example.projet_mobile_niama_afaf.data

class ProductRepository {
    private val apiService = RetrofitInstance.api

    suspend fun getProducts(): List<Product> {
        return apiService.getProducts()
    }

    suspend fun getProductById(id: Int): Product {
        return apiService.getProductById(id)
    }
}

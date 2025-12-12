package com.example.projet_mobile_niama_afaf.data

import com.example.projet_mobile_niama_afaf.data.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}

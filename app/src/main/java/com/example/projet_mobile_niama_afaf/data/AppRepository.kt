package com.example.projet_mobile_niama_afaf.data

import kotlinx.coroutines.flow.Flow

class AppRepository(
    private val userDao: UserDao,
    private val productCartDao: ProductCartDao,
    private val favoriteProductDao: FavoriteProductDao
) {

    // User methods
    suspend fun insertUser(user: User) = userDao.insert(user)
    fun getUser(email: String): Flow<User?> = userDao.getUser(email)

    // Cart methods
    val cartItems: Flow<List<ProductCart>> = productCartDao.getAll()
    suspend fun addToCart(product: ProductCart) = productCartDao.insert(product)
    suspend fun removeFromCart(product: ProductCart) = productCartDao.delete(product)
    suspend fun updateCartItem(product: ProductCart) = productCartDao.update(product)
    suspend fun clearCart() = productCartDao.deleteAll()

    // Favorite methods
    val favoriteItems: Flow<List<FavoriteProduct>> = favoriteProductDao.getAll()
    suspend fun addFavorite(product: FavoriteProduct) = favoriteProductDao.insert(product)
    suspend fun removeFavorite(product: FavoriteProduct) = favoriteProductDao.delete(product)
    fun isFavorite(productId: String): Flow<FavoriteProduct?> = favoriteProductDao.getById(productId)
}

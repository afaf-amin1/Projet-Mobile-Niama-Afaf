package com.example.projet_mobile_niama_afaf.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.projet_mobile_niama_afaf.R

class NotificationHelper(private val context: Context) {

    private val channelId = "product_channel_v2" // Changed Channel ID to force recreation
    private val channelName = "Product Notifications"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            // Optional: Delete the old channel if you want to ensure changes are applied
            // notificationManager.deleteNotificationChannel("product_channel")

            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH // Use HIGH importance
            ).apply {
                description = "Notifications for product updates"
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showProductAddedNotification(productName: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Assurez-vous que cette ressource existe
            .setContentTitle("Product Added to Cart")
            .setContentText("$productName has been added to your cart.")
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Use HIGH priority
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}

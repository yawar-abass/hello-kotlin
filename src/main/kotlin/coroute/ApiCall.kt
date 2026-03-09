package org.example.coroute
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class ApiCall {

    private suspend fun fetchUserProfile(userId:String):String{
        println("   [API 1] Requesting profile for $userId...")
        delay(2000)
        return """{"name": "Alice", "role": "Admin"}"""
    }
    private suspend fun fetchUserOrders(userId: String): String {
        println("   [API 2] Requesting orders for $userId...")
        delay(3000)
        return """[{"id": "ORD-123", "total": 45.00}]"""
    }

    suspend fun loadDashboardData(userId: String) = coroutineScope {
        println("Starting Dashboard Load...")
        val timeTaken = measureTimeMillis {
            val deferredProfile = async(Dispatchers.IO) { fetchUserProfile(userId) }
            val deferredOrders = async(Dispatchers.IO) { fetchUserOrders(userId) }
            println("   [Main] Both requests fired! I can do other work while waiting...")
            val profileJson = deferredProfile.await()
            val ordersJson = deferredOrders.await()
            println("\n--- Dashboard Ready ---")
            println("Profile: $profileJson")
            println("Orders: $ordersJson")
        }
    }
}
package org.example

import kotlinx.coroutines.runBlocking
import org.example.coroute.ApiCall

fun main() = runBlocking() {
   val api = ApiCall()
   api.loadDashboardData("USR-999")
}


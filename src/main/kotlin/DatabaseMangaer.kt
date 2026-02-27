package org.example

// Singleton pattern in kotlin
object DatabaseMangaer {
    val dbUrl:String =""

    fun  connectDb( url:String){
        println("connecting to db $url")
    }
}
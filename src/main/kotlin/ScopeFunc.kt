package org.example

class ScopeFunc {

    var name:String? = "Jhon Doe"

    var length = name?.let{
        println("The name is $it")
        it.length
    }
//    val intent = Intent().apply {
//        action = "android.intent.action.VIEW" // Notice we don't have to type 'it.action'!
//        data = Uri.parse("https://google.com")
//    }

    fun sidelogs(){
        val numbers = mutableListOf(1, 2, 3)

        numbers
            .also{ println("Before adding: $it") }
            .add(4)
    }


}
package org.example

interface Yoyo{
    fun beep()
    fun meow()
}


class AnonymousClass {

    fun createAnonyCls(){
        var yo = object : Yoyo{
            override fun beep() {
                println("beep beep beep!")
            }

            override fun meow() {
                println("moew moew moew")

            }



        }

//        yo.meow()
    }

}
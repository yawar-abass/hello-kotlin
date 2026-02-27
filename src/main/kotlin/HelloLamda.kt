package org.example

class HelloLamda {

   val fisrtLamda: (Int, Int)->Int = {
       a:Int, b:Int ->a+b
   }

    val square:(Int)->Int = {it*it}

    fun dolamda (number:Int, operator:(Int)->Int){
        val result = operator(number)
        println("Result: $result")

        // dolamda(23,{it *2})
        // dolada(23){it *34}
    }


}
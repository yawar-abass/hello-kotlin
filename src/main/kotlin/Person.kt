package org.example

open class Person {
    var name:String ?= null
    var age:Int ? =0
    var hoursWorked:Int=0
    var hourlyRate:Double= 12.0

    constructor( name:String, age:Int){
        this.name= name
        this.age = age
    }

    open fun  greet(){
        println("Hello $name , welcome to Kotlin")
    }
}

//
//data class Employee(val name: String,val hoursWorked:Int, val hourlyRate:Double)


class Employee(val gender: String, name: String,age: Int):Person( name, age){

    fun doWork(){
        println("I am currently working...")
    }

    fun getVehicleCost():Double{
        val car1 = Car("lambo", 2323,2023)
        return car1.price.toDouble()
    }

}

class Student(var grade:String , name: String, age: Int):Person(name, age){


    fun  study(){
        println("I am studying")
    }

}




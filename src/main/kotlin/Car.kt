package org.example

open class Vehicle(val name: String, open var price: Int, val model:Int){

    fun  start(){
        println(name+ " is  starting...")
    }


}

class Car( name:String,  price: Int, model: Int): Vehicle(name, price, model) {

    override var price:Int =0
        get(){
            return  super.price +900
        }
        set(value){
            if(value>0){
                field = value
            }else{
               println("negative value not accept.")
            }
        }



}
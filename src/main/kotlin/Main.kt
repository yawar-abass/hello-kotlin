package org.example

fun main() {
   val person:Person = Person("yawar",23)

    var a1:Int?  = 12
//    a1 = null
    val a: Int = (a1 ?: 0) +23

    val person1:Person = Person("yawar",23)
    person.greet()

    val employee: Employee = Employee("Male","Yawar",23) // Upcasting child to parent
    println(employee.getVehicleCost())

    println(person==person1)

    val std:Student = Student("12th","Mike",23)
    std.study()
    std.greet()
}



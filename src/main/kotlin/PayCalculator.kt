package org.example


 class PayCalculator {

    fun calculateWeeklyPay(employee:Employee):Double{
        return employee.hoursWorked * employee.hourlyRate
    }
}

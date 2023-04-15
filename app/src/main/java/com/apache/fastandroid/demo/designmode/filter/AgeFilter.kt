package com.apache.fastandroid.demo.designmode.filter

/**
 * Created by Jerry on 2023/4/9.
 */
class AgeFilter(private val minAge:Int,private val maxAge:Int):FFilter {
    override fun filter(persons: List<FPerson>): List<FPerson> {
        return persons.filter {
            it.age in minAge..maxAge
        }
    }

}
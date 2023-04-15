package com.apache.fastandroid.demo.designmode.filter

/**
 * Created by Jerry on 2023/4/9.
 */
class GenderFilter(private val gender:String):FFilter {
    override fun filter(persons: List<FPerson>): List<FPerson> {
        return persons.filter {
            it.gender == gender
        }
    }

}
package com.apache.fastandroid.demo.designmode.filter

/**
 * Created by Jerry on 2023/4/9.
 */
class NameFilter(private val name:String):FFilter {
    override fun filter(persons: List<FPerson>): List<FPerson> {
        return persons.filter {
            it.name.contains(name,ignoreCase = true)
        }
    }

}
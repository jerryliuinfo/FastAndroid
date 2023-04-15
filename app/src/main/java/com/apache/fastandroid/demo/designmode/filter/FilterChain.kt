package com.apache.fastandroid.demo.designmode.filter

/**
 * Created by Jerry on 2023/4/9.
 */
class FilterChain {
    private val filters = mutableListOf<FFilter>()

    fun addFilter(filter:FFilter):FilterChain{
        filters.add(filter)
        return this
    }

    fun filter(persons:List<FPerson>):List<FPerson>{
        var result = persons
        for (filter in filters){
            result = filter.filter(result)
        }
        return result
    }
}
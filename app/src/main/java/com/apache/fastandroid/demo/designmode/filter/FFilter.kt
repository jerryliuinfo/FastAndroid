package com.apache.fastandroid.demo.designmode.filter

/**
 * Created by Jerry on 2023/4/9.
 */
interface FFilter {

    fun filter(persons:List<FPerson>):List<FPerson>
}
package com.apache.fastandroid.demo.designmode.observer

/**
 * Created by Jerry on 2023/3/12.
 */
interface Observer {

    fun onChanged(subject: Subject)
}
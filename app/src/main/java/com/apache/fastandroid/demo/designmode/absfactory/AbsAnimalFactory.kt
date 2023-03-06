package com.apache.fastandroid.demo.designmode.absfactory

import com.apache.fastandroid.demo.designmode.base.Animal

/**
 * Created by Jerry on 2023/3/5.
 */
interface AbsAnimalFactory {

    fun createDog(): Animal
    fun createCat(): Animal

}
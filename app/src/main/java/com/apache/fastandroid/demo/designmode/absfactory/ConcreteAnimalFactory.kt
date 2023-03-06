package com.apache.fastandroid.demo.designmode.absfactory

import com.apache.fastandroid.demo.designmode.base.Animal
import com.apache.fastandroid.demo.designmode.base.Cat
import com.apache.fastandroid.demo.designmode.base.Dog

/**
 * Created by Jerry on 2023/3/5.
 */
class ConcreteAnimalFactory:AbsAnimalFactory {
    override fun createDog(): Animal {
        return Dog()
    }

    override fun createCat(): Animal {
        return Cat()
    }


}
package com.apache.fastandroid.demo.designmode.factory

import com.apache.fastandroid.demo.designmode.base.Animal
import com.apache.fastandroid.demo.designmode.base.AnimalType
import com.apache.fastandroid.demo.designmode.base.Cat
import com.apache.fastandroid.demo.designmode.base.Dog

/**
 * Created by Jerry on 2023/3/5.
 * 工厂模式
 */
object AnimalFactory {
    fun createAnimal(type: AnimalType): Animal? {
        return when (type) {
            AnimalType.DOG -> Dog()
            AnimalType.CAT -> Cat()
        }
    }
}
package com.apache.fastandroid.demo.kt.extensions

/**
 * Created by Jerry on 2022/4/27.
 */
data class Item(val name:String, val price:Float)

data class Order(val items:Collection<Item>)

fun Order.maxPricedItemValue():Float = this.items.maxByOrNull { it.price } ?.price ?: 0F
fun Order.maxPricedItemName():String = this.items.maxByOrNull { it.price } ?.name ?: "NO_PRODUCTS"

val Order.commaDelimitedItemNames:String
    get() = items.map { it.name }.joinToString {
        it + "_"
    }
package com.apache.fastandroid.demo.kt.delegate

/**
 * Created by Jerry on 2022/2/22.
 */
class People(name:String, lastname:String) {

    var lastname:String by FormatDelegate()
    var name:String by FormatDelegate()

    var updateCount = 0

    val d1:String by ReadOnlyDelegate()
    var d2:Int by ReadWriteDelegate()

}
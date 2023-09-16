package com.apache.fastandroid.demo.designmode.builder

import android.os.Handler
import android.os.Message
import com.apache.fastandroid.MainActivity
import java.lang.ref.WeakReference


/**
 * Created by Jerry on 2023/3/12.
 */
class Person2 private constructor(builder: Builder):Cloneable {

    val name: String?
    val age: Int?

    init {
        name = builder.name
        age = builder.age
    }

    public override fun clone(): Any {
        return super.clone() as Person
    }



    class Builder private constructor() {

        var name: String? = null
        var age: Int? = null
        private var maxLength:Int = 0
        companion object{

            @JvmStatic
            fun obtain():Builder{
                return Builder().apply {
                    maxLength = 100
                }
            }
        }

        fun build(): Person2 {
            return Person2(this)
        }
    }



}

package com.apache.fastandroid.demo.designmode.builder

import android.os.Handler
import android.os.Message
import com.apache.fastandroid.MainActivity
import java.lang.ref.WeakReference


/**
 * Created by Jerry on 2023/3/12.
 */
class Person private constructor(builder: Builder):Cloneable {

    val name: String?
    val age: Int?
    val address: String?

    init {
        name = builder.name
        age = builder.age
        address = builder.address
    }

    public override fun clone(): Any {
        return super.clone() as Person
    }

    fun newBuilder():Builder = Builder(this)


    class Builder {

        var name: String? = null
        var age: Int? = null
        var address: String? = null

        internal constructor() {

        }
        internal constructor(person: Person) {
           this.name = person.name
            this.age = person.age
            this.address = person.address
        }




        fun setName(name: String) = apply { this.name = name }

        fun setAge(age: Int) = apply { this.age = age }

        fun setAddress(address: String) = apply { this.address = address }

        fun build(): Person {
            return Person(this)
        }
    }
}


internal class MyHandler(activity: MainActivity) : Handler() {
    private val mActivity: WeakReference<MainActivity> = WeakReference(activity)


    override fun handleMessage(msg: Message) {
        val activity: MainActivity? = mActivity.get()
        if (activity != null) {
            // 处理消息
        }
    }
}
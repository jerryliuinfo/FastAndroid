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
        name = builder.mName
        age = builder.mAge
        address = builder.mAddress
    }

    public override fun clone(): Any {
        return super.clone() as Person
    }


    class Builder {
        var mName: String? = null
        var mAge: Int? = null
        var mAddress: String? = null

        fun setName(name: String) = apply { this.mName = name }

        fun setAge(age: Int) = apply { this.mAge = age }

        fun setAddress(address: String) = apply { this.mAddress = address }

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
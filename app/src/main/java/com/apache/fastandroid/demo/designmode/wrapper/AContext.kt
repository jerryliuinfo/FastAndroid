package com.apache.fastandroid.demo.designmode.wrapper

import android.content.res.Resources

/**
 * Created by Jerry on 2021/10/13.
 */
abstract class AContext {
    abstract fun doSomething1()

    abstract fun getResources(): Resources?


}
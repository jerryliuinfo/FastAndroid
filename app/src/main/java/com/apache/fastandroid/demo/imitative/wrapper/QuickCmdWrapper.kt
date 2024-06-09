package com.apache.fastandroid.demo.imitative.wrapper

/**
 * Created by Jerry on 2024/6/9.
 */
class QuickCmdWrapper(private val base:IQuickCmd): IQuickCmd {
    override fun attach() {
        base.attach()
    }

    override fun method1() {
        base.method1()
    }

    override fun method2() {
        base.method2()
    }

}
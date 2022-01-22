package com.apache.fastandroid.demo.kt

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.microsoft.office.outlook.magnifierlib.frame.FrameCalculator
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.kt_grammer.*
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinKnowledgeFragment:BaseFragment() {
    companion object{
        private const val TAG = "KotlinKnowledgeFragment"
    }
    override fun getLayoutId(): Int {
        return R.layout.kt_grammer
    }

    private lateinit var mFrameCalculator:FrameCalculator

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        btn_on_each.setOnClickListener {
            onEach()
        }
        btn_method_cost.setOnClickListener {
            val costTime = measureTimeMillis {
                Thread.sleep(100)
            }
            Logger.d("costTime:${costTime}")

            println()
        }
        btn_coerceAtLeast.setOnClickListener {
            Logger.d("${3.coerceAtLeast(5)}")
        }
        btn_set_bean_field.setOnClickListener {
            ViewItemBean("Kotlin").title = "Java"
        }

        mFrameCalculator = FrameCalculator{
            Logger.d("frame: ${it}")
        }
        btn_high_order_function.setOnClickListener {

            //这里加了两个 ：： 就变成了对象，一个函数类型的对象，kotlin 中函数可以作为参数传递的本质是：函数可以作为对象存在
            val result1 =  a(::b)
            val result2 = ::b
            println( "result1:$result1, result2:$result2")
        }
    }


    fun a(function: (Int) -> String):String{
        return function(1)
    }

    fun b(param:Int):String{
        return param.toString()
    }


    private fun onEach(vararg numbs: Int):String{
       mutableSetOf("aa","bb","cc").onEach {
           if (it == "bb"){
               return@onEach
           }
           NLog.d(TAG, it)
       }
        return "Hello"
    }
}
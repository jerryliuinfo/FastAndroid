package com.apache.fastandroid.demo.kt

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.kt_grammer.*

/**
 * Created by Jerry on 2021/10/18.
 */
class KotlinKnowledgeFragment:BaseFragment() {
    companion object{
        private const val TAG = "KotlinKnowledgeFragment"
    }
    override fun inflateContentView(): Int {
        return R.layout.kt_grammer
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        btn_on_each.setOnClickListener {
            onEach()
        }
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
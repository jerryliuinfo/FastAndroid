package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.fragment_temp_knowledge.*
import java.lang.StringBuilder

/**
 * Created by Jerry on 2021/9/6.
 */
class KnowledgeFragment: BaseStatusFragmentNew() {
    private val items:MutableList<String> = ArrayList()
    companion object{
        private const val TAG = "KnowledgeFragment"
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_temp_knowledge
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        forAddSeperator()
        addOnPredrawListener()
        btn_textview_horizontal_scroll.setOnClickListener {
            textviewHorizontalScroll()
        }
        btn_collections_suffle.setOnClickListener {
            (0..20).forEach {
                items.add("value:${it}")
            }
            items.subList(0,5).shuffle()
            NLog.d(TAG, "suffle: ${items}")
        }

    }

    /**
     * for循环添加连接符
     */
    private fun forAddSeperator(){
        var map = mapOf("one" to 1, "two" to 2, "three" to 3)
        val paramBuilder = StringBuilder()
        var appendAnd = false
        for (key in map.keys) {
            if (appendAnd){
                paramBuilder.append("&")
            }
            paramBuilder.append(key).append("=").append(map[key])
            appendAnd = true
        }
        NLog.d(TAG, "msg: %s", paramBuilder.toString())
    }

    private fun addOnPredrawListener(){
        tv_name.viewTreeObserver.addOnPreDrawListener (object :ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                tv_name.viewTreeObserver.removeOnPreDrawListener(this)
                return false
            }
        })
    }
    val result = StringBuilder()
    private fun textviewHorizontalScroll(){
        result.let {
            (0..20).forEach{
                result.append("add text:${it}")
            }
        }
        tv_message.setHorizontallyScrolling(true)
        tv_message.movementMethod= ScrollingMovementMethod.getInstance()
        tv_message.text= result.toString()
    }


}
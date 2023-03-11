package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.GridLayoutManager
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.databinding.FragmentTempKnowledge2Binding
import com.apache.fastandroid.demo.temp.adapter.KnowlegeAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.fragment_temp_knowledge.*
import kotlinx.android.synthetic.main.fragment_temp_knowledge2.*
import java.lang.StringBuilder

/**
 * Created by Jerry on 2021/9/6.
 */
class KnowledgeFragment2: BaseBindingFragment<FragmentTempKnowledge2Binding>(FragmentTempKnowledge2Binding::inflate) {

    private val items:MutableList<String> = ArrayList()
    companion object{
        private const val TAG = "KnowledgeFragment"
    }
    private lateinit var adapter: BaseQuickAdapter<ViewItemBean, BaseViewHolder>

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val items = mutableListOf<ViewItemBean>(
             ViewItemBean("forAddSeperator","forAddSeperator")
            ,ViewItemBean("addOnPredrawListener","addOnPredrawListener")
            ,ViewItemBean("textviewHorizontalScroll","textviewHorizontalScroll")
            ,ViewItemBean("LinerLayoutCompat","利用LinerLayoutCompat设置分割线效果")

        )

        adapter = KnowlegeAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager= GridLayoutManager(context,3)
        adapter.setNewData(items)


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
    }

    private fun addOnPredrawListener(){
        tv_name.viewTreeObserver.addOnPreDrawListener (object : ViewTreeObserver.OnPreDrawListener{
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
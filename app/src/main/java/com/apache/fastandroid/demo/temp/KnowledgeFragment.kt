package com.apache.fastandroid.demo.temp

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import com.apache.fastandroid.R
import com.blankj.utilcode.util.MetaDataUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.fragment_temp_knowledge.*
import android.widget.Toast

import android.text.Spanned

import android.text.InputFilter
import com.apache.fastandroid.MainActivity


/**
 * Created by Jerry on 2021/9/6.
 */
class KnowledgeFragment: BaseStatusFragmentNew() {
    private val items:MutableList<String> = ArrayList()
    companion object{
        private const val TAG = "KnowledgeFragment"
    }

    private lateinit var mContext: Context
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
        btn_multi_channel.setOnClickListener {
            ToastUtils.showShort(MetaDataUtils.getMetaDataInApp("HOST"))
        }
        btn_sisuo.setOnClickListener {

        }

        btn_varargs.setOnClickListener {
            initvarArgs("aaa","bbb")
        }

        val str = "Longfu2012"
        et_userName.filters = arrayOf<InputFilter>(MyFilter(str,context))

    }

    class MyFilter(str: String?, val context: Context?) : InputFilter {
        var ch: String? = null
        var str: String? = null
        override fun filter(
            source: CharSequence, start: Int, end: Int,
            dest: Spanned, dstart: Int, dend: Int
        ): CharSequence {
            //最后输入的一个字符
            ch = if (dest.length < str!!.length) {
                //截取未过滤的最后一个字符
                str!!.substring(dstart + start, dstart + end)
            } else {
                return dest.subSequence(dstart, dend)
            }
            return if (ch == source) {


                Toast.makeText(
                    context, "符合要求",
                    Toast.LENGTH_SHORT
                ).show()
                //符合规定要求的字符以原输入显示
                dest.subSequence(dstart, dend).toString() + source.toString()
            } else {
                Toast.makeText(
                    context, "不符合要求喔~",
                    Toast.LENGTH_SHORT
                ).show()
                //如果没有按要求输入字符，则该字符被“*”代替，并显示
                dest.subSequence(dstart, dend).toString() + "*"
            }
        }

        init {
            this.str = str
        }
    }


    private fun initvarArgs(vararg params:String){

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

    private val set1 = mutableListOf<String>("111")
    private val set2 = mutableListOf<String>("222")
    private val set3 = set1.addAll(set2)








}
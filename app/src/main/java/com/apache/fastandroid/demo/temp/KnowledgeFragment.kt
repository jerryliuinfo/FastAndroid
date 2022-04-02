package com.apache.fastandroid.demo.temp

import android.app.AlertDialog
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import com.tesla.framework.common.util.log.NLog
import kotlinx.android.synthetic.main.fragment_temp_knowledge.*
import android.widget.Toast

import android.text.Spanned

import android.text.InputFilter
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentTempKnowledgeBinding
import com.apache.fastandroid.demo.temp.concurrency.Player
import com.blankj.utilcode.util.*
import com.tesla.framework.ui.fragment.BaseVBFragment


/**
 * Created by Jerry on 2021/9/6.
 */
class KnowledgeFragment: BaseVBFragment<FragmentTempKnowledgeBinding>(FragmentTempKnowledgeBinding::inflate) {
    private val items:MutableList<String> = ArrayList()
    companion object{
        private const val TAG = "KnowledgeFragment"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        //注意：语言要是非中文才会 生效
        mBinding.tvQuatity.setOnClickListener {
            var size = 1
            var result1 = resources.getQuantityString(R.plurals.subtitle_plural,size,size)
            println("quantity = 1 output:$result1")

            size = 2
            result1 = resources.getQuantityString(R.plurals.subtitle_plural,size,size)
            println("quantity = 2 output:$result1")

            size = 4
            result1 = resources.getQuantityString(R.plurals.subtitle_plural,size,size)
            println("quantity = 4 output:$result1")
        }

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

        mBinding.btnMultiChannel.setOnClickListener {
            ToastUtils.showShort(MetaDataUtils.getMetaDataInApp("HOST"))
        }

        btn_sisuo.setOnClickListener {

        }

        mBinding.btnVarargs.setOnClickListener {
            initvarArgs("aaa","bbb")
        }

        mBinding.btnConcurrence.setOnClickListener {
            concurencyUsage()
        }

        mBinding.btnShowDialogWithApplicationContext.setOnClickListener {
            showDialogWithApplicationContext()
        }

        mBinding.btnLocationOnscreen.setOnClickListener {
            val location = IntArray(2)
            mBinding.btnLocationOnscreen.getLocationOnScreen(location)
            val rect = Rect()
            rect.apply {
                left = location[0]
                top = location[1]
                right = location[0] + mBinding.btnLocationOnscreen.width
                bottom = location[1] + mBinding.btnLocationOnscreen.height
            }
            println("rect:${rect.toString()}")
        }

        mBinding.btnListInit.setOnClickListener {

        }

        val str = "Longfu2012"
        et_userName.filters = arrayOf<InputFilter>(MyFilter(str,context))
    }


    private fun showDialogWithApplicationContext(){
        AlertDialog.Builder(Utils.getApp())
            .setTitle("I am title")
            .show()

    }
    private fun concurencyUsage() {
        val player1:Player = Player(Player.PLAYER1)
        val player2:Player = Player(Player.PLAYER2)
        Thread(player1).start()
        Thread(player2).start()
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


    private fun <T> filter(originalList: List<T>, condition:(T) -> Boolean): List<T> {
        val list = arrayListOf<T>()
        for (t in originalList) {
            if (condition(t)) {
                list.add(t)
            }
        }
        return list
    }

    private fun <T,R> map(originalList: List<T>, transform:(T) -> R): List<R> {
        val list = arrayListOf<R>()
        for (t in originalList) {
            list.add(transform(t))
        }
        return list
    }


}
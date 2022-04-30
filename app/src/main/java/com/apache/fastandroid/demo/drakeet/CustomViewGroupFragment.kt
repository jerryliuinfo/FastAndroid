package com.apache.fastandroid.demo.drakeet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.apache.fastandroid.demo.drakeet.customview.sample.chat.ChatActivity
import com.apache.fastandroid.demo.drakeet.customview.sample.course.OnlineActivity
import com.seiko.demo.base.CustomLayout

/**
 * Created by Jerry on 2022/4/29.
 */
class CustomViewGroupFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val contentView =  MainLayout(requireContext())
        contentView.btnGoOnline.setOnClickListener {
            startActivity(Intent(requireActivity(),ChatActivity::class.java))
        }
        contentView.btnGotoChat.setOnClickListener {
            startActivity(Intent(requireActivity(),OnlineActivity::class.java))
        }
        return contentView
    }

}

class MainLayout(context:Context, attrs:AttributeSet ?= null):CustomLayout(context,attrs){

    val btnGoOnline = Button(context).autoAddView()
    val btnGotoChat = Button(context).apply {
    }.autoAddView()

    private val btnTest1 = Button(context).autoAddView(){
        setMargins(20.dp,20.dp)
    }
    private val btnTest2 = Button(context).autoAddView()

    init {
        btnGoOnline.text = "在线直播"
        btnGotoChat.text = "WindowInsets使用"
        btnTest1.text = "测试1"
        btnTest2.text = "测试2"
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        autoMeasure(btnGoOnline,btnGotoChat,btnTest1,btnTest2)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        layoutCenter(btnGoOnline, btnGotoChat, btnTest1, btnTest2)

    }

}
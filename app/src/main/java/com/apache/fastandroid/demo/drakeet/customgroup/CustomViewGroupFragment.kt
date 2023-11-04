package com.apache.fastandroid.demo.drakeet.customgroup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.apache.fastandroid.demo.drakeet.customview.sample.TheLayoutDemoFragment
import com.apache.fastandroid.demo.drakeet.customview.sample.chat.ChatActivity
import com.apache.fastandroid.demo.drakeet.customview.sample.course.OnlineActivity
import com.seiko.demo.base.CustomLayout
import com.tesla.framework.kt.launchFragment

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
        contentView.btnTheLayout.setOnClickListener {
            requireActivity().launchFragment<TheLayoutDemoFragment>()
        }
        return contentView
    }

}

class MainLayout(context:Context, attrs:AttributeSet ?= null):CustomLayout(context,attrs){

    val btnGoOnline = Button(context).autoAddView()
    val btnGotoChat = Button(context).autoAddView()
    val btnTheLayout = Button(context).autoAddView()

    private val btnTest1 = Button(context).autoAddView(){
        setMargins(20.dp,20.dp)
    }
    private val btnTest2 = Button(context).autoAddView()

    init {
        btnGoOnline.text = "在线直播"
        btnGotoChat.text = "聊天"
        btnTheLayout.text = "TheLayout"
        btnTest1.text = "测试1"
        btnTest2.text = "测试2"
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        autoMeasure(btnGoOnline,btnGotoChat,btnTheLayout,btnTest1,btnTest2)

    }
    //垂直居中排列
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        layoutCenter(btnGoOnline, btnGotoChat, btnTheLayout,btnTest1, btnTest2)

    }

}
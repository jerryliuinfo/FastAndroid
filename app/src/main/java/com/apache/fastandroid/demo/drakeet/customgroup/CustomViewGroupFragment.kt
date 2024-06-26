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
import com.apache.fastandroid.demo.drakeet.customview.sample.profile.ProfileInfoFragment
import com.seiko.demo.base.CustomLayout
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.kt.launchFragment

/**
 * Created by Jerry on 2022/4/29.
 */
class CustomViewGroupFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val contentView =  MainLayout(requireContext())
        contentView.btnGotoChat.setOnClickListener {
            startActivity(Intent(requireActivity(),ChatActivity::class.java))
        }
        contentView.btnGoOnline.setOnClickListener {
            startActivity(Intent(requireActivity(),OnlineActivity::class.java))
        }
        contentView.btnGotoProfile.setOnClickListener {
            requireActivity().launchFragment<ProfileInfoFragment>()
        }
        return contentView
    }

}

class MainLayout(context:Context, attrs:AttributeSet ?= null):CustomLayout(context,attrs){


    val btnGotoChat = Button(context).autoAddView() {
        text = "聊天"
    }
    /**
     *   addView 不会导致提前 触发多次layout， 实际并不会， view 和 viewgroup 的 measure 和 layout 是 view 被 attch 到 RootView树中才会被调用
     */
    val btnGoOnline = Button(context).autoAddView {
        text = "在线直播"
    }
    val btnGotoProfile = Button(context).autoAddView()
    {
        text = "个人信息"
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        autoMeasure(btnGoOnline,btnGotoChat,btnGotoProfile)
    }
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        //垂直排列
        layoutCenter(btnGotoChat, btnGoOnline, btnGotoProfile)
    }

}
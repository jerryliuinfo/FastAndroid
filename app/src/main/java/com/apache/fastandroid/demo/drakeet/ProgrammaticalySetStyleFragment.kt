package com.apache.fastandroid.demo.drakeet

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.apache.fastandroid.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.tesla.framework.kt.dp
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_drakeet_knowledge.*
import kotlinx.android.synthetic.main.fragment_programily_set_style.*
import kotlin.math.roundToInt

/**
 * Created by Jerry on 2021/10/15.
 * https://t.zsxq.com/AiIIMBu
 */
class ProgrammaticalySetStyleFragment: BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_programily_set_style
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        var bottomAppBar = BottomAppBar(
            ContextThemeWrapper(context, R.style.Theme_BottomBar),
            null,
            com.google.android.material.R.attr.bottomAppBarStyle
        )

        //将style 设置到主题上面，通过 构造Context的时候设置， 需要使用3个参数的构造方法，因为只有一个参数的构造方法不回去读取style的内容
        val threeParamsConstructortextView = TextView(ContextThemeWrapper(context,R.style.textStyle), null,R.style.textStyle)
        threeParamsConstructortextView.setText("我是动态创建出来的3个构造参数的text")

        val oneParamsConstructortextView = TextView(ContextThemeWrapper(context,R.style.textStyle))
        oneParamsConstructortextView.setText("我是动态创建出来的1个构造参数的text")

        content_container.addView(bottomAppBar)
        content_container.addView(threeParamsConstructortextView)
        content_container.addView(oneParamsConstructortextView)

        val relativeLayout = RelativeLayout(ContextThemeWrapper(context,R.style.relativeLayoutStyle))
        relativeLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayout.layoutParams.apply {
            height = 100.dp.roundToInt()
        }

        content_container.addView(relativeLayout)
    }
}
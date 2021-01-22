package com.apache.fastandroid.demo.databinding

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.RetrofitDemoFragment
import com.apache.fastandroid.demo.databinding.practice.DatabinDingBasicDemo

/**
 * Created by Jerry on 2021/1/11.
 */
class DatabindingFragmentDemo:BaseListFragment() {



    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("基本用法","双向绑定",DatabinDingBasicDemo::class.java),
                ViewItemBean("Retrofit","xml中绑定数据",RetrofitDemoFragment::class.java)
        )
    }

}
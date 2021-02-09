package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableBinding
import com.apache.fastandroid.databinding.FragmentDatabindingOnewayBindObservableFieldBinding
import com.apache.fastandroid.databinding.FragmentDatabindingTwoewayEventBinding
import com.apache.fastandroid.demo.bean.UserObservableBean
import com.apache.fastandroid.demo.bean.UserObservableFieldBean
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.ABaseDatabindingFragment
import kotlinx.android.synthetic.main.fragment_databinding_twoeway_event.*
import kotlin.random.Random
import kotlin.random.nextInt


class DatabinDingEventFragment: ABaseDatabindingFragment<FragmentDatabindingTwoewayEventBinding>() {

    private lateinit var user:UserObservableFieldBean
    companion object{
        private const val TAG = "DatabinDingEventFragment"
    }

    override fun inflateContentView(): Int {
        return R.layout.fragment_databinding_twoeway_event
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        user = UserObservableFieldBean()
        binding.user = user
        binding.fragment = this


    }

    fun onClickEvent(){
        user.name.set("zhangsan${Random.nextInt(100)}")
    }





}
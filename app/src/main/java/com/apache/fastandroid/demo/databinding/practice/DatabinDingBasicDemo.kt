package com.apache.fastandroid.demo.databinding.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.support.bean.User
import com.apache.fastandroid.databinding.FragmentDatabindingBasisUserBinding
import com.tesla.framework.common.util.log.NLog
import java.util.Random


/**
 * Created by Jerry on 2021/1/11.
 * activity 中: 使用 xxBing.inflate
 * val binding: ActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater())
 * Fragment中:
 *     val listItemBinding = ListItemBinding.inflate(layoutInflater, viewGroup, false)
       val listItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false)
 */
class DatabinDingBasicDemo: Fragment() {
    companion object{
        private const val TAG = "DatabinDingBasicDemo"
    }
//    val viewModel by lazy {
//        ViewModelProvider(this, ViewModelInject.getUserModelFactory() ).get(UserViewModel::class.java)
//    }

    lateinit var  binding:FragmentDatabindingBasisUserBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_databinding_basis_user, container, false)
        // 1、对布局需要绑定的内容进行加载
        binding = DataBindingUtil.bind(view)!!
        binding!!.user = User("zhangsan")
        return binding.root
    }


}
package com.apache.fastandroid.demo.temp.savestate

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.apache.fastandroid.databinding.FragmentSavestateHandleBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/14.
 * https://www.jianshu.com/p/3cd7bdf9a8c4
 * SaveStateHandle 系统资源限制导致Activity被销毁时数据没有被恢复问题（模拟方法:打开设置-开发者轩轩-不保留活动 ）
 * onSaveInstanceState(Bundle) 方法保存的数据在资源配置更改或 Activity 被意外杀死时都会被保留，但存在存储容量和存取速度的限制。因为 Bundle 有着容量限制，不适合用于存储大量数据，且 onSaveInstanceState(Bundle) 方法会将数据序列化到磁盘，如果要保存的数据很复杂，序列化会消耗大量的内存和时间。
  所以 onSaveInstanceState(Bundle) 仅适合用于存储少量的简单类型的数据
  SavedStateHandle组件可以看作是ViewModel功能的扩展，解决ViewModel无法感知onSaveInstanceState被触发的问题

 */
class SaveStateHandleFragment:BaseVBFragment<FragmentSavestateHandleBinding>(FragmentSavestateHandleBinding::inflate) {

    private val viewModel:SaveStateViewModel by viewModels()
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        println("SaveStateHandleFragment viewModel:${viewModel}, name:${viewModel.nameLiveData.value}, blog:${viewModel.blogLiveData.value}")

        viewBinding.btnInsert.setOnClickListener {
            viewModel.nameLiveData.value = "Zhangsan"
            viewModel.blogLiveData.value = "www.baidu.com"
            viewModel.addNumber()
        }
        updateUI()

        viewModel.nameLiveData.observe(this){
            println("SaveStateHandleFragment nameLiveData onChanged: ${it}")
            updateUI()
        }

        viewModel.getContentNumber().observe(this){
            println("SaveStateHandleFragment ContentNumber onChanged: ${it}")
            updateUI()
        }

        viewModel.blogLiveData.observe(this){
            println("SaveStateHandleFragment blogLiveData onChanged: ${it}")
            updateUI()
        }
    }

    private fun updateUI(){
        viewBinding.tvResult.text = "name:${viewModel.nameLiveData.value}, blog:${viewModel.blogLiveData.value}, number:${viewModel.getContentNumber().value}"

    }

}
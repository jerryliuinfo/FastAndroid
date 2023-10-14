package com.apache.fastandroid.jetpack.viewmodel

import ArticleViewModelFactory
import ViewModelFactorySavedStateRegistryOwner
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.databinding.FragmentJetpackViewmodelRestoreDataBinding
import com.apache.fastandroid.demo.kt.coroutine.CoroutineVewModel
import com.apache.fastandroid.demo.paging.article.data.ArticleRepository
import com.apache.fastandroid.demo.paging.article.ui.ArticleViewModel
import com.apache.fastandroid.demo.temp.savestate.SaveStateViewModel
import com.apache.fastandroid.jetpack.InjectUtil
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.apache.fastandroid.jetpack.viewmodel.factory.ViewModelFactoryWithArgs
import com.tesla.framework.component.di.KotlinViewModelProvider
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2020/11/1.
 */
class ViewModelDemoFragment: BaseBindingFragment<FragmentJetpackViewmodelRestoreDataBinding>(FragmentJetpackViewmodelRestoreDataBinding::inflate) {

    //不能使用这种方式，这种方式生成的ViewModel 在横竖屏切换后数据不会保存
//    private val userInfoViewModel :UserInfoViewModel by lazy {
//        UserInfoViewModel(PostCardReporsity.get())
//    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)


        mBinding.btnViewModelNoArgs.setOnClickListener {

            viewModelWithNoArgsUsage()
        }
        mBinding.btnViewModelWithArgs.setOnClickListener {
            viewModelWithArgsUsage()
        }
        mBinding.btnSaveStateViewModel.setOnClickListener {
            saveStateViewModelUsage()
        }

        mBinding.btnViewModelProvider.setOnClickListener {
            viewModelProvider()
        }

    }

    private fun viewModelProvider() {
        val fragmentViewModel = KotlinViewModelProvider.of(this){
            NormalViewModel()
        }

        val activityViewModel =  KotlinViewModelProvider.of(requireActivity()){
            NormalViewModel()
        }
        println("fragmentViewModel:$fragmentViewModel, activityViewModel:$activityViewModel")
    }

    private fun saveStateViewModelUsage() {
         val viewModel1:SaveStateViewModel by viewModels()
        println("viewModel1:$viewModel1")

    }

    private fun viewModelWithNoArgsUsage() {
        //不带参数的
        val viewModel1: NormalViewModel by viewModels()

        val viewModel2: NormalViewModel = ViewModelProvider(this).get(NormalViewModel::class.java)
        println("viewModel1:$viewModel1, viewModel2:$viewModel2")

    }
    private fun viewModelWithArgsUsage() {
        val viewModel1:ViewModelWithArgs = ViewModelProvider(this,
            ViewModelFactoryWithArgs(InjectUtil.getUserInfoReporsity())
        ).get(ViewModelWithArgs::class.java)

        val viewModel2 by viewModels<ViewModelWithArgs>(
            factoryProducer = { ViewModelFactorySavedStateRegistryOwner(this, InjectUtil.getUserInfoReporsity()) }
        )
        println("viewModelWithArgsUsage viewModel1:$viewModel1, viewModel2:$viewModel2")

    }



}
package com.apache.fastandroid.demo.app

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.apache.fastandroid.databinding.FragmentMvvmMailBinding
import com.apache.fastandroid.network.model.DataHolder
import com.tesla.framework.ui.fragment.BaseBindingFragment
import com.tesla.framework.component.viewstate.State
import com.zwb.mvvm_mall.base.viewstate.StateType

/**
 * Created by Jerry on 2022/1/20.
 *
 */
class MvvmMailDemoFragment: BaseBindingFragment<FragmentMvvmMailBinding>(FragmentMvvmMailBinding::inflate) {

    private val mViewModel:MailViewModel by viewModels()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?)
    {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnLoadData.setOnClickListener{
            mViewModel.loadData()
        }

        mBinding.btnDataHolder.setOnClickListener {
            mViewModel.loadData2()
        }




        mViewModel.topArticleLiveData.observe(this){
            println("data size:${it.size}")
        }

        mViewModel.responseLiveData.observe(this){
            println("responseLiveData size:${it}")

            when(it){
                DataHolder.Loading -> {
                    println("showLoading")
                }
                is DataHolder.Success -> {
                    println("show result:${it.data}")
                }
            }
        }

        mViewModel.connectionStatusLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataHolder.Loading -> {
                }
                is DataHolder.Success -> {
                }
                is DataHolder.Error -> {

                }
            }

            for(i in 0 until 10){

            }
            for(i in 0..10){

            }
        })


        mViewModel.loadState.observe(this,observer)
    }

    private val observer by lazy {
        Observer<State>{
            it?.let {
                when(it.code){
                    StateType.SUCCESS -> showSuccess(it.urlKey)
                    StateType.ERROR -> showError("网络异常",it.urlKey)
                    StateType.NETWORK_ERROR -> showError("网络异常",it.urlKey)
                    StateType.EMPTY -> showEmpty(it.urlKey)
                }
            }
        }
    }
}
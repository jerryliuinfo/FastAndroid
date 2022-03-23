package com.apache.fastandroid.jetpack.hit

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentHitBinding
import com.apache.fastandroid.demo.bean.*
import com.tesla.framework.ui.fragment.BaseVBFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 * 参考:https://mp.weixin.qq.com/s/OEX1d2cU1zGG5BBM-nANBg
 * Hit 使用步骤
 * 1.添加依赖
 *    a:项目根目录添加 classpath
 *    b:app module 添加依赖
 *    c：app module 添加 katp 插件
 * 2.Application 添加 @HiltAndroidApp
 * 3.需要处理依赖注入的类添加 @@AndroidEntryPoint
 * 4. 对需要注入的类添加 @inject 注解
 *
 *
 */

@AndroidEntryPoint
class HitDemoFragment:BaseVBFragment<FragmentHitBinding>(FragmentHitBinding::inflate) {

    /**
     * Hilt注入的字段是不可以声明成private的
     */
    @Inject
    lateinit var truck: Truck


    @Inject
    lateinit var truck2: Truck2

    @Inject
    lateinit var okHttpClient: OkHttpClient


    @Inject
    lateinit var singletonTruck: TruckSingleton

    @Inject
    lateinit var builtInApplicationQualifer: BuiltInApplicationQualifer

    @Inject
    lateinit var builtInActivityQualifer: BuiltInActivityQualifer


    private val viewModel: HitViewModel by lazy { ViewModelProvider(this).get(HitViewModel::class.java) }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        viewBinding.btnInjectMember.setOnClickListener {
            truck.delivery()
        }

        viewBinding.btnInjectWithParam.setOnClickListener {
            truck2.delivery()
        }

        viewBinding.btnInjectInterface.setOnClickListener {
            truck2.deliveryByEngine()
        }

        viewBinding.btnInjectInterfaceMultiParam.setOnClickListener {
            truck2.deliveryByInterfaceWithTwoParams()
        }

        viewBinding.btnInjectThirdParty.setOnClickListener {
            println("inject okhttpClient: ${okHttpClient}")
        }

        viewBinding.btnInjectSingleton.setOnClickListener {
            println("inject singleton truck: ${singletonTruck}")
        }

        viewBinding.btnBuiltinAppQualifier.setOnClickListener {
            println("inject application context:${builtInApplicationQualifer}")
        }

        viewBinding.btnBuiltinActivityQualifier.setOnClickListener {
            println("inject activity context:${builtInActivityQualifer}")
        }

        viewBinding.btnInjectViewModel.setOnClickListener {
            println("inject viewModel :${viewModel}")
            viewModel.doWork()
        }
    }
}
package com.apache.fastandroid.jetpack.hit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.apache.fastandroid.databinding.FragmentHitBinding
import com.apache.fastandroid.demo.bean.*
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.hit.engine.GasEngine
import com.apache.fastandroid.jetpack.hit.interfaces.CarInjectInterface
import com.apache.fastandroid.jetpack.hit.interfaces.CarInjectMultiInterface
import com.apache.fastandroid.jetpack.hit.qualifier.BuiltInActivityQualifer
import com.apache.fastandroid.jetpack.hit.qualifier.BuiltInApplicationQualifer
import com.apache.fastandroid.jetpack.hit.tyre.ChinaTyre
import com.apache.fastandroid.jetpack.hit.viewmodel.HitViewModel
import com.apache.fastandroid.jetpack.hit.viewmodel.HitViewModel2
import com.tesla.framework.ui.activity.BaseVBActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Hilt注入的字段是不可以声明成private的
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
class HitDemoActivity:BaseVBActivity<FragmentHitBinding>(FragmentHitBinding::inflate) {

    /**
     * 不带参数的依赖注入
     */
    @Inject
    lateinit var carNoParam: CarNoParam

    /**
     * 带参数的依赖注入
     */
    @Inject
    lateinit var carParam: CarParam


    @Inject
    lateinit var carInjectInterface: CarInjectInterface


    @Inject
    lateinit var carInjectMultiInterface: CarInjectMultiInterface



    //由于 注入的 OkHttpClient 不是单例，因此这里okHttpClient 和 okHttpClient2 是两个不同的对象
    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var okHttpClient2: OkHttpClient



    @Inject
    lateinit var mRetrofit: Retrofit

    @Inject
    lateinit var mRetrofit2: Retrofit

    //单例-自己的类
    @Inject
    lateinit var mSingletonCar: CarSingleton

    @Inject
    lateinit var mApiHelper: ApiHelper

    @Inject
    lateinit var mApiHelper2: ApiHelper


    @Inject
    lateinit var builtInApplicationQualifer: BuiltInApplicationQualifer

    @Inject
    lateinit var builtInActivityQualifer: BuiltInActivityQualifer


    @Inject
    lateinit var driver2: Driver2

    @Inject
    lateinit var driver3: Driver3



    private val mNormalViewModel: NormalViewModel by viewModels {
        NormalViewModel.NormalViewModelFactory(Repository())
    }
//

     val  mHitViewModel: HitViewModel by lazy{
        ViewModelProvider(this).get(HitViewModel::class.java)
    }

    val  mHitViewModel2: HitViewModel2 by lazy{
        ViewModelProvider(this).get(HitViewModel2::class.java)
    }



    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)


        //外部构造对象，手动依赖注入多 参数
        mBinding.btnInjectByHandleMultiParam.setOnClickListener {
            val car = Car3(GasEngine(),ChinaTyre())
            car.start()
        }

        //Hit 依赖注入，无参数
        mBinding.btnInjectMember.setOnClickListener {
            carNoParam.start()
        }
        //Hit 依赖注入，有参数
        mBinding.btnInjectWithParam.setOnClickListener {
            carParam.start()
        }

        //Hit 依赖注入，参数为接口类型, 且注入的是同一个实例
        mBinding.btnInjectInterface.setOnClickListener {
            carInjectInterface.deliveryByEngine()
        }

        //Hit 依赖注入，参数为接口类型, 且注入不同 实例
        mBinding.btnInjectInterfaceMultiParam.setOnClickListener {
            carInjectMultiInterface.deliveryByInterfaceWithTwoParams()
        }

        //第三方类注入OKhttp
        mBinding.btnInjectThirdParty.setOnClickListener {
            println("inject okhttpClient: ${okHttpClient}， okHttpClient2:${okHttpClient2}")
        }
        //第三方类注入 Retrofit
        mBinding.btnInjectThirdPartyRetrofit.setOnClickListener {
            println("inject retrofit: ${mRetrofit}, mRetrofit2:$mRetrofit2")
        }

        mBinding.btnInjectSingleton.setOnClickListener {
            println("inject singleton truck: ${mSingletonCar}, apiHelper:${mApiHelper}, apiHelper2:${mApiHelper2}")
        }

        //Application 作用域
        mBinding.btnBuiltinAppQualifier.setOnClickListener {
            println("inject application context:${builtInApplicationQualifer}")
        }

        mBinding.btnBuiltinActivityQualifier.setOnClickListener {
            println("inject activity context:${builtInActivityQualifer}")
        }

        mBinding.btnApplicationActivity.setOnClickListener {
            driver2.drive()
            driver3.drive()
        }

        mBinding.btnNormalViewModelWithParamter.setOnClickListener {
            println("normal viewModel :${mNormalViewModel}")
            mNormalViewModel.doWork()
        }

        mBinding.btnInjectViewModel.setOnClickListener {
            println("inject viewModel :${mHitViewModel}")
            mHitViewModel.doWork()
        }

        mBinding.btnInjectViewModel2.setOnClickListener {
            println("inject viewModel :${mHitViewModel2}")
            mHitViewModel2.doWork()
        }


    }
}
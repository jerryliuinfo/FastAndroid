package com.apache.fastandroid.jetpack.hit

import android.os.Bundle
import androidx.activity.viewModels
import com.apache.fastandroid.databinding.FragmentHitBinding
import com.apache.fastandroid.demo.bean.*
import com.apache.fastandroid.jetpack.hit.engine.GasEngine
import com.apache.fastandroid.jetpack.hit.tyre.ChinaTyre
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.activity.BaseVmActivity
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
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
class HitDemoActivity:BaseVmActivity<FragmentHitBinding>(FragmentHitBinding::inflate) {

    /**
     * Hilt注入的字段是不可以声明成private的
     */


    @Inject
    lateinit var carNoParam: CarNoParam

    @Inject
    lateinit var carParam: CarParam


    @Inject
    lateinit var carInjectInterface: CarInjectInterface


    @Inject
    lateinit var carInjectMultiInterface: CarInjectMultiInterface

    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var mRetrofit: Retrofit


    @Inject
    lateinit var mSingletonCar: CarSingleton

    @Inject
    lateinit var builtInApplicationQualifer: BuiltInApplicationQualifer

    @Inject
    lateinit var builtInActivityQualifer: BuiltInActivityQualifer


    private val mNormalViewModel: NormalViewModel by viewModels {
        NormalViewModel.NormalViewModelFactory(Repository())
    }

    lateinit var mHitViewModel: HitViewModel



    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        //内部构造对象，非依赖注入
        mBinding.btnNotInject.setOnClickListener {
            val car = Car()
            car.start()
        }

        //外部构造对象，手动依赖注入单参数
        mBinding.btnInjectByHandle.setOnClickListener {
//            val car = Car2(ElectricEngine())
            val car = Car2(GasEngine())
            car.start()
        }


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
            println("inject okhttpClient: ${okHttpClient}")
        }
        //第三方类注入 Retrofit
        mBinding.btnInjectThirdPartyRetrofit.setOnClickListener {
            println("inject retrofit: ${mRetrofit}")
        }

        mBinding.btnInjectSingleton.setOnClickListener {
            println("inject singleton truck: ${mSingletonCar}")
        }

        mBinding.btnBuiltinAppQualifier.setOnClickListener {
            println("inject application context:${builtInApplicationQualifer}")
        }

        mBinding.btnBuiltinActivityQualifier.setOnClickListener {
            println("inject activity context:${builtInActivityQualifer}")
        }

        mBinding.btnNormalViewModel.setOnClickListener {
            println("normal viewModel :${mNormalViewModel}")
            mNormalViewModel.doWork()
        }

        mBinding.btnInjectViewModel.setOnClickListener {
            println("inject viewModel :${mHitViewModel}")
            mHitViewModel.doWork()
        }
        mBinding.btnToActivity2.setOnClickListener {
            launchActivity<HitDemoActivity2>(this)
        }


    }
}
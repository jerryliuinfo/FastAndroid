package com.apache.fastandroid.jetpack.hit

import android.os.Bundle
import androidx.activity.viewModels
import com.apache.fastandroid.databinding.FragmentHit2Binding
import com.apache.fastandroid.databinding.FragmentHitBinding
import com.apache.fastandroid.demo.bean.*
import com.apache.fastandroid.jetpack.hit.engine.GasEngine
import com.apache.fastandroid.jetpack.hit.tyre.ChinaTyre
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
class HitDemoActivity2:BaseVmActivity<FragmentHit2Binding>(FragmentHit2Binding::inflate) {


    @Inject
    lateinit var okHttpClient: OkHttpClient

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var mSingletonCar: CarSingleton

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)

        println("inject okhttpClient: ${okHttpClient}, retrofit:${retrofit},mSingletonCar:$mSingletonCar")

    }
}
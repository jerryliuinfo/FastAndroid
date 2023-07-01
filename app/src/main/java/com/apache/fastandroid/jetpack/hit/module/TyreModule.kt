package com.apache.fastandroid.jetpack.hit.module

import com.apache.fastandroid.jetpack.hit.tyre.ChinaTyre
import com.apache.fastandroid.jetpack.hit.tyre.Tyre
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * Created by Jerry on 2022/3/20.
 */
@Module //表示这是一个提供注入实例的模块
@InstallIn(ActivityComponent::class)
abstract class TyreModule {

    /**
     * 首先我们要定义一个抽象函数，为什么是抽象函数呢？因为我们并不需实现具体的函数体。
       其次，这个抽象函数的函数名叫什么都无所谓，你也不会调用它，不过起个好点的名字可以有助于你的阅读和理解。
       第三，抽象函数的返回值必须是Engine，表示用于给Engine类型的接口提供实例。那么提供什么实例给它呢？抽象函数接收了什么参数，就提供什么实例给它。
     */
    @Binds
    abstract fun bindTyre(chinaTyre: ChinaTyre): Tyre


}

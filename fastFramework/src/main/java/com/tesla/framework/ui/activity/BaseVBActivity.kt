package com.tesla.framework.ui.activity

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.tesla.framework.kt.inflateBinding

/**
 * Created by JerryLiu on 17/04/08.
 * 不使用反射创建 ViewBinding
 */
abstract class BaseVBActivity<V : ViewBinding>(var inflater: (inflater: LayoutInflater) -> V) :BaseActivity(){

    protected lateinit var mBinding: V


    override fun inflateView(): View {
        mBinding = inflateBinding<V>(layoutInflater).also {
            setContentView(it.root)
        }
        return mBinding.root
    }


}
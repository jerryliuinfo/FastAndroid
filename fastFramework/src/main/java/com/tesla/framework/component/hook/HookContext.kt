package com.tesla.framework.component.hook

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Resources
import com.tesla.framework.component.hook.HookResources

/**
 * Created by Jerry on 2023/11/2.
 */
class HookContext(base:Context):ContextWrapper(base) {

    private var mHookResources: HookResources?= null


    override fun getResources(): Resources {
        val originRes = super.getResources()
        if (mHookResources == null){
            mHookResources = HookResources(originRes)
        }
        val result = mHookResources!!
        if (result.configuration != originRes.configuration || result.displayMetrics != originRes.displayMetrics){
            result.updateConfiguration(originRes.configuration,originRes.displayMetrics)
        }
        return result
    }
}
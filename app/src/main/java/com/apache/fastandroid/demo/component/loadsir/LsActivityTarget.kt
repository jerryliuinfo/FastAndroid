package com.apache.fastandroid.demo.component.loadsir

import android.app.Activity
import com.apache.fastandroid.demo.component.loadsir.ILSTarget

/**
 * Created by Jerry on 2023/4/16.
 */
class LsActivityTarget:ILSTarget {
    override fun isEquals(target: Any): Boolean {
        return target is Activity
    }

}
package com.apache.fastandroid.demo.temp

import androidx.annotation.IntDef
import com.apache.fastandroid.databinding.FragmentCommonBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by Jerry on 2021/10/22.
 */
class IntDefFragment : BaseVBFragment<FragmentCommonBinding>(FragmentCommonBinding::inflate) {



    @IntDef(GET_ONE, GET_TWO, GET_THREE)
    @Retention(RetentionPolicy.SOURCE)
    annotation class TypeInfoFlags

    fun getTypeBean(@TypeInfoFlags flags: Int) {
        if (flags and GET_ONE != 0) {
        } else if (flags and GET_TWO != 0) {
        } else if (flags and GET_THREE != 0) {
        }
    }

    companion object {
        const val GET_ONE = 0x00000002
        const val GET_TWO = 0x00000004
        const val GET_THREE = 0x00000008
    }
}
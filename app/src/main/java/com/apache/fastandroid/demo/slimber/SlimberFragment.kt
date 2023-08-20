package com.apache.fastandroid.demo.slimber

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentSlimberBinding
import com.tesla.framework.component.logD
import com.tesla.framework.component.logV
import com.tesla.framework.ui.fragment.BaseBindingFragment
import com.tesla.framework.component.log.Timber


/**
 * Created by Jerry on 2021/8/31.
 * https://github.com/DylanCaiCoding/MMKV-KTX
 */
class SlimberFragment: BaseBindingFragment<FragmentSlimberBinding>(FragmentSlimberBinding::inflate) {
    companion object{
        private const val TAG = "MMKVFragment"
    }

    private val longLog = "this is a long log"

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        Timber.uprootAll()
       mBinding.btnNomralLog.setOnClickListener {
           //不管 Timber 有没有种树，都会构建 longLog 字符串 和 预计算
           Timber.d(longLog)
       }

        mBinding.btnSlimberLog.setOnClickListener {

            logD {
                longLog
            }

            logV {
                longLog
            }
        }


    }


}
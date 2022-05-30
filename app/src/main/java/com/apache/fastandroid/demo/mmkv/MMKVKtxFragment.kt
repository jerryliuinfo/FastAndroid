package com.apache.fastandroid.demo.mmkv

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentMmkvBinding
import com.apache.fastandroid.databinding.FragmentMmkvKtxBinding
import com.tencent.mmkv.MMKV
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.fragment_mmkv.*
import org.junit.Assert
import java.util.*


/**
 * Created by Jerry on 2021/8/31.
 * https://github.com/DylanCaiCoding/MMKV-KTX
 */
class MMKVKtxFragment: BaseVBFragment<FragmentMmkvKtxBinding>(FragmentMmkvKtxBinding::inflate) {
    companion object{
        private const val TAG = "MMKVFragment"
    }



    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnBasic.setOnClickListener {
            basicUsage()
        }

    }

    private fun basicUsage(){
        val origin = MMKVRepository.i1
        println("before: $origin")

        MMKVRepository.i1 = -1
        println("after: $MMKVRepository.i1")

        val originB1 = MMKVRepository.b1
        println("boolean before : $originB1")

        MMKVRepository.b1 = true
        println("boolean after: $MMKVRepository.b1")
    }
}
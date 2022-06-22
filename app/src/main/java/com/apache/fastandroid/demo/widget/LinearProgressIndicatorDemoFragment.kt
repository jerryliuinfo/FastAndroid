package com.apache.fastandroid.demo.widget

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.FragmentLinearProgressIndicatorBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Jerry on 2022/6/22.
 */
class LinearProgressIndicatorDemoFragment:BaseVBFragment<FragmentLinearProgressIndicatorBinding>(FragmentLinearProgressIndicatorBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.progressLinear.show()

        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                delay(1000)
            }
            mBinding.progressLinear.hide()
        }
    }
}
package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.os.SystemClock
import android.telephony.PhoneNumberUtils
import android.text.format.DateUtils
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentEdittextLossFocusBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.getDrawable
import com.tesla.framework.ui.fragment.BaseBindingFragmentRef

/**
 * Created by Jerry on 2023/12/2.
 * https://androidperformance.com/2014/06/03/android-edittext-do-not-auto-get-focus/
 * 如何让EditText不自动获取焦点
 *
 * Android 小技巧 - 1:ttps://androidperformance.com/2014/05/28/android-tips-round-up-1/#/%E6%AD%A3%E6%96%87
 * Android 小技巧 - 2:https://androidperformance.com/2014/05/31/android-tips-round-up-2/#/%E6%AD%A3%E6%96%87
 *
 * Android 开发中一些很有用但你不知道的方法：https://blog.csdn.net/qq_21763489/article/details/53692792
 *
 */
class AndroidPerformanceDemoFragment : BaseBindingFragmentRef<FragmentEdittextLossFocusBinding>() {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnSetError.setOnClickListener {
            // 验证用户输入的时候很棒
            if (mBinding.etName.text.length < 5) {
                mBinding.etName.setError(
                    "Length is less than 5",
                    R.drawable.black_dot.getDrawable(requireContext())
                )
            }
        }

        mBinding.btnKeypadLettersToDigits.setOnClickListener {

            val result =
                PhoneNumberUtils.convertKeypadLettersToDigits("ASDFGH")
            Logger.d("convertKeypadLettersToDigits:$result")
        }




    }
}
package com.apache.fastandroid.demo.temp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.temp_bit_or_and_demo.*


/**
 * Created by Jerry on 2021/3/1.
 */
class BitOrAndFragment: BaseFragment() {
    companion object{
        val TAG = "EnumFragment"
    }
    private var mPrivateFlags = 0x000000

    private val PFLAG_1 = 0x00000001


    val PFLAG_2 = 0x00000002


    val PFLAG_3 = 0x00000004


    val PFLAG_4 = 0x00000008

    override fun inflateContentView(): Int {
        return R.layout.temp_bit_or_and_demo
    }

    @SuppressLint("RestrictedApi")
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        btnOr.setOnClickListener {
            mPrivateFlags = mPrivateFlags or PFLAG_1
            NLog.d(TAG, "result1: %s", Integer.toBinaryString(mPrivateFlags))

            mPrivateFlags = mPrivateFlags or PFLAG_2
            NLog.d(TAG, "result2: %s", Integer.toBinaryString(mPrivateFlags))

            mPrivateFlags = mPrivateFlags or PFLAG_3
            NLog.d(TAG, "result3: %s", Integer.toBinaryString(mPrivateFlags))

            mPrivateFlags = mPrivateFlags or PFLAG_4
            NLog.d(TAG, "result4: %s", Integer.toBinaryString(mPrivateFlags))
        }
        btnAnd.setOnClickListener {

        }

        btnClearBit.setOnClickListener {
            mPrivateFlags = mPrivateFlags and  PFLAG_1.inv()
            NLog.d(TAG, "result1: %s", Integer.toBinaryString(mPrivateFlags))
        }



        btnJudgeBit.setOnClickListener {
            var result1:Boolean = (mPrivateFlags and PFLAG_1) != 0
            var result2:Boolean = (mPrivateFlags and PFLAG_2) != 0
            NLog.d(TAG, "result1 flag1: %s, result2: %s", result1,result2)
        }








    }




}
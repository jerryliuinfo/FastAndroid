package com.apache.fastandroid.demo.blacktech.sdkeditor

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentBlackTechBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/1/15.
 */
class CommonBlackTechFragment :
    BaseBindingFragment<FragmentBlackTechBinding>(FragmentBlackTechBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        //https://github.com/iwhys/sdk-editor-plugin/blob/master/项目简析.md
        mBinding.btnSdkEditor.setOnClickListener {
//            BuildCompat.isAtLeastR()
        }
        mBinding.btnAndroidHiddenApiByPass.setOnClickListener {
//           HiddenApiBypass.invoke(ApplicationInfo::class.java,  ApplicationInfo(), "usesNonSdkApi"/*, args*/)

        }
    }
}
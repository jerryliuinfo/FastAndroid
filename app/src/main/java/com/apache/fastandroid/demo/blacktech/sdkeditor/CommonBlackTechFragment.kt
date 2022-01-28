package com.apache.fastandroid.demo.blacktech.sdkeditor

import android.content.pm.ApplicationInfo
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentBlackTechBinding
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.tesla.framework.ui.fragment.BaseVMFragment
import org.lsposed.hiddenapibypass.HiddenApiBypass
import java.lang.reflect.Method
import java.util.Arrays.stream
import java.util.stream.StreamSupport.stream

/**
 * Created by Jerry on 2022/1/15.
 */
class CommonBlackTechFragment :
    BaseVMFragment<FragmentBlackTechBinding>(FragmentBlackTechBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        //https://github.com/iwhys/sdk-editor-plugin/blob/master/项目简析.md
        viewBinding.btnSdkEditor.setOnClickListener {
//            BuildCompat.isAtLeastR()
        }
        viewBinding.btnAndroidHiddenApiByPass.setOnClickListener {
//           HiddenApiBypass.invoke(ApplicationInfo::class.java,  ApplicationInfo(), "usesNonSdkApi"/*, args*/)

        }
    }
}
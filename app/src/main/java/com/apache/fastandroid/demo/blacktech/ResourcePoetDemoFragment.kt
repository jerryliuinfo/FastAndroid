package com.apache.fastandroid.demo.blacktech

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentResourcePoetBinding
import com.commit451.resourcespoet.ResourcesPoet
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/7/19.
 */
class ResourcePoetDemoFragment:BaseBindingFragment<FragmentResourcePoetBinding>(FragmentResourcePoetBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnGenerateXml.setOnClickListener {
            val poet = ResourcesPoet.create()
                .addString("app_name", "Test")
                .addColor("color_primary", "#FF0000")
                .addBool("is_cool", true)
                .addComment("This is a comment")
                .addDrawable("logo", "@drawable/logo")
                .addStyle("AppTheme.Dark", "Base.AppTheme.Dark")
                // etc
                .indent(true)
            val xml: String = poet.build()
            println(xml)
        }
    }
}
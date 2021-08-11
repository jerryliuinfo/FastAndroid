package com.apache.fastandroid.demo.glide

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.glide_transform_demo.*

/**
 * Created by Jerry on 2021/6/24.
 */
class GlideTransformFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.glide_transform_demo
    }

    val url = "https://www.baidu.com/img/bd_logo1.png"

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        btn_load.setOnClickListener {
            Glide.with(activity).load(url).into(image)
        }
        btn_diable_transform.setOnClickListener {
            Glide.with(activity).load(url).dontTransform().into(image)
        }

        btn_override.setOnClickListener {
            Glide.with(activity).load(url).override(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).into(image)
        }

    }




}
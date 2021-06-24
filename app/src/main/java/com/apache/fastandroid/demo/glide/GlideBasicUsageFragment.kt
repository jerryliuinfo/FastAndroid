package com.apache.fastandroid.demo.glide

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.bumptech.glide.Glide
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.glide_basic_usage_demo.*

/**
 * Created by Jerry on 2021/6/24.
 */
class GlideBasicUsageFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.glide_basic_usage_demo
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        btn.setOnClickListener {
            val url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fblog%2F201406%2F12%2F20140612042459_nN5mZ.jpeg&refer=http%3A%2F%2Fcdn.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1627090612&t=376413dceca7371b4b2086cdbb955ff2"
            Glide.with(activity).load(url).into(image)
        }
    }
}
package com.apache.fastandroid.demo.nodrawable

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.DrawableCustomBinding
import com.apache.fastandroid.demo.drawable.LoadingDrawable
import com.tesla.framework.kt.dpInt
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.drawable_custom.*

/**
 * Created by Jerry on 2021/2/2.
 * android:textCursorDrawable   这个属性是用来控制光标颜色的，@null"   是作用是让光标颜色和text color一样,如果需要自定义颜色，需要自定义一个drawable文件
 */
class LoadingDrawableFragment: BaseBindingFragment<DrawableCustomBinding>(DrawableCustomBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_loading)
        val loadingDrawable =
            LoadingDrawable(
                bitmap,
                21.dpInt
            )
        loadingDrawable.setBounds(0, 0, 78.dpInt, 32.dpInt)
        imageview.setImageDrawable(loadingDrawable)
    }
}
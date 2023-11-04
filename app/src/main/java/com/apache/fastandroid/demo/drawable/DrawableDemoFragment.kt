package com.apache.fastandroid.demo.drawable

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDrawableDemoBinding
import com.tesla.framework.kt.dp
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.drawable_custom.*

/**
 * Created by Jerry on 2021/12/14.
 */
class DrawableDemoFragment: BaseBindingFragment<FragmentDrawableDemoBinding>(FragmentDrawableDemoBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_loading)
        val loadingDrawable =
            LoadingDrawable(
                bitmap, 21.dp
            )
        loadingDrawable.setBounds(0, 0, 78.dp, 32.dp)
        imageview.setImageDrawable(loadingDrawable)
    }
}
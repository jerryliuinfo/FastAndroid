package com.apache.fastandroid.demo.coorinator

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.CoordinatorBasicBinding
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import com.tesla.framework.ui.fragment.BaseBindingFragment


/**
 * Created by Jerry on 2020/11/19.
 * 当ScrollView将要向下滚动的时候，优先滚动的是自己，当自己滚动到顶部头的时候，再开始触发滚动AppBarLayoout中的childView；
   当ScrollView将要向上滚动的时候， 优先将AppBarLayout的childView滚出屏幕，然后ScrollView才开始滚动；

 */
class CoordinatorScrollFragment: BaseBindingFragment<CoordinatorBasicBinding>(CoordinatorBasicBinding::inflate) {

    val size = 3
    var count:Int = 0
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnUpdateMode.setOnClickListener {

            val layoutParams:AppBarLayout.LayoutParams = mBinding.toolbar.layoutParams as AppBarLayout.LayoutParams
            if (count % size ==  0){
                layoutParams.scrollFlags = SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            }else if (count % size ==  1){
                layoutParams.scrollFlags = SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS or SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED
            } else if (count % size ==  2){
                layoutParams.scrollFlags = SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
            }
            mBinding.toolbar.layoutParams = layoutParams
            count++
        }


    }

}
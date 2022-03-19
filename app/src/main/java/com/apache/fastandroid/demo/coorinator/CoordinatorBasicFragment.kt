package com.apache.fastandroid.demo.coorinator

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.CoordinatorBasicBinding
import com.apache.fastandroid.widget.SpaceItemDecoration
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.coordinator_basic.*

/**
 * Created by Jerry on 2020/11/19.
 * 当ScrollView将要向下滚动的时候，优先滚动的是自己，当自己滚动到顶部头的时候，再开始触发滚动AppBarLayoout中的childView；
   当ScrollView将要向上滚动的时候， 优先将AppBarLayout的childView滚出屏幕，然后ScrollView才开始滚动；

 */
class CoordinatorBasicFragment: BaseVBFragment<CoordinatorBasicBinding>(CoordinatorBasicBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


    }

}
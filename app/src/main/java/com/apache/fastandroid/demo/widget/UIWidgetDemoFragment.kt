package com.apache.fastandroid.demo.widget

import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.ui.BaseTabLayoutFragment
import com.apache.fastandroid.artemis.ui.bean.PageModel

/**
 * Created by Jerry on 2022/3/12.
 */
class UIWidgetDemoFragment: BaseTabLayoutFragment() {
    override fun loadPageModels(): MutableList<PageModel> {
        return arrayListOf(
            PageModel(R.layout.layout_pciker, "圆形头像"),
            PageModel(R.layout.layout_pciker, "圆形头像"),
        )
    }

}
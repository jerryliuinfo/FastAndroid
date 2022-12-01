package com.apache.fastandroid.demo.coorinator

import com.apache.fastandroid.databinding.CoordinatorScrollflagsEnteralwaysBinding


import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2020/11/19.
 * 使用enterAlways，必须要带上scroll,否则没有效果
 * enterAlways决定向下滚动时Scrolling View和Child View之间的滚动优先级问题
 *
 * 当ScrollView将要向下滚动的时候，优先滚动的是AppBarLayout中的childView，当childView完全滚动进入屏幕的时候，才开始滚动 ScrollView
   当ScrollView将要向上滚动的时候， 优先将AppBarLayout的childView滚出屏幕，然后ScrollView才开始滚动

 */
class CoordinatorEnterAlwaysFragment :
    BaseBindingFragment<CoordinatorScrollflagsEnteralwaysBinding>(CoordinatorScrollflagsEnteralwaysBinding::inflate) {





}
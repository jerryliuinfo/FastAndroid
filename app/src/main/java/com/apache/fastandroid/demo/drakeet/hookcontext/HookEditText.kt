package com.apache.fastandroid.demo.drakeet.hookcontext

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


/**
 * Description:TODO
 * Create Time:2021/12/20 10:53 下午
 * Author:jerry
 *
 */

/*
class HookEditText(context:Context,attrs: AttributeSet ?= null) : AppCompatEditText(HookContext(context),attrs) {
 init {
 //注意: 这里要使用 getContext，不能使用 context, 因为这里访问到的context为 constructor的context，是未包装过的
  setText(getContext().getString(123))

 }
}*/

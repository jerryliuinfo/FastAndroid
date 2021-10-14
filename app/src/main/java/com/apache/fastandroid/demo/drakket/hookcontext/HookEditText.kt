package com.apache.fastandroid.demo.drakket.hookcontext

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

/**
 * Created by Jerry on 2021/10/13.
 */
class HookEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(HookContext(context), attrs) {
    init {
        setText(getContext().getString(123))
    }
}
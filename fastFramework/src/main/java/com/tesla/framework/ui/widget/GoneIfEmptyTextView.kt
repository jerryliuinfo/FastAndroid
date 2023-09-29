package com.tesla.framework.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView

class GoneIfEmptyTextView constructor(context: Context, attrs: AttributeSet? = null) : AppCompatTextView(context, attrs) {
    override fun onTextChanged(text: CharSequence, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        visibility = if (text.isEmpty()) View.GONE else View.VISIBLE
    }
}

package com.tesla.framework.common.util

import android.text.Editable
import android.widget.EditText
import com.blankj.utilcode.util.KeyboardUtils

/**
 * Created by Jerry on 2022/2/7.
 */
class HideTextWatcher(private val editText: EditText):SimpleTextWatcher() {

    private val mMaxLength:Int = AnimateUtil.getMaxLengthForTextView(editText)

    private var mStr:String ?= null

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)

        mStr = s?.toString()
    }

    override fun afterTextChanged(s: Editable?) {
        super.afterTextChanged(s)
        if (mStr.isNullOrEmpty()){
            return
        }
        if (mStr?.length!! >= mMaxLength){
            KeyboardUtils.hideSoftInput(editText)
        }
    }
}
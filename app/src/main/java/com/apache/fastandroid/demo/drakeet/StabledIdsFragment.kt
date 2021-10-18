package com.apache.fastandroid.demo.drakeet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.apache.fastandroid.R
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_stable_ids.*

/**
 * Created by Jerry on 2021/10/18.
 */
class StabledIdsFragment:BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_stable_ids
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        btn_hash_conflict.setOnClickListener {
            NLog.d(TAG, "Aa's hashCode is:${"Aa".hashCode()}, BB' s hashCode is: ${"BB".hashCode()}")
        }
    }




}
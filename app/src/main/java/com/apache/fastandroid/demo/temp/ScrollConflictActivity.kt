package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.View
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.TempScrollConflictBinding
import com.tesla.framework.ui.activity.BaseVmActivityNew

/**
 * Created by blueberry on 2016/6/20.
 */
class ScrollConflictActivity : BaseVmActivityNew<TempScrollConflictBinding>(TempScrollConflictBinding::inflate), View.OnClickListener {

    override fun layoutInit(savedInstanceState: Bundle?) {
        super.layoutInit(savedInstanceState)
        findViewById<View>(R.id.btn_out_scroll_hv).setOnClickListener(this)
        findViewById<View>(R.id.btn_out_scroll_vv).setOnClickListener(this)
        findViewById<View>(R.id.btn_innner_scroll_hv).setOnClickListener(this)
        findViewById<View>(R.id.btn_innner_scroll_vv).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_out_scroll_hv -> supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    ScrollConflictFragment.newInstance(ScrollConflictFragment.OUT_HV)
                )
                .commit()
            R.id.btn_out_scroll_vv -> supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    ScrollConflictFragment.newInstance(ScrollConflictFragment.OUT_VV)
                )
                .commit()
            R.id.btn_innner_scroll_hv -> supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    ScrollConflictFragment.newInstance(ScrollConflictFragment.INNER_HV)
                )
                .commit()
            R.id.btn_innner_scroll_vv -> supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    ScrollConflictFragment.newInstance(ScrollConflictFragment.INNER_VV)
                )
                .commit()
        }
    }
}
package com.apache.fastandroid.demo.constraint.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.constraint_place_holder.*


class ConstrainPlaceHolderFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.constraint_place_holder
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        favorite.setOnClickListener {
            onClickView(it)
        }
        mail.setOnClickListener {
            onClickView(it)
        }
        save.setOnClickListener {
            onClickView(it)
        }
        play.setOnClickListener {
            onClickView(it)
        }
    }

    private fun onClickView(view:View){
        placeholder.setContentId(view.id)
    }
}
package com.apache.fastandroid.demo.recycleview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseFragment
import com.apache.fastandroid.demo.adapter.AnimatorAdapter
import com.apache.fastandroid.demo.recycleview.data.DataServer
import com.chad.baserecyclerviewadapterhelper.entity.Status
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jaredrummler.materialspinner.MaterialSpinner
import com.tesla.framework.common.util.anim.CustomAnimation
import kotlinx.android.synthetic.main.recycleview_animation.*

/**
 * Created by Jerry on 2021/5/3.
 */
class RecycleViewAnimationFragment: BaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.recycleview_animation
    }


    private lateinit var mAnimationAdapter: BaseQuickAdapter<Status,BaseViewHolder>


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        initAdapter()
        initMenu()
    }

    private fun initAdapter(){
        mAnimationAdapter = AnimatorAdapter(DataServer.getSampleData(100))
        mAnimationAdapter.openLoadAnimation()
        val mFirstPageItemCount = 3
        mAnimationAdapter.setNotDoAnimationCount(mFirstPageItemCount)
        recycleview.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        recycleview.adapter = mAnimationAdapter
    }


    private fun initMenu() {
        val spinner = findViewById<View>(R.id.spinner) as MaterialSpinner
        spinner.setItems("AlphaIn", "ScaleIn", "SlideInBottom", "SlideInLeft", "SlideInRight", "Custom")
        spinner.setOnItemSelectedListener { view, position, id, item ->
            when (position) {
                0 -> mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN)
                1 -> mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
                2 -> mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
                3 -> mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT)
                4 -> mAnimationAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT)
                5 -> mAnimationAdapter.openLoadAnimation(CustomAnimation())
                else -> {
                }
            }
            recycleview.adapter = mAnimationAdapter
        }
        //init firstOnly state
        mAnimationAdapter.isFirstOnly(false)
        val switchButton: CheckBox = findViewById<View>(R.id.switch_button) as CheckBox
        switchButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mAnimationAdapter.isFirstOnly(true)
            } else {
                mAnimationAdapter.isFirstOnly(false)
            }
            mAnimationAdapter.notifyDataSetChanged()
        })
    }




}
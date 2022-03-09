package com.apache.fastandroid.demo.drakeet

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentFluentAnimationBinding
import com.apache.fastandroid.demo.databinding.UserViewModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tesla.framework.common.util.DrakeetUtils.doOnMainThreadIdle
import com.tesla.framework.ui.fragment.BaseVBFragment
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration

/**
 * Created by Jerry on 2022/1/25.
 * https://t.zsxq.com/JEyZjQv
 */
class FluentAnimationDemoFragment: BaseVBFragment<FragmentFluentAnimationBinding>(FragmentFluentAnimationBinding::inflate) {

    private val userViewModel:UserViewModel by viewModels()
    private lateinit var mAdapter: BaseQuickAdapter<String,BaseViewHolder>

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val targetLayer = viewBinding.rootView
        val doOnIdle:Boolean = arguments?.getBoolean("doOnIdle",false) == true
        if (doOnIdle){
            targetLayer.isInvisible = true
            targetLayer.doOnMainThreadIdle({
                TransitionManager.beginDelayedTransition(targetLayer.parent as ViewGroup, Slide(Gravity.BOTTOM)).apply {
                    targetLayer.isInvisible = false
                }
            })
        }else{
            TransitionManager.beginDelayedTransition(targetLayer.parent as ViewGroup, Slide(Gravity.BOTTOM))
        }

        mAdapter = object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_comment) {
            override fun convert(helper: BaseViewHolder, item: String?) {
                helper.setText(R.id.tv_title, item)
            }
        }
        viewBinding.recyclerView.adapter = mAdapter
        viewBinding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
            addItemDecoration( HorizontalDividerItemDecoration.Builder(context)
                .color(Color.RED)
                .sizeResId(R.dimen.divider_height)
                .marginResId(R.dimen.divider_margin)
                .build()
            )
        }
        userViewModel.commentsLiveData.observe(this){
            mAdapter.setNewData(it)
        }
        userViewModel.loadCommentData()



    }
}
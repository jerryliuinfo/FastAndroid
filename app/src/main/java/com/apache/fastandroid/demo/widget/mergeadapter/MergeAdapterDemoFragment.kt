package com.apache.fastandroid.demo.widget.mergeadapter

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.demo.adapter.BodyAdapter
import com.apache.fastandroid.demo.adapter.TitleAdapter
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.databinding.FragmentRecycleview2Binding
import com.tesla.framework.ui.fragment.BaseVBFragment
import masteryi.me.mergeadapterdemo.FooterAdapter
import masteryi.me.mergeadapterdemo.HeaderAdapter
import masteryi.me.mergeadapterdemo.ItemAdapter


/**
 * Created by Jerry on 2022/6/26.
 */
class MergeAdapterDemoFragment:BaseVBFragment<FragmentRecycleview2Binding>(FragmentRecycleview2Binding::inflate) {

    private val mViewModel:ContactAdapterViewModel by viewModels()

    private val mHeaderAdapter:HeaderAdapter by lazy {
        HeaderAdapter(requireContext())
    }

    private val mFootAdapter:FooterAdapter by lazy {
        FooterAdapter(requireContext())
    }

    private val mItemAdapter:ItemAdapter by lazy {
        ItemAdapter(requireContext())
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)



        mViewModel.data.observe(this, Observer {
            mItemAdapter.addItem(it)
        })
        mViewModel.hasMoreState.observe(this, Observer {
            mFootAdapter.updateFooterState(it)
        })

        val myLayoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)

        val concatAdapter = ConcatAdapter().apply {
            addAdapter(mHeaderAdapter)
            addAdapter(mItemAdapter)
                addAdapter(mFootAdapter)
        }
        mBinding.recyclerView.apply {
            adapter = concatAdapter
            layoutManager = myLayoutManager
        }



        mBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val lastVisible = myLayoutManager.findLastVisibleItemPosition()
                    val itemCount = concatAdapter.itemCount
                    Logger.d( "lastVisible:$lastVisible, itemCount:$itemCount")

                    if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && myLayoutManager.findLastVisibleItemPosition() == concatAdapter.itemCount - 1
                    ) {
                        mViewModel.loadMore()
                    }
                }
            })


    }


    private fun generateTitles():List<String>{
        return Array(10){
            "title: $it"
        }.toMutableList()
    }

    private fun generateBodys():List<String>{
        return Array(10){
            "body: $it"
        }.toMutableList()
    }
}
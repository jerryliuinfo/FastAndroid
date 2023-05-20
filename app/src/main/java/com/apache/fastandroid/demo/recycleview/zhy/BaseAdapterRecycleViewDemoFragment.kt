package com.apache.fastandroid.demo.recycleview.zhy

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentComRecycleviewBinding
import com.apache.fastandroid.demo.recycleview.zhy.base.ViewHolder
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/5/18.
 */
class BaseAdapterRecycleViewDemoFragment:BaseBindingFragment<FragmentComRecycleviewBinding>(FragmentComRecycleviewBinding::inflate) {


    private val mDatas: ArrayList<String> = ArrayList()

    private lateinit var mAdapter: CommonAdapter<String>

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        initDatas()
        mAdapter = object :CommonAdapter<String>(requireContext(), R.layout.base_adapter_item_list,mDatas){
            override fun convert(holder: ViewHolder?, data: String?, position: Int) {
                holder!!.setText(
                    R.id.id_item_list_title, "$data:${holder.bindingAdapterPosition},${holder.layoutPosition}"
                )

            }

        }
        mBinding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),2)
            adapter = mAdapter
        }

    }

    private fun initDatas() {
        var i = 'A'.code
        while (i <= 'z'.code) {
            mDatas.add(i.toChar().toString() + "")
            i++
        }
    }
}
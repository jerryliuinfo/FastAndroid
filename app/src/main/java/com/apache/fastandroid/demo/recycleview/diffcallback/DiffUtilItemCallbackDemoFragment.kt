package com.apache.fastandroid.demo.recycleview.diffcallback

/**
 * Created by Jerry on 2023/7/1.
 */


import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

class DiffUtilItemCallbackDemoFragment : BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate) {


    val oldList = listOf(
        DiffItemBean(1, "Apple"),
        DiffItemBean(2, "Banana"),
        DiffItemBean(3, "Orange"),
        DiffItemBean(4, "Orange2")
    )

    val newList = listOf(
        DiffItemBean(1, "Apple"),
        DiffItemBean(2, "Banana"),
        DiffItemBean(4, "Orange"),
        DiffItemBean(5, "Grapes")
    )


    private lateinit var mAdapter:MyAdapter

     override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


         mAdapter = MyAdapter(oldList)

         mBinding.recyclerView.apply {
             adapter = MyAdapter(oldList).also {
                 mAdapter = it
             }
             mAdapter.notifyDataSetChanged()
         }

         mBinding.btnRefresh.setOnClickListener {

             mAdapter.datas = newList

             val diffCallback = ItemDiffCallback(oldList,newList)
             val diffResult = DiffUtil.calculateDiff(diffCallback)
             diffResult.dispatchUpdatesTo(mAdapter)
         }


     }
}
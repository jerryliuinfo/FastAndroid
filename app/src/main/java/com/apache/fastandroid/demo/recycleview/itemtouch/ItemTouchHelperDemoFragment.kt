package com.apache.fastandroid.demo.recycleview.itemtouch

/**
 * Created by Jerry on 2023/7/1.
 */


import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.ItemTouchHelper
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.apache.fastandroid.demo.recycleview.bean.DiffItemBean
import com.apache.fastandroid.demo.recycleview.diffcallback.DiffAdapter
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.util.*

class ItemTouchHelperDemoFragment :
    BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate),
    IMoveAndSwipeCallback {


    val oldList = listOf(
        DiffItemBean(1, "Apple"),
        DiffItemBean(2, "Banana"),
        DiffItemBean(3, "Orange"),
        DiffItemBean(4, "Orange2")
    )

    val newList = mutableListOf<DiffItemBean>(
        DiffItemBean(1, "Apple"),
        DiffItemBean(2, "Banana"),
        DiffItemBean(4, "Orange"),
        DiffItemBean(5, "Grapes")
    )


    private lateinit var mAdapter: DiffAdapter

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.recyclerView.apply {
            adapter = DiffAdapter().also {
                mAdapter = it
            }
            mAdapter.updateDatas(oldList)
        }

        mBinding.btnRefresh.setOnClickListener {
            mAdapter.updateDatas(newList)
        }

        val itemTouchHelperCallback = ItemTouchHelperCallback(this)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(mBinding.recyclerView)

    }

    override fun onMove(prePosition: Int, postPosition: Int) {
        Logger.d("onMove prePosition:$prePosition,postPosition:$postPosition")
        Collections.swap(newList, prePosition, postPosition);
        mAdapter.updateDatas(newList)
    }

    override fun onSwiped(position: Int) {
        Logger.d("onSwiped position:$position")
        newList.removeAt(position)
        mAdapter.updateDatas(newList)
    }
}
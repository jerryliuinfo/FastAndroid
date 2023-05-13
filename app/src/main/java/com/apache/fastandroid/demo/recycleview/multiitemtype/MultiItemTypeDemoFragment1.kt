package com.apache.fastandroid.demo.recycleview.multiitemtype

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentComRecycleviewBinding
import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.ItemBean
import com.apache.fastandroid.demo.recycleview.multiitemtype.item.ItemType00
import com.apache.fastandroid.demo.recycleview.multiitemtype.item.ItemType01
import com.apache.fastandroid.demo.recycleview.multiitemtype.item.ItemType02
import com.tencent.lib.multi.MultiAdapter
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.util.ArrayList

/**
 * Created by Jerry on 2023/5/10.
 * 单 bean 类型对应多样 item。
 */
class MultiItemTypeDemoFragment1:BaseBindingFragment<FragmentComRecycleviewBinding>(FragmentComRecycleviewBinding::inflate) {


    lateinit var adapter: MultiAdapter

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        //初始化ItemType
        val item00 = ItemType00()
        val item01 = ItemType01()
        val item02 = ItemType02()
        /*初始化Adapter*/
        adapter = MultiAdapter(initialCapacity = 3)
        adapter.clearAllItemTypes()
        /*将所有ItemType添加到Adapter中*/
        adapter.addItemType(item00)
            .addItemType(item01)
            .addItemType(item02)
        /*设置数据*/
        adapter.setDataList(getData())
        mBinding.recyclerView.adapter = adapter

    }

    /**
     * 模拟数据
     */
    private fun getData(): List<ItemBean> {
        val beans = ArrayList<ItemBean>()
        for (i in 0..5) {
            beans.add(ItemBean(ItemBean.TYPE_00, "我是A_00类Item$i"))
            beans.add(ItemBean(ItemBean.TYPE_01, "我是A_01类Item${i + 1}"))
            beans.add(ItemBean(ItemBean.TYPE_02, "我是A_02类Item${i + 2}"))
        }
        return beans
    }
}
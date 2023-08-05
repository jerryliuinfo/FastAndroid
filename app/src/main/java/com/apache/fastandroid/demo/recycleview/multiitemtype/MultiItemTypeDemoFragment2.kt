//package com.apache.fastandroid.demo.recycleview.multiitemtype
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.DiffUtil
//import com.apache.fastandroid.databinding.FragmentComRecycleviewBinding
//import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.BeanA
//import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.BeanB
//import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.BeanC
//import com.apache.fastandroid.demo.recycleview.multiitemtype.bean.ItemBean
//import com.apache.fastandroid.demo.recycleview.multiitemtype.item.*
//import com.tencent.lib.multi.MultiAdapter
//import com.tencent.lib.multi.paging.MultiPagingDataAdapter
//import com.tesla.framework.ui.fragment.BaseBindingFragment
//import kotlinx.coroutines.launch
//import java.util.ArrayList
//
///**
// * Created by Jerry on 2023/5/10.
// * 单 bean 类型对应多样 item。
// */
//class MultiItemTypeDemoFragment2:BaseBindingFragment<FragmentComRecycleviewBinding>(FragmentComRecycleviewBinding::inflate) {
//
//
//    private lateinit var adapter: MultiPagingDataAdapter
//
//    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
//        super.layoutInit(inflater, savedInstanceState)
//
//
//        //初始化ItemType
//        val aItemType = AItemType()
//        val bItemType = BItemType()
//        val cItemType = CItemType()
//        /*初始化Adapter*/
//        adapter = MultiPagingDataAdapter(diffCallback = object :
//            DiffUtil.ItemCallback<Any>() {
//            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean = false
//            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean = false
//
//        }, 3)
//        adapter.clearAllItemTypes2()
//        /*将所有ItemType添加到Adapter中*/
//        adapter.addItemType(aItemType)
//            .addItemType(bItemType)
//            .addItemType(cItemType)
//        mBinding.recyclerView.adapter = adapter
//        /*设置数据*/
//        lifecycleScope.launch {
//            adapter.submitData(androidx.paging.PagingData.Companion.from(getData()))
//        }
//
//    }
//
//    /**
//     * 模拟数据
//     */
//    private fun getData(): List<Any> {
//        val beans = ArrayList<Any>()
//        for (i in 0..5) {
//            beans.add(BeanA( "我是A类Item$i"))
//            beans.add(BeanB("我是B类Item${i + 1}"))
//            beans.add(BeanC( "我是C类Item${i + 2}"))
//        }
//        return beans
//    }
//}
package com.apache.fastandroid.demo.drakeet.multitype

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.VisibleForTesting
import com.apache.fastandroid.demo.drakeet.multitype.onetomany.Data
import com.apache.fastandroid.demo.drakeet.multitype.onetomany.UserType1ViewHolder
import com.apache.fastandroid.demo.drakeet.multitype.onetomany.UserType2ViewHolder
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.drakeet.multitype.MultiTypeAdapter
import com.tesla.framework.databinding.FragmentRecycleview2Binding
import com.tesla.framework.ui.fragment.BaseVBFragment
import java.util.ArrayList


/**
 * Description:TODO
 * Create Time:2021/12/19 10:36 下午
 * Author:jerry
 *
 */

class MultiTypeDemoFragment : BaseVBFragment<FragmentRecycleview2Binding>(FragmentRecycleview2Binding::inflate) {

    private lateinit var mAdapter:MultiTypeAdapter

    private val dataFromService: List<Data>
        @VisibleForTesting
        get() {
            val list = ArrayList<Data>()
            var i = 0
            while (i < 30) {
                list.add(Data("title: $i", Data.TYPE_1))
                list.add(Data("title: ${ i + 1 }", Data.TYPE_2))
                i += 2
            }
            return list
        }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mAdapter = MultiTypeAdapter().apply {
            register(Data::class).to(
                UserType1ViewHolder(),
                UserType2ViewHolder()
            ).withKotlinClassLinker { _, item ->
                when(item.type){
                    Data.TYPE_2 -> UserType2ViewHolder::class
                    else -> UserType1ViewHolder::class
                }
            }
        }
        mBinding.recyclerView.apply {
            adapter = mAdapter
        }
        mAdapter.apply {
            items = dataFromService
            notifyDataSetChanged()
        }

    }


}
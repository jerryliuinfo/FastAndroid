package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import com.apache.fastandroid.databinding.TempScrollConflictInnerInterceptBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.temp_scroll_conflict.listView

/**
 * Created by Jerry on 2021/8/6.
 */
class ScrollConflictInnernterceptDemoFragment: BaseVBFragment<TempScrollConflictInnerInterceptBinding>(TempScrollConflictInnerInterceptBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        val adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1, data)
        listView.adapter = adapter
    }

    private val data = listOf("Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango","Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango","Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango","Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango","Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango","Apple")




}
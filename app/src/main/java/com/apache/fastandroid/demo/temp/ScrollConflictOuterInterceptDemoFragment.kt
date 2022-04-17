package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import com.apache.fastandroid.databinding.TempScrollConflictOuterInterceptBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.temp_scroll_conflict.listView
import kotlinx.android.synthetic.main.temp_scroll_conflict_outer_intercept.*

/**
 * Created by Jerry on 2021/8/6.
 */
class ScrollConflictOuterInterceptDemoFragment: BaseVBFragment<TempScrollConflictOuterInterceptBinding>(TempScrollConflictOuterInterceptBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        scrollView.setListView(listView)

        val adapter = ArrayAdapter<String>(context!!,android.R.layout.simple_list_item_1, data)
        listView.adapter = adapter
    }

    private val data = listOf("Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango","Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango","Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango","Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango","Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango","Apple")




}
package com.apache.fastandroid.demo.drakeet

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EdgeEffect
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.drakeet.purewriter.widget.StretchEdgeEffect
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.fragment_com_recycleview.*


/**
 * Description:TODO
 * Create Time:2021/12/20 10:20 下午
 * Author:jerry
 *
 */

class DrakeetEdgeEffectDemoFragment : BaseStatusFragmentNew() {

   private lateinit var mAdapter: BaseQuickAdapter<String,BaseViewHolder>
	 override fun getLayoutId(): Int {
			return R.layout.fragment_com_recycleview
	 }

	 override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
			super.layoutInit(inflater, savedInstanceState)

			mAdapter =
				 object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_com_text) {
						override fun convert(helper: BaseViewHolder, item: String) {
							 helper.setText(R.id.tv_name, item)
						}

				 }
      recyclerView.adapter = mAdapter
      val list = mutableListOf<String>()
      for (i in 0 until 100){
         list.add("item: $i")
      }


/*

      recyclerView.edgeEffectFactory = object :RecyclerView.EdgeEffectFactory(){
				 override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
						return object :StretchEdgeEffect(view.context,view){
							 override fun another(): StretchEdgeEffect {

							 }

							 override fun pivotY(): Float {

							 }

						};
				 }
      }
*/

      mAdapter.setNewData(list)

	 }


}
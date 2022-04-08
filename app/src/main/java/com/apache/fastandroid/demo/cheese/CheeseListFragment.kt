package com.apache.fastandroid.demo.cheese

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentCheeseListBinding
import com.apache.fastandroid.demo.cheese.Cheeses.randomCheeseDrawable
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tesla.framework.kt.launchActivity
import com.tesla.framework.ui.fragment.BaseVBFragment
import java.util.ArrayList

/**
 * Created by Jerry on 2022/4/5.
 */
class CheeseListFragment:BaseVBFragment<FragmentCheeseListBinding>(FragmentCheeseListBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val itemAdapter = Adapter()
        mBinding.recyclerview.apply {
            adapter = itemAdapter

        }
        itemAdapter.setNewData(Cheeses.STRINGS.randomSublist(30))
        itemAdapter.setOnItemClickListener { adapter, view, position ->
            val item:String = adapter.getItem(position) as String
            launchActivity<CheeseDetailActivity>(requireActivity()){
                putExtra(CheeseDetailActivity.EXTRA_NAME, item)
            }
        }
    }

    companion object{
        fun newInstance():Fragment{
            return CheeseListFragment()
        }
    }

    internal class Adapter:BaseQuickAdapter<String,BaseViewHolder>(R.layout.list_item){
        override fun convert(helper: BaseViewHolder, item: String?) {
            helper.setText(R.id.text,item)
            val imageView = helper.getView<ImageView>(R.id.avatar)
            Glide.with(imageView.context)
                .load(randomCheeseDrawable)
                .into(imageView)
        }



    }
}

private fun <T> Array<T>.randomSublist(amount: Int): List<T> {
    val list = ArrayList<T>(amount)
    while (list.size < amount) {
        list.add(random())
    }
    return list
}
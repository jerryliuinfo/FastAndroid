package com.apache.fastandroid.jetpack.navigation.advance.list

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentLeaderboardBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/12.
 */
class LeaderboardFragment:BaseVBFragment<FragmentLeaderboardBinding>(FragmentLeaderboardBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.leaderboardList.apply {
            val datas = Array(10){ "Person: $it"}
            setHasFixedSize(true)
            adapter = LeaderAdapter(R.layout.list_view_item, datas.toList())
        }

    }

   class LeaderAdapter(layoutId:Int, datas:List<String>):BaseQuickAdapter<String, BaseViewHolder>(layoutId, datas.toMutableList()){
        override fun convert(helper: BaseViewHolder, item: String) {
            helper.setText(R.id.user_name_text,item)
            helper.setImageResource(R.id.user_avatar_image, listOfAvatars[helper.adapterPosition % listOfAvatars.size])
            helper.itemView.setOnClickListener {
                val args = bundleOf("username" to item)
                helper.itemView.findNavController().navigate(R.id.leaderboard_to_profile,args)
            }
        }

    }

    companion object{
        private val listOfAvatars = listOf(
            R.drawable.avatar_1_raster,
            R.drawable.avatar_2_raster,
            R.drawable.avatar_3_raster,
            R.drawable.avatar_4_raster,
            R.drawable.avatar_5_raster,
            R.drawable.avatar_6_raster
        )
    }


}


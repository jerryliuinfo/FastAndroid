package com.apache.fastandroid.demo.sunflower.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apache.fastandroid.databinding.ListItemPlantBinding
import com.apache.fastandroid.demo.sunflower.bean.Plant
import com.apache.fastandroid.demo.sunflower.fragement.PlantDetailFragment
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.ui.activity.FragmentArgs
import com.tesla.framework.ui.activity.FragmentContainerActivity

/**
 * Created by Jerry on 2022/4/3.
 * 使用 ListAdapter 有动画效果
 */
class PlantAdapterNew(val activity: Activity):ListAdapter<Plant,PlantAdapterNew.PlantViewHolder>(PlantDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantAdapterNew.PlantViewHolder {
        return PlantViewHolder(activity,
            ListItemPlantBinding.inflate(LayoutInflater.from(parent.context),parent,false
        ))
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val plant = getItem(position)
        holder.bind(plant,holder)
    }



    class PlantViewHolder(activity: Activity,private val binding:ListItemPlantBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.setClickListener {
                binding.plant?.let {
                    val args = FragmentArgs().apply {
                        add("plantId", it.plantId)
                    }
                    FragmentContainerActivity.launch(activity,PlantDetailFragment::class.java,args,false)
                }
            }
        }

        fun bind(item:Plant,holder: PlantViewHolder){
            binding.apply {
                plant = item
                executePendingBindings()
            }

        }
    }
}

private class PlantDiffCallback:DiffUtil.ItemCallback<Plant>(){
    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.plantId == newItem.plantId
    }

    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
    }

}
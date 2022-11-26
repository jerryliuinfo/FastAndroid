package com.apache.fastandroid.demo.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentBottomTabsBinding
import com.apache.fastandroid.databinding.MainTabItemBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.tesla.framework.ui.fragment.BaseDBFragment

/**
 * Created by Jerry on 2022/4/30.
 */
class BottomTabsFragment:BaseDBFragment<FragmentBottomTabsBinding>(FragmentBottomTabsBinding::inflate) {

    private val tabTexts = arrayOf("首页", "导航", "问答", "项目", "我的")
    private val tabDrawable = intArrayOf(
        R.drawable.ic_bottom_bar_home,
        R.drawable.ic_bottom_bar_navigation,
        R.drawable.ic_bottom_bar_faq,
        R.drawable.ic_bottom_bar_system,
        R.drawable.ic_bottom_bar_project
    )
    private val fragments = arrayListOf(
        BottomFragment1.newInstance(),
        BottomFragment1.newInstance(),
        BottomFragment1.newInstance(),
        BottomFragment1.newInstance(),
        BottomFragment1.newInstance()
    )

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        initTableLayout()
    }

    private fun initTableLayout() {
        mBinding.viewpager.apply {
            adapter = object : FragmentStateAdapter(
                requireActivity().supportFragmentManager,
                viewLifecycleOwner.lifecycle
            ) {
                override fun getItemCount(): Int {
                    return fragments.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragments[position]
                }
            }
            isUserInputEnabled = false
            offscreenPageLimit = fragments.size
        }

        mBinding.tabLayout.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    setColorFilter(tab.customView, R.color.three_nine_gray)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                    setColorFilter(tab.customView, R.color.text_theme)
                }

                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
        TabLayoutMediator(mBinding.tabLayout, mBinding.viewpager, false, false) { tab, position ->
            val item = MainTabItemBinding.inflate(LayoutInflater.from(mBinding.root.context))
            item.icon.setImageResource(tabDrawable[position])
            item.icon.setColorFilter(ContextCompat.getColor(item.icon.context, R.color.text_theme))
            item.name.text = tabTexts[position]
            item.name.setTextColor(ContextCompat.getColor(item.name.context, R.color.text_theme))
            tab.customView = item.root
        }.attach()
    }

    private fun setColorFilter(view: View?, iconColor: Int, nameColor: Int = R.color.text_theme) {
        view?.apply {
            val icon = findViewById<ImageView>(R.id.icon)
            val name = findViewById<TextView>(R.id.name)
            icon.setColorFilter(ContextCompat.getColor(icon.context, iconColor))
            name.setTextColor(ContextCompat.getColor(name.context, nameColor))
        }
    }


}
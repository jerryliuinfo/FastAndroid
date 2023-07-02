package com.apache.fastandroid.demo.recycleview.viewtype

/**
 * Created by Jerry on 2023/7/1.
 */


import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.doOnNextLayout
import com.apache.fastandroid.databinding.FragmentRecycleviewBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.showToast
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * 不同的 bean 展示不同的样式
 * @property mAdapter NavigationAdapter
 */
class MultiViewTypeDemoFragment : BaseBindingFragment<FragmentRecycleviewBinding>(FragmentRecycleviewBinding::inflate),
    NavigationAdapter.NavigationAdapterListener {

    private lateinit var mAdapter: NavigationAdapter

    private val navigationListeners: MutableList<NavigationAdapter.NavigationAdapterListener> =
        mutableListOf()

     override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

         mBinding.recyclerView.apply {
             adapter = NavigationAdapter(this@MultiViewTypeDemoFragment).also {
                 mAdapter = it


             }
         }
         NavigationModel.navigationList.observe(viewLifecycleOwner) {

             mAdapter.submitList(it)
         }
         NavigationModel.setNavigationMenuItemChecked(0)
     }

    fun addNavigationListener(listener: NavigationAdapter.NavigationAdapterListener) {
        navigationListeners.add(listener)
    }

    override fun onNavMenuItemClicked(item: NavigationModelItem.NavMenuItem) {
        Logger.d("onNavMenuItemClicked item:${item.titleRes}")
        showToast("onNavMenuItemClicked item:${item.titleRes}")

        NavigationModel.setNavigationMenuItemChecked(item.id)
        navigationListeners.forEach { it.onNavMenuItemClicked(item) }
    }

    override fun onNavEmailFolderClicked(folder: NavigationModelItem.NavEmailFolder) {
        Logger.d("onNavEmailFolderClicked folder:${folder.emailFolder}")
        showToast("onNavEmailFolderClicked folder:${folder.emailFolder}")

        navigationListeners.forEach { it.onNavEmailFolderClicked(folder) }


    }
}
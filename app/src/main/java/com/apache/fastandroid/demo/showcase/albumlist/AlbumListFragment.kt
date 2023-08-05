package com.apache.fastandroid.demo.showcase.albumlist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentComRecycleviewBinding
import com.apache.fastandroid.databinding.ItemAlbumBinding
import com.apache.fastandroid.demo.showcase.AlbumListViewModelFactory
import com.apache.fastandroid.demo.showcase.data.datasource.api.service.AlbumRetrofitService
import com.apache.fastandroid.demo.showcase.data.datasource.database.AlbumDatabase
import com.apache.fastandroid.demo.showcase.data.repository.AlbumRepositoryImpl
import com.apache.fastandroid.demo.showcase.domain.model.Album
import com.apache.fastandroid.demo.showcase.domain.usecase.GetAlbumListUseCase
import com.apache.fastandroid.network.retrofit.RetrofitFactory
import com.blankj.utilcode.util.Utils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.tesla.framework.component.imageloader.showImage
import com.tesla.framework.kt.hide
import com.tesla.framework.kt.show
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Jerry on 2023/5/14.
 * https://github.com/igorwojda/android-showcase
 */
class AlbumListFragment :BaseBindingFragment<FragmentComRecycleviewBinding>(FragmentComRecycleviewBinding::inflate){

//    private val mViewModel:AlbumListViewModel by viewModels {getViewModelFactory()  }
    private val mViewModel: AlbumListViewModel by viewModels {
//        getViewModelFactory()

    val apiService: AlbumRetrofitService =  RetrofitFactory.get().createAlbumService(
        AlbumRetrofitService::class.java,
        "http://ws.audioscrobbler.com/2.0/"
    )
    AlbumListViewModelFactory(GetAlbumListUseCase(AlbumRepositoryImpl(apiService,AlbumDatabase.getInstance(
        Utils.getApp()).albums())), SavedStateHandle())

}

    private  val mAdapter = object :BaseQuickAdapter<Album,BaseDataBindingHolder<ItemAlbumBinding>>(
        R.layout.item_album){
        override fun convert(holder: BaseDataBindingHolder<ItemAlbumBinding>, item: Album) {

            holder.dataBinding?.icAlbum?.run {
                showImage(item.getDefaultImageUrl())
            }

            holder.setText(R.id.tv_title,item.name)
        }

    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mViewModel.loadData()
        mBinding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(),3)
            adapter = mAdapter
        }



        lifecycleScope.launchWhenCreated {
            mViewModel.uiStateFlow.collectLatest { uiState ->
                when(uiState){
                    AlbumListViewModel.UiState.Loading -> {
                        mBinding.progressBar.show()
                    }
                    is AlbumListViewModel.UiState.Content -> {
                        mBinding.progressBar.hide()
                        mAdapter.setNewInstance(uiState.albums.toMutableList())
                    }
                }
            }
        }


    }
}
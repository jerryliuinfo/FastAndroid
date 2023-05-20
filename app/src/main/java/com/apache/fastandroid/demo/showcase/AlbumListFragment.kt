package com.apache.fastandroid.demo.showcase

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentComRecycleviewBinding
import com.apache.fastandroid.databinding.ItemAlbumBinding
import com.apache.fastandroid.demo.showcase.bean.Album
import com.apache.fastandroid.demo.showcase.repository.AlbumRepositoryImpl
import com.apache.fastandroid.demo.showcase.service.AlbumRetrofitService
import com.apache.fastandroid.demo.showcase.usecase.GetAlbumListUseCase
import com.apache.fastandroid.demo.sunflower.repository.PlantRepository
import com.apache.fastandroid.demo.sunflower.viewmodel.PlantListViewModelFactory
import com.apache.fastandroid.network.retrofit.RetrofitFactory
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.example.android.architecture.blueprints.todoapp.util.getViewModelFactory
import com.tesla.framework.component.imageloader.display
import com.tesla.framework.kt.hide
import com.tesla.framework.kt.show
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Jerry on 2023/5/14.
 */
class AlbumListFragment :BaseBindingFragment<FragmentComRecycleviewBinding>(FragmentComRecycleviewBinding::inflate){

//    private val mViewModel:AlbumListViewModel by viewModels {getViewModelFactory()  }
    private val mViewModel:AlbumListViewModel by viewModels {
//        getViewModelFactory()

    val apiService: AlbumRetrofitService =  RetrofitFactory.get().createAlbumService(
        AlbumRetrofitService::class.java,
        "http://ws.audioscrobbler.com/2.0/"
    )
    AlbumListViewModelFactory(GetAlbumListUseCase(AlbumRepositoryImpl(apiService)), SavedStateHandle())

}

    private  val mAdapter = object :BaseQuickAdapter<Album,BaseDataBindingHolder<ItemAlbumBinding>>(
        R.layout.item_album){
        override fun convert(holder: BaseDataBindingHolder<ItemAlbumBinding>, item: Album) {

            holder.dataBinding?.icAlbum?.run {
                display(item.getDefaultImageUrl())
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
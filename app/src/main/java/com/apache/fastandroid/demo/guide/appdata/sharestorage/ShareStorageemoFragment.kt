package com.apache.fastandroid.demo.guide.appdata.sharestorage

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.apache.fastandroid.databinding.FragmentAppDataShareStorageBinding
import com.blankj.utilcode.util.Utils
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/1/25.
 * https://developer.android.com/training/data-storage/shared?hl=zh-cn
 */
class ShareStorageemoFragment :
    BaseBindingFragment<FragmentAppDataShareStorageBinding>(FragmentAppDataShareStorageBinding::inflate) {

    private val mViewModel:ShareStorageViewModel by viewModels()

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnQueryMediaList.setOnClickListener {
            queryMediaList()
        }

        mBinding.btnLoadThumbnail.setOnClickListener {
            loadThumbnail()
        }

        mBinding.btnStorageVolumn.setOnClickListener {
            storageVolumn()
        }


    }

    private fun shareSimpleData() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    @SuppressLint("NewApi")
    private fun storageVolumn() {
        //卷用于提供设备上所有共享存储卷的视图。您可以读取此合成卷的内容，但无法修改这些内容。
        val volumeExternal = MediaStore.VOLUME_EXTERNAL

        //代表设备上的主要共享存储卷。您可以读取和修改此卷的内容
        val volumeExternalPrimary = MediaStore.VOLUME_EXTERNAL_PRIMARY


        val volumeNames: Set<String> = MediaStore.getExternalVolumeNames(Utils.getApp().applicationContext)
        val firstVolumeName = volumeNames.iterator().next()
        Logger.d("volumeExternal:$volumeExternal, volumeExternalPrimary:$volumeExternalPrimary, firstVolumeName:$firstVolumeName")
    }

    private fun loadThumbnail() {
        val permissionResultLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            // val thumbnail = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //     getAlbumArtAfterQ()
            // } else {
            //     getAlbumArtBeforeQ()
            // }
            mViewModel.loadThumbnail()
        }
        permissionResultLauncher.launch(READ_EXTERNAL_STORAGE)

    }

    /**
     * 查询媒体集合
     */
    private fun queryMediaList() {
        mViewModel.queryMediaList()
    }


}
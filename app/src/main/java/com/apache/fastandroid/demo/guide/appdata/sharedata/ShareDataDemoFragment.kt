package com.apache.fastandroid.demo.guide.appdata.sharedata

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentAppDataShareDataBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * https://developer.android.com/training/sharing?hl=zh-cn
 */
class ShareDataDemoFragment :
    BaseBindingFragment<FragmentAppDataShareDataBinding>(FragmentAppDataShareDataBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnShareTextData.setOnClickListener {
            shareTextData()
        }

        mBinding.btnShareBinaryData.setOnClickListener {
            shareBinaryData()
        }

        mBinding.btnShareMultiple.setOnClickListener {
            shareMultipleData()
        }

        mBinding.btnAddRichMedia.setOnClickListener {
            addRichMediaData()
        }

        mBinding.btnAddCustomOperationToSharesheet.setOnClickListener {
            addCustomOperationToShareSheet()
        }

        mBinding.btnShareFile.setOnClickListener {
            shareFile()
        }
    }


    /**
     * https://developer.android.com/training/secure-file-sharing/setup-sharing?hl=zh-cn
     */
    private fun shareFile() {

    }

    private fun addCustomOperationToShareSheet() {

    }

    private fun addRichMediaData() {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://developer.android.com/training/sharing/")

            // (Optional) Here you're setting the title of the content
            putExtra(Intent.EXTRA_TITLE, "Introducing content previews")

            // (Optional) Here you're passing a content URI to an image to be displayed
            // data = contentUri
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }, null)
        startActivity(share)
    }

    private fun shareMultipleData() {
        val imageUris: ArrayList<Uri> = arrayListOf(
            // Add your image URIs here
            Uri.parse("imageUri1"),
            Uri.parse("imageUri2"),
        )

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)
            type = "image/*"
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }

    private fun shareBinaryData() {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            // Example: content://com.google.android.apps.photos.contentprovider/...
            putExtra(Intent.EXTRA_STREAM, "/sdcard/test.png")
            type = "image/jpeg"
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }


    /**
     * 将文字内容从一个应用发送到另一个应用
     */
    private fun shareTextData() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }



}
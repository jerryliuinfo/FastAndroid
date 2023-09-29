package com.apache.fastandroid.component.compress

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.apache.fastandroid.databinding.ActivityCompressDemoBinding
import com.tesla.framework.component.compress.Compressor
import com.tesla.framework.component.compress.constraint.format
import com.tesla.framework.component.compress.constraint.quality
import com.tesla.framework.component.compress.constraint.resolution
import com.tesla.framework.component.compress.constraint.size
import com.tesla.framework.component.compress.loadBitmap
import com.tesla.framework.kt.showToast
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.activity_compress_demo.*

import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.text.DecimalFormat
import java.util.*
import kotlin.math.log10
import kotlin.math.pow

/**
 * Created on : January 25, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
class CompressDemoFragment : BaseBindingFragment<ActivityCompressDemoBinding>(ActivityCompressDemoBinding::inflate) {
    companion object {
        private const val PICK_IMAGE_REQUEST = 1

        fun getNavigationIntent(
            context: Context,

        ) = Intent(context, CompressDemoFragment::class.java)
    }

    private var actualImage: File? = null
    private var compressedImage: File? = null


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        actualImageView.setBackgroundColor(getRandomColor())
        clearImage()
        setupClickListener()
    }



    private fun setupClickListener() {
        mBinding.chooseImageButton.setOnClickListener { chooseImage() }
        mBinding.compressImageButton.setOnClickListener { compressImage() }
        mBinding.customCompressImageButton.setOnClickListener { customCompressImage() }
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    private fun compressImage() {
        actualImage?.let { imageFile ->
            lifecycleScope.launch {
                // Default compression
                compressedImage = Compressor.compress(requireContext(), imageFile)
                setCompressedImage()
            }
        } ?: showError("Please choose an image!")
    }

    private fun customCompressImage() {
        actualImage?.let { imageFile ->
            lifecycleScope.launch {
                // Default compression with custom destination file
                /*compressedImage = Compressor.compress(this@MainActivity, imageFile) {
                    default()
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.also {
                        val file = File("${it.absolutePath}${File.separator}my_image.${imageFile.extension}")
                        destination(file)
                    }
                }*/

                // Full custom
                compressedImage = Compressor.compress(requireContext(), imageFile) {
                    resolution(1280, 720)
                    quality(80)
                    format(Bitmap.CompressFormat.WEBP)
                    size(2_097_152) // 2 MB
                }
                setCompressedImage()
            }
        } ?: showError("Please choose an image!")
    }

    private fun setCompressedImage() {
        compressedImage?.let {
            mBinding.compressedImageView.setImageBitmap(BitmapFactory.decodeFile(it.absolutePath))
            mBinding.compressedSizeTextView.text = String.format("Size : %s", getReadableFileSize(it.length()))
            showToast("Compressed image save in " + it.path)
            Log.d("Compressor", "Compressed image save in " + it.path)
        }
    }

    private fun clearImage() {
        actualImageView.setBackgroundColor(getRandomColor())
        mBinding.compressedImageView.setImageDrawable(null)
        mBinding.compressedImageView.setBackgroundColor(getRandomColor())
        mBinding.compressedSizeTextView.text = "Size : -"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                showError("Failed to open picture!")
                return
            }
            try {
                actualImage = FileUtil.from(
                    requireContext(),
                    data.data
                )?.also {
                    actualImageView.setImageBitmap(loadBitmap(it))
                    actualSizeTextView.text = String.format("Size : %s", getReadableFileSize(it.length()))
                    clearImage()
                }
            } catch (e: IOException) {
                showError("Failed to read picture data!")
                e.printStackTrace()
            }
        }
    }

    private fun showError(errorMessage: String) {
        showToast(errorMessage)
    }

    private fun getRandomColor() = Random().run {
        Color.argb(100, nextInt(256), nextInt(256), nextInt(256))
    }

    private fun getReadableFileSize(size: Long): String {
        if (size <= 0) {
            return "0"
        }
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
    }
}

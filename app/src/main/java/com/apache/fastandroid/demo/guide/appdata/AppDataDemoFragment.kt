package com.apache.fastandroid.demo.guide.appdata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentAppDataBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.print
import com.tesla.framework.kt.print2
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.io.File
import kotlin.random.Random

/**
 * Created by Jerry on 2023/1/25.
 * https://developer.android.com/training/data-storage?hl=zh-cn
 */
class AppDataDemoFragment:BaseBindingFragment<FragmentAppDataBinding>(FragmentAppDataBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnAppExclusiveInnerSpace.setOnClickListener {
            filesAndCacheDir()
        }

        mBinding.btnAppExclusiveOuterSpace.setOnClickListener {
            externalFilesAndCacheDir()
        }
        mBinding.btnOpenFileOutput.setOnClickListener {
            openFileOutput()
        }
        mBinding.btnOpenFileInput.setOnClickListener {
            openFileInput()
        }
        mBinding.btnInstallLocation.setOnClickListener {
            installLocation()
        }
        mBinding.btnFilelist.setOnClickListener {
            fileList()
        }

        mBinding.btnCreateTempFile.setOnClickListener {
            createTempFileInCacheDir()
        }
        mBinding.btnDeleteCacheFile.setOnClickListener {
            deleteCacheFile()
        }
        mBinding.btnDeleteFile.setOnClickListener {
            deleteFileByContext()
        }
        mBinding.btnVerifyAvailable.setOnClickListener {
            sdCardAvailable()
        }
        mBinding.btnCreateCacheFileInExternal.setOnClickListener {
            createCacheFileInExternal()
        }
        mBinding.btnCreateCacheFileInExternal.setOnClickListener {
            createCacheFileInExternal()
        }
        mBinding.btnMediaStorage.setOnClickListener {
            mediaStorage()
        }
        mBinding.btnSendDataToOtherApp.setOnClickListener {
            sendDataToOtherApp()
        }
        mBinding.btnTextPreview.setOnClickListener {
            textPreview()
        }
        mBinding.btnReceiveData.setOnClickListener {
            receiveData()
        }
    }

    private fun receiveData() {
        val intent = Intent(activity,ReceiveDataDemoActivity::class.java)
        startActivity(intent)
    }

    private fun textPreview() {
        val share = Intent.createChooser(Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://developer.android.com/training/sharing/")

            // (Optional) Here we're setting the title of the content
            putExtra(Intent.EXTRA_TITLE, "Introducing content previews")

            // (Optional) Here we're passing a content URI to an image to be displayed
//            data = contentUri
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }, null)
        startActivity(share)
    }

    private fun sendDataToOtherApp() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "This is my text to send2.")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun mediaStorage() {
        val file = getAppSpecificAlbumStorageDir(mContext,"chou")
        println("file name:${file?.name ?: "unknow"}")
    }


    private fun getAppSpecificAlbumStorageDir(context: Context, albumName: String): File? {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        val file:File? = File(context.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES), albumName)
        if (file?.mkdirs() == false) {
            println("Directory not created")
        }
        return file
    }

    private fun createCacheFileInExternal() {
        val child = "test:${Random.nextInt(5,15)}"
        val externalCacheFile = File(mContext.externalCacheDir, child)
        externalCacheFile.writeText(child)
    }

    private fun sdCardAvailable() {
        // Checks if a volume containing external storage is available
// for read and write.
        fun isExternalStorageWritable(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        // Checks if a volume containing external storage is available to at least read.
        fun isExternalStorageReadable(): Boolean {
            return Environment.getExternalStorageState() in
                    setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
        }
        println("isExternalStorageWritable:${isExternalStorageWritable()}, isExternalStorageReadable:${isExternalStorageReadable()}")
    }


    private fun deleteCacheFile() {
        val fileName = "test"
        //注意：当设备的内部存储空间不足时，Android 可能会删除这些缓存文件以回收空间。因此，请在读取前检查缓存文件是否存在。
        val cacheFile = File(mContext.cacheDir,fileName)
        Logger.d("deleteCacheFile cacheFile before delete exist:${cacheFile.exists()}")
        if (cacheFile.exists()){
            cacheFile.delete()
        }
        Logger.d("deleteCacheFile cacheFile after delete exist:${cacheFile.exists()}")

    }
    private fun deleteFileByContext() {
        val fileName = "test"
        //注意：当设备的内部存储空间不足时，Android 可能会删除这些缓存文件以回收空间。因此，请在读取前检查缓存文件是否存在。
        val cacheFile = File(mContext.cacheDir,fileName)
        Logger.d("deleteFileByContext cacheFile before delete exist:${cacheFile.exists()}")
        if (cacheFile.exists()){
            mContext.deleteFile(fileName)
        }
        Logger.d("deleteFileByContext cacheFile after delete exist:${cacheFile.exists()}")


    }

    private fun createTempFileInCacheDir() {
        val fileName = "test"
        val tempFile = File.createTempFile(fileName,null,mContext.cacheDir)

        //注意：当设备的内部存储空间不足时，Android 可能会删除这些缓存文件以回收空间。因此，请在读取前检查缓存文件是否存在。
        val cacheFile = File(mContext.cacheDir,fileName)
        Logger.d("createTempFileInCacheDir cacheFile exist:${cacheFile.exists()}")

    }

    private fun fileList() {
        val fileList = mContext.fileList()
        Logger.d("fileList :${fileList.toList().print2 { it }}")


    }

    private fun openFileInput() {
        val fileName = "myFile"
        val content = mContext.openFileInput(fileName).bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }
        Logger.d("openFileInput content:$content")

    }


    /**
     * 注意：在搭载 Android 7.0（API 级别 24）或更高版本的设备上，除非您将 Context.MODE_PRIVATE 文件模式传递到 openFileOutput()，否则会发生 SecurityException。
     */
    private fun openFileOutput() {
        val fileName = "myFile"
        val fileContent = "Hello world:${Random.nextInt(5,20)}"
        mContext.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(fileContent.toByteArray())
        }
    }

    /**
     * 默认情况下，应用本身存储在内部存储空间中。不过，如果您的 APK 非常大，也可以在应用的清单文件中指明偏好设置，以便将应用安装到外部存储空间：
        <manifest ...
        android:installLocation="preferExternal">
        ...
        </manifest>
     */
    private fun installLocation() {

    }


    /**
     * 从内部存储空间访问不需要任何权限,注意:
     * 这些目录的空间通常比较小。在将应用专属文件写入内部存储空间之前，应用应查询设备上的可用空间。
     */
    private fun filesAndCacheDir() {
        //data/user/0/com.apache.fastandroid.debug/files
        //data/data/com.apache.fastandroid.debug/files
        val fileDir = mContext.filesDir
        ///data/user/0/com.apache.fastandroid.debug/cache
        val cacheDir = mContext.cacheDir
        Logger.d("fileDir:$fileDir, cacheDir:$cacheDir")

        val file = File(mContext.filesDir, "userInfo")
        file.writeText("hello")
        val content = file.readText()
        Logger.d("content:$content")



    }

    /**
     * 如果应用在搭载 Android 4.4（API 级别 19）或更高版本的设备上运行，从外部存储空间访问不需要任何权限
     */
    private fun externalFilesAndCacheDir() {
        //storage/emulated/0/Android/data/com.apache.fastandroid.debug/files,
        val fileDir = mContext.getExternalFilesDir(null)
        //storage/emulated/0/Android/data/com.apache.fastandroid.debug/cache
        val cacheDir = mContext.externalCacheDir
        Logger.d("external fileDir:$fileDir, cacheDir:$cacheDir")

        val appSpecificExternalDir = File(mContext.getExternalFilesDir(null), "test")

    }
}
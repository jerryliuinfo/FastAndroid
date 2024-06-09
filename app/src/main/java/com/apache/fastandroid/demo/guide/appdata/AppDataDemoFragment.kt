package com.apache.fastandroid.demo.guide.appdata

import android.app.usage.StorageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.storage.StorageManager
import android.os.storage.StorageManager.ACTION_MANAGE_STORAGE
import android.view.LayoutInflater
import androidx.core.content.getSystemService
import com.apache.fastandroid.databinding.FragmentAppDataBinding
import com.apache.fastandroid.demo.guide.appdata.sharestorage.ShareStorageemoFragment
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.kt.launchFragment
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.io.File
import java.util.UUID
import kotlin.random.Random

/**
 * Created by Jerry on 2023/1/25.
 * https://developer.android.com/training/data-storage?hl=zh-cn
 */
class AppDataDemoFragment :
    BaseBindingFragment<FragmentAppDataBinding>(FragmentAppDataBinding::inflate) {


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

        mBinding.btnCreateNestDir.setOnClickListener {
            createNestDir()
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

        mBinding.btnQueryAvailableSpace.setOnClickListener {
            queryAvailableSpace()
        }

        mBinding.btnRemoveFile.setOnClickListener {
            partlyRemoveFile()
        }

        mBinding.btnRemoveAllCacheFile.setOnClickListener {
            removeAllCacheFile()
        }

        mBinding.btnSaveToShareStorage.setOnClickListener {
            requireActivity().launchFragment<ShareStorageemoFragment>()
        }

    }

    private fun removeAllCacheFile() {

        try {
            val intent = Intent(StorageManager.ACTION_CLEAR_APP_CACHE)
            requireActivity().startActivityForResult(intent,99)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 让用户移除部分设备文件
     * 如需请求用户在设备上选择文件进行移除，请调用包含 ACTION_MANAGE_STORAGE 操作的 intent。
     * 此 intent 会向用户显示提示。如果需要，此提示可以显示设备上的可用空间量。如需显示此人性化信息，
     * 请使用以下计算结果：
     *
     *
     */
    private fun partlyRemoveFile() {
        val storageManager = requireActivity().getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
        val percent = storageManager.getFreeBytes(StorageManager.UUID_DEFAULT).toFloat() / storageManager.getTotalBytes(StorageManager.UUID_DEFAULT)
        Logger.d("可用空间占比:$percent")
    }

    /**
     * 您还可以通过以下方式创建嵌套目录或打开内部目录：在基于 Kotlin 的代码中调用 getDir()，或在基于 Java 的代码中将根目录和新目录名称传递到 File 构造函数：
     *
     * 注意 ：filesDir 始终是此新目录的祖先目录。
     */
    private fun createNestDir() {
        val dirName = "hello ${Random.nextInt(50)}"
        val file = mContext.getDir(dirName, Context.MODE_PRIVATE)
        if (!file.exists() && file.mkdirs()){
           Logger.d("$dirName 创建成功")
        }
        fileList()

    }

    /**
     * 查询可用空间
     * 如果您事先知道要存储的数据量，您可以通过调用 getAllocatableBytes() 查出设备可以为应用提供多少空间。getAllocatableBytes() 的返回值可能大于设备上的当前可用空间量。这是因为系统已识别出可以从其他应用的缓存目录中移除的文件。

    如果有足够的空间保存您的应用数据，请调用 allocateBytes()。否则，您的应用可以请求用户从设备移除一些文件或从设备移除所有缓存文件。


     */
    private fun queryAvailableSpace() {
// App needs 10 MB within internal storage.
         val NUM_BYTES_NEEDED_FOR_MY_APP = 1024 * 1024 * 10L

        val storageManager:StorageManager = requireContext().getSystemService<StorageManager>()!!
        val appSpecificInternalDirUuid: UUID = storageManager.getUuidForPath(requireActivity().filesDir)
        val availableBytes: Long =
            storageManager.getAllocatableBytes(appSpecificInternalDirUuid)
        Logger.d("queryAvailableSpace availableBytes:$availableBytes")

        if (availableBytes >= NUM_BYTES_NEEDED_FOR_MY_APP) {
            storageManager.allocateBytes(
                appSpecificInternalDirUuid, NUM_BYTES_NEEDED_FOR_MY_APP)
        } else {
            val storageIntent = Intent().apply {
                // To request that the user remove all app cache files instead, set
                // "action" to ACTION_CLEAR_APP_CACHE.
                action = ACTION_MANAGE_STORAGE
            }
        }
    }

    private fun receiveData() {
        val intent = Intent(activity, ReceiveDataDemoActivity::class.java)
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
        val file = getAppSpecificAlbumStorageDir(mContext, "chou")
        println("file name:${file?.name ?: "unknow"}")
    }


    private fun getAppSpecificAlbumStorageDir(context: Context, albumName: String): File? {
        // Get the pictures directory that's inside the app-specific directory on
        // external storage.
        val file: File = File(
            context.getExternalFilesDir(
                //务必使用 DIRECTORY_PICTURES 等 API 常量提供的目录名称。这些目录名称可确保系统正确处理文件。如果没有适合您文件的预定义子目录名称，您可以改为将 null 传递到 getExternalFilesDir()。这将返回外部存储空间中的应用专属根目录。
                Environment.DIRECTORY_PICTURES
                // null
            ), albumName
        )
        if (!file.mkdirs()) {
            println("Directory not created")
        }
        return file
    }

    private fun createCacheFileInExternal() {
        val child = "test:${Random.nextInt(5, 15)}"
        val externalCacheFile = File(mContext.externalCacheDir, child)
        externalCacheFile.writeText(child)

        val readText = externalCacheFile.readText()
        Logger.d("write text:$child, readText:${readText}")
    }


    /**
     * 由于外部存储空间位于用户可能能够移除的物理卷上，因此在尝试从外部存储空间读取应用专属数据或将应用专属数据写入外部存储空间之前，请验证该卷是否可访问。
     * 您可以通过调用 Environment.getExternalStorageState() 查询该卷的状态。如果返回的状态为 MEDIA_MOUNTED，那么您就可以在外部存储空间中读取和写入应用专属文件。如果返回的状态为 MEDIA_MOUNTED_READ_ONLY，您只能读取这些文件。
     */
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
        val cacheFile = File(mContext.cacheDir, fileName)
        Logger.d("deleteCacheFile cacheFile before delete exist:${cacheFile.exists()}")
        if (cacheFile.exists()) {
            cacheFile.delete()
        }
        Logger.d("deleteCacheFile cacheFile after delete exist:${cacheFile.exists()}")

    }

    private fun deleteFileByContext() {
        val fileName = "test"
        //注意：当设备的内部存储空间不足时，Android 可能会删除这些缓存文件以回收空间。因此，请在读取前检查缓存文件是否存在。
        val cacheFile = File(mContext.cacheDir, fileName)
        Logger.d("deleteFileByContext cacheFile before delete exist:${cacheFile.exists()}")
        if (cacheFile.exists()) {
            mContext.deleteFile(fileName)
        }
        Logger.d("deleteFileByContext cacheFile after delete exist:${cacheFile.exists()}")


    }

    /**
     * 创建缓存文件
     * 如果您只需要暂时存储敏感数据，应使用应用在内部存储空间中的指定缓存目录保存数据。与所有应用专属存储空间一样，当用户卸载应用后，系统会移除存储在此目录中的文件，但也可以更早地移除此目录中的文件。


     */
    private fun createTempFileInCacheDir() {
        val fileName = "test1"
        val tempFile = File.createTempFile(fileName, null, mContext.cacheDir)
        tempFile.writeText(fileName)
        //注意：当设备的内部存储空间不足时，Android 可能会删除这些缓存文件以回收空间。因此，请在读取前检查缓存文件是否存在。
        // val cacheFile = File(mContext.cacheDir, fileName)
        Logger.d("createTempFileInCacheDir cacheFile $fileName, exist:${tempFile.exists()}")

    }

    private fun fileList() {
        val fileList = mContext.fileList()
        fileList.toList().forEach {
            Logger.d("fileList file Name:$it")
        }

    }

    /**
     * 使用信息流访问文件
     */
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
     * 使用信息流存储文件
     * 注意：在搭载 Android 7.0（API 级别 24）或更高版本的设备上，除非您将 Context.MODE_PRIVATE 文件模式传递到 openFileOutput()，否则会发生 SecurityException。
     */
    private fun openFileOutput() {
        val fileName = "myFile"
        val fileContent = "Hello world:${Random.nextInt(5, 20)}"
        // openFileOutput 会写入 filesDir 目录中的文件的
        mContext.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(fileContent.toByteArray())
        }
        //
        val file = File(context?.filesDir, fileName)
        val readText = file.readText()
        Logger.d("openFileOutput readText:$readText")

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
     * 从内部存储空间访问，可以使用 getFilesDir() 或 getCacheDir() 方法
     *
     * 从内部存储空间访问不需要任何权限,注意:
     * 这些目录的空间通常比较小。在将应用专属文件写入内部存储空间之前，应用应查询设备上的可用空间。
     *  data/user/0/com.apache.fastandroid.debug/files
     *  data/user/0/com.apache.fastandroid.debug/cache
     *
     */
    private fun filesAndCacheDir() {
        //data/user/0/com.apache.fastandroid.debug/files
        //data/data/com.apache.fastandroid.debug/files
        val fileDir = mContext.filesDir
        //data/user/0/com.apache.fastandroid.debug/cache
        val cacheDir = mContext.cacheDir
        Logger.d("fileDir:$fileDir, cacheDir:$cacheDir")

        val file = File(mContext.filesDir, "userInfo")
        file.writeText("hello")
        val content = file.readText()
        Logger.d("content:$content")


    }

    /**
     * 从外部存储空间访问，可以使用 getExternalFilesDir() 或 getExternalCacheDir() 方法
     * 如果应用在搭载 Android 4.4（API 级别 19）或更高版本的设备上运行，从外部存储空间访问不需要任何权限
     * storage/emulated/0/Android/data/com.apache.fastandroid.debug/files
     * storage/emulated/0/Android/data/com.apache.fastandroid.debug/cache
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
package com.apache.fastandroid.util

import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*

/**
 * author: jerry
 * created on: 2020/8/21 2:50 PM
 * description:
 */
object IntonationConfigUtil {

    private const val TAG = "IntonationConfigUtil"

    private const val UI_CONFIG_JSON_FILE_NAME = "intonation_config.json"
    // todo 提交代码的时候， 记得改为false
    const val DEBUG = false



    /**
     * zipPath: /sdcard/intonation_ui_config/template1
     */
    fun getJsonConfigFilePath(zipPath:String): String {
        return zipPath + File.separator + UI_CONFIG_JSON_FILE_NAME
    }

    @Throws(IOException::class)
    fun readFileToString(file: File?): String? {
        return if (file == null) {
            null
        } else if (file.exists()) {
            if (file.isDirectory) {
                throw IOException("File '$file' exists but is a directory")
            } else if (!file.canRead()) {
                throw IOException("File '$file' cannot be read")
            } else {
                var fis: InputStream? = null
                var reader: InputStreamReader? = null
                var rtn: String? = null
                val var5 = false
                try {
                    fis = FileInputStream(file)
                    reader = InputStreamReader(fis, "UTF-8")
                    val size = file.length().toInt()
                    val buffer: CharArray
                    var n: Int
                    if (size > 12288) {
                        buffer = CharArray(4096)
                        val result = StringBuilder(12288)
                        while (-1 != reader.read(buffer).also { n = it }) {
                            result.append(buffer, 0, n)
                        }
                        rtn = result.toString()
                    } else {
                        buffer = CharArray(size)
                        n = reader.read(buffer)
                        rtn = String(buffer, 0, n)
                    }
                } catch (var20: Exception) {
                } finally {
                    if (fis != null) {
                        try {
                            fis.close()
                        } catch (var19: Exception) {
                        }
                    }
                    if (reader != null) {
                        try {
                            reader.close()
                        } catch (var18: Exception) {
                        }
                    }
                }
                rtn
            }
        } else {
            throw FileNotFoundException("File '$file' does not exist")
        }
    }

    fun writeFile(filePath: String?, str: String): Boolean {
        var out: FileOutputStream? = null
        val wfile = File(filePath)
        var ret = true
        if (!wfile.exists()) {
            try {
                wfile.createNewFile()
            } catch (var10: IOException) {
                ret = false
            }
        }
        try {
            out = FileOutputStream(wfile, false)
        } catch (var9: FileNotFoundException) {
            ret = false
        }
        try {
            out?.write(str.toByteArray())
        } catch (var8: IOException) {
            ret = false
        }
        try {
            out?.flush()
        } catch (var7: IOException) {
            ret = false
        }
        try {
            out?.close()
        } catch (var6: IOException) {
            ret = false
        }
        return ret
    }


}
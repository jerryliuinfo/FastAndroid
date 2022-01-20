package com.apache.fastandroid.demo.hawk.impl

import android.util.Base64
import com.apache.fastandroid.demo.hawk.interfaces.IEncryption

/**
 * Created by Jerry on 2022/1/19.
 */
class NoEncryption:IEncryption {
    override fun doInit(): Boolean {
        return true
    }

    override fun encrypt(key: String, value: String): String? {
        return encodeBase64(value.toByteArray())
    }

    override fun decrypt(key: String, value: String): String? {
        return decodeBase64(value)?.let { String(it) }

    }

    fun encodeBase64(bytes: ByteArray?): String? {
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    fun decodeBase64(value: String?): ByteArray? {
        return Base64.decode(value, Base64.DEFAULT)
    }
}
package com.apache.fastandroid.demo.hawk

import android.content.Context
import com.google.gson.Gson

/**
 * Created by Jerry on 2022/1/20.
 */
/*
class HawkBuilder {

    */
/**
     * NEVER ever change STORAGE_TAG_DO_NOT_CHANGE and TAG_INFO.
     * It will break backward compatibility in terms of keeping previous data
     *//*

    private val STORAGE_TAG_DO_NOT_CHANGE = "Hawk2"

    private var context: Context? = null
    private var cryptoStorage: Storage? = null
    private var converter: Converter? = null
    private var parser: Parser? = null
    private var encryption: Encryption? = null
    private var serializer: Serializer? = null
    private var logInterceptor: LogInterceptor? = null

    fun HawkBuilder(context: Context) {
        HawkUtils.checkNull("Context", context)
        this.context = context.applicationContext
    }

    fun setStorage(storage: Storage?): HawkBuilder? {
        cryptoStorage = storage
        return this
    }

    fun getStorage(): Storage? {
        if (cryptoStorage == null) {
            cryptoStorage = SharedPreferencesStorage(context, STORAGE_TAG_DO_NOT_CHANGE)
        }
        return cryptoStorage
    }

    fun setParser(parser: Parser?): HawkBuilder? {
        this.parser = parser
        return this
    }

    fun setSerializer(serializer: Serializer?): HawkBuilder? {
        this.serializer = serializer
        return this
    }

    fun setLogInterceptor(logInterceptor: LogInterceptor?): HawkBuilder? {
        this.logInterceptor = logInterceptor
        return this
    }

    fun setConverter(converter: Converter?): HawkBuilder? {
        this.converter = converter
        return this
    }

    fun setEncryption(encryption: Encryption?): HawkBuilder? {
        this.encryption = encryption
        return this
    }

    fun getLogInterceptor(): LogInterceptor? {
        if (logInterceptor == null) {
            logInterceptor = object : LogInterceptor() {
                fun onLog(message: String?) {
                    //empty implementation
                }
            }
        }
        return logInterceptor
    }


    fun getConverter(): Converter? {
        if (converter == null) {
            converter = HawkConverter(getParser())
        }
        return converter
    }

    fun getParser(): Parser? {
        if (parser == null) {
            parser = GsonParser(Gson())
        }
        return parser
    }

    fun getEncryption(): Encryption? {
        if (encryption == null) {
            encryption = ConcealEncryption(context)
            if (!encryption.init()) {
                encryption = NoEncryption()
            }
        }
        return encryption
    }

    fun getSerializer(): Serializer? {
        if (serializer == null) {
            serializer = HawkSerializer(getLogInterceptor())
        }
        return serializer
    }

    fun build() {
        Hawk.build(this)
    }
}*/

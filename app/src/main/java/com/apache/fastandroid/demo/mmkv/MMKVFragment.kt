package com.apache.fastandroid.demo.mmkv

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tencent.mmkv.MMKV
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_mmkv.*
import java.util.*


/**
 * Created by Jerry on 2021/8/31.
 */
class MMKVFragment:BaseFragment() {
    companion object{
        private const val TAG = "MMKVFragment"
    }
    override fun inflateContentView(): Int {
        return R.layout.fragment_mmkv
    }

    private lateinit var kv: MMKV

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        kv = MMKV.defaultMMKV()

        btn_basic.setOnClickListener {


            kv.encode("bool", true)
            NLog.d(TAG,"bool: " + kv.decodeBool("bool"))

            kv.encode("int", 10)
            NLog.d(TAG,"int: " + kv.decodeInt("int"))

            kv.encode("long", Long.MAX_VALUE)
            NLog.d(TAG,"long: " + kv.decodeLong("long"))

            kv.encode("string", "Hello from mmkv")
            NLog.d(TAG,"string: " + kv.decodeString("string"))

            val bytes = byteArrayOf('m'.toByte(), 'm'.toByte(), 'k'.toByte(), 'v'.toByte())
            kv.encode("bytes", bytes)
            println("bytes: " + kv.decodeBytes("bytes")?.let { String(it) })

        }

        btn_delete.setOnClickListener {
            kv.removeValueForKey("bool")
            NLog.d(TAG, "bool: ${kv.decodeBool("bool")}")

            kv.removeValuesForKeys(arrayOf("int","long"))
            NLog.d(TAG, "all keys: ${Arrays.toString(kv.allKeys())}")
        }

        btn_save_individual.setOnClickListener {
            var mmkvWithID = MMKV.mmkvWithID("myId")
            mmkvWithID.encode("bool",true)

            NLog.d(TAG, "bool1: ${kv.decodeBool("bool")}, bool2: ${mmkvWithID.decodeBool("bool")}")
        }


    }
}
package com.apache.fastandroid.demo.mmkv

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentMmkvBinding
import com.tencent.mmkv.MMKV
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.fragment_mmkv.*
import java.util.*


/**
 * Created by Jerry on 2021/8/31.
 */
class MMKVFragment: BaseVBFragment<FragmentMmkvBinding>(FragmentMmkvBinding::inflate) {
    companion object{
        private const val TAG = "MMKVFragment"
    }

    private lateinit var kv: MMKV

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        MMKV.initialize(context)
        kv = MMKV.defaultMMKV()

        btn_basic.setOnClickListener {

            val putResult = kv.putString("name", "jerry").commit()
            val getResult = kv.getString("name", null)
            NLog.d(TAG,"putResult: ${putResult}, getResult: $getResult")

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
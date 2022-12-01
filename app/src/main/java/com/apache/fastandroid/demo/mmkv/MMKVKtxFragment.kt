package com.apache.fastandroid.demo.mmkv

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.component.mmkv.*
import com.apache.fastandroid.databinding.FragmentMmkvKtxBinding
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.tencent.mmkv.MMKV
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment


/**
 * Created by Jerry on 2021/8/31.
 * https://github.com/DylanCaiCoding/MMKV-KTX
 */
class MMKVKtxFragment: BaseBindingFragment<FragmentMmkvKtxBinding>(FragmentMmkvKtxBinding::inflate),
    MMKVOwner {
    companion object{
        private const val TAG = "MMKVFragment"
    }


    private var i1 by mmkvInt()
    private var i2 by mmkvInt(default = -1)
    private var l1 by mmkvLong()
    private var l2 by mmkvLong(default = -1L)
    private var b1 by mmkvBool()
    private var b2 by mmkvBool(default = true)

    private var s1 by mmkvString()
    private var s2 by mmkvString(default = "")
    private var set1 by mmkvStringSet()

    private var user1 by mmkvParcelable<User>()
    private var user2 by mmkvParcelable(default = User(0, "Admin"))

    override val kv: MMKV = MMKV.mmkvWithID("repo")


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnMmkvInt.setOnClickListener {
            putInt1()
        }

        mBinding.btnMmkvIntDefault.setOnClickListener {
            putInt2()
        }

        mBinding.btnMmkvLong.setOnClickListener {
            putLong1()
        }

        mBinding.btnMmkvLongDefault.setOnClickListener {
            putLong2()
        }

        mBinding.btnMmkvBoolean.setOnClickListener {
            putBoolean1()
        }
        mBinding.btnMmkvBooleanDefault.setOnClickListener {
            putBoolean2()
        }

        mBinding.btnPutParcelable.setOnClickListener {
            putParcelable1()
        }

        mBinding.btnRemoveValue.setOnClickListener {
            removeValue()
        }

        mBinding.btnRemoveValues.setOnClickListener {
            removeValues()
        }
    }

    fun putInt1() {
        Logger.d("putInt1 i1 before:$i1")
        i1 = 6
        Logger.d("putInt1 i1 after:$i1")
    }

    fun putInt2() {
        Logger.d("putInt2  before:$i2")
        i2 = 6
        Logger.d("putInt2  after:$i2")

    }

    fun putLong1() {
        Logger.d("putLong1 before:$l1")
        l1 = 3L
        Logger.d("putLong1 after:$l1")
    }

    fun putLong2() {
        Logger.d("putLong2 before:$l1")
        l2 = 3L
        Logger.d("putLong2 after:$l1")
    }

    fun putBoolean1() {
        Logger.d("putBoolean1 before:$b1")
        b1 = true
        Logger.d("putBoolean1 after:$b1")
    }

    fun putBoolean2() {
        Logger.d("putBoolean2 before:$b2")
        b2 = true
        Logger.d("putBoolean2 after:$b2")

    }


    fun putParcelable1() {
        Logger.d("putParcelable1 before:$user1")
        user1 = User(1, "DylanCai")
        Logger.d("putParcelable1 after:$user1")

    }

    fun removeValue() {
        i1 = 6
        Logger.d("removeValue containsKey before:${kv.containsKey(::i1.name)}")
        kv.removeValueForKey(::i1.name)
//        Assert.assertFalse(kv.containsKey(::i1.name))
        Logger.d("removeValue containsKey after:${kv.containsKey(::i1.name)}")

    }

    fun removeValues() {
        s1 = "1"
        s2 = "2"
        Logger.d("removeValues containsKey before i1:${kv.containsKey(::i1.name)},i2:${kv.containsKey(::i2.name)}, ")
        kv.removeValuesForKeys(arrayOf(::s1.name, ::s2.name))
        Logger.d("removeValues containsKey after i1:${kv.containsKey(::i1.name)},i2:${kv.containsKey(::i2.name)}, ")
    }
}
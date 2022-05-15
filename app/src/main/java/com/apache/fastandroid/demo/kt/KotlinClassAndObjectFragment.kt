package com.apache.fastandroid.demo.kt

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.KtClassObjectBinding
import com.apache.fastandroid.databinding.KtOfficialGrammerBinding
import com.apache.fastandroid.demo.kt.bit.BitDemo
import com.apache.fastandroid.demo.kt.common.GsonDemo
import com.apache.fastandroid.demo.kt.constructor.Asiatic
import com.apache.fastandroid.demo.kt.constructor.Lion
import com.apache.fastandroid.demo.kt.delegate.DelegateDemo
import com.apache.fastandroid.demo.kt.delegate.ElvisPresley
import com.apache.fastandroid.demo.kt.delegate.TomAraya
import com.apache.fastandroid.demo.kt.enum.Colour
import com.apache.fastandroid.demo.kt.enum.State
import com.apache.fastandroid.demo.kt.extensions.*
import com.apache.fastandroid.demo.kt.genericity.MultableStack
import com.apache.fastandroid.demo.kt.genericity.mutableStackOf
import com.apache.fastandroid.demo.kt.sealed.*
import com.tesla.framework.kt.maxAge
import com.tesla.framework.kt.times
import com.tesla.framework.ui.fragment.BaseVBFragment
import java.io.File
import java.lang.IllegalArgumentException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * Created by Jerry on 2021/10/18.
 * https://book.kotlincn.net/text/classes.html
 */
class KotlinClassAndObjectFragment:BaseVBFragment<KtClassObjectBinding>(KtClassObjectBinding::inflate) {
    companion object{
        private const val TAG = "KotlinClassAndObjectFragment"
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnConstructor.setOnClickListener {
            constructorUsage()
        }

    }

    /**
     * 主构造函数不能包含任何的代码。初始化的代码可以放到以 init 关键字作为前缀的初始化块（initializer blocks）中。
        在实例初始化期间，初始化块按照它们出现在类体中的顺序执行，与属性初始化器交织在一起：
     */
    private fun constructorUsage() {
        val lion = Lion("Kitty", "china")
    }


}
package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentConcurryDemoBinding
import com.apache.fastandroid.demo.temp.concurry.PlayerNew
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2021/11/3.
 * 模拟死锁发生
 */
class ConcurrencyDemoFragment : BaseVBFragment<FragmentConcurryDemoBinding>(FragmentConcurryDemoBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        findViewById<View>(R.id.btn_sisuo).setOnClickListener {
            val zhangsan = PlayerNew("zhangsan")
            val lisi = PlayerNew("lisi")
            Thread(zhangsan, "线程1").start()
            Thread(lisi, "线程2").start()

            //只会打印出zhangsan pay 和 lisi pay
        }
    }


}
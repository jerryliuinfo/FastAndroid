package com.apache.fastandroid.demo.drakeet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentCommonBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2021/10/18.
 */
class LooperFragment:BaseVBFragment<FragmentCommonBinding>(FragmentCommonBinding::inflate) {
    private lateinit var msg:String

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
//        Toast.makeText(context,msg.toString(),Toast.LENGTH_SHORT).show()

        startActivity(Intent(activity,UnDeclareActivity::class.java))
    }
}
package com.apache.fastandroid.jetpack.lifecycle.customlifecycle

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.apache.fastandroid.databinding.FragmentJetpackLifecycleCustomRegistryBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/11/20.
 * https://www.modb.pro/db/556544
 */
class CustomLifecycleOwnerFragment : BaseVBFragment<FragmentJetpackLifecycleCustomRegistryBinding>(
    FragmentJetpackLifecycleCustomRegistryBinding::inflate
) {


    private val lifecycleOb = LifecycleEventObserver { source, event ->

        when (event) {
            Lifecycle.Event.ON_DESTROY -> {
                Logger.d("LifecycleEventObserver onDestroy")
                // 销毁逻辑
                for (index in 0 until mBinding.stubContainer.childCount) {
                    val child = mBinding.stubContainer.getChildAt(index)
                    if (child is StubView) {
                        child.release()
                    }

                }

            }
            Lifecycle.Event.ON_START-> {

                Logger.d("LifecycleEventObserver onStart")

            }

        }

    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        val stubVH = StubViewHolder(this, mBinding).apply {
            addLifecycleObserver(lifecycleOb)
        }

        mBinding.btnInstall.setOnClickListener {
            stubVH.install()
        }

        mBinding.btnUnInstall.setOnClickListener {
            stubVH.unInstall()
        }

        lifecycle.addObserver(lifecycleOb)
    }


}
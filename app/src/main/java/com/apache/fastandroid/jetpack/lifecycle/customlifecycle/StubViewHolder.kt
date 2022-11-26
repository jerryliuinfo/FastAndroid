package com.apache.fastandroid.jetpack.lifecycle.customlifecycle

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.apache.fastandroid.databinding.FragmentJetpackLifecycleBinding
import com.apache.fastandroid.databinding.FragmentJetpackLifecycleCustomRegistryBinding

/**
 * Created by Jerry on 2022/11/22.
 */
class StubViewHolder(val owner: Fragment, val binding: FragmentJetpackLifecycleCustomRegistryBinding) {

    private var lifecycleOwner : LifecycleOwner = LifecycleOwnerWrapper(owner)



    fun addLifecycleObserver(ob: LifecycleObserver) {

        (lifecycleOwner as LifecycleOwnerWrapper).addObserver(ob)

    }



    fun install() {

        val stub = StubView(owner.context)

        (lifecycleOwner as LifecycleOwnerWrapper).markState(Lifecycle.State.STARTED)

        binding.stubContainer.addView(stub)

    }



    fun unInstall() {

        for (index in 0 until binding.stubContainer.childCount) {

            val child = binding.stubContainer.getChildAt(index)

            if (child is StubView) {

                (lifecycleOwner as LifecycleOwnerWrapper).markState(Lifecycle.State.DESTROYED)

                binding.stubContainer.removeView(child)

            }

        }

    }

}
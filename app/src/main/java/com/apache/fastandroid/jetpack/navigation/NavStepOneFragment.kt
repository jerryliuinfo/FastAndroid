package com.apache.fastandroid.jetpack.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FlowStepOneFragmentBinding
import com.apache.fastandroid.databinding.FragmentNavHomeBinding
import com.apache.fastandroid.databinding.FragmentStepOneBinding
import com.tesla.framework.ui.fragment.BaseVBFragment

/**
 * Created by Jerry on 2022/3/11.
 */
class NavStepOneFragment:Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val safeArgs: NavStepOneFragmentArgs by navArgs()
        return when(safeArgs.flowStepNumber){
            2 -> inflater.inflate(R.layout.flow_step_two_fragment,container,false)
            else -> inflater.inflate(R.layout.flow_step_one_fragment,container,false)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.next_button).setOnClickListener {
            findNavController().navigate(R.id.next_action)
        }


    }
}
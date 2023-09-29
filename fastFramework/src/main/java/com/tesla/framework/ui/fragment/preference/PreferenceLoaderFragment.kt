package com.tesla.framework.ui.fragment.preference

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.RecyclerView

abstract class PreferenceLoaderFragment : PreferenceFragmentCompat(), PreferenceLoader {
    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        requireActivity().window.decorView.post {
            if (!isAdded) {
                return@post
            }
            loadPreferences()
        }
    }

    override fun onCreateRecyclerView(inflater: LayoutInflater, parent: ViewGroup, savedInstanceState: Bundle?): RecyclerView {
        val v = super.onCreateRecyclerView(inflater, parent, savedInstanceState)
//        v.setBackgroundColor(ResourceUtil.getThemedColor(requireContext(), R.attr.paper_color))
        return v
    }
}

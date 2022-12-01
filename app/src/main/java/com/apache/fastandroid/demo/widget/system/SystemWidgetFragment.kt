package com.apache.fastandroid.demo.widget.system

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.text.HtmlCompat
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentSystemWidgetBinding
import com.apache.fastandroid.widget.ScrollableDialogBuilder
import com.blankj.utilcode.util.ResourceUtils
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/6/18.
 */
class SystemWidgetFragment:BaseBindingFragment<FragmentSystemWidgetBinding>(FragmentSystemWidgetBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnTextinput.setOnClickListener {
           AlertDialog.Builder(requireContext())
               .setTitle("title")
               .setView(R.layout.fragment_textinput)
               .show()
        }
        mBinding.btnMateralDialog.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Material title")
                .setMessage("I am long message")
                .show()
        }
        mBinding.btnScrollableDialog.setOnClickListener {
            val spannedChangelog = HtmlCompat.fromHtml(
                ResourceUtils.readAssets2String( "changelog.html"),
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )

            ScrollableDialogBuilder(requireActivity(),spannedChangelog)
                .setTitle("更新日志")
                .setNegativeButton("OK",null)
                .setPositiveButton("说明",null)
                .show()

        }

    }
}
package com.apache.fastandroid.demo.temp

import android.os.Bundle
import android.view.LayoutInflater
import android.text.SpannableStringBuilder
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.TextPaint
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentSpannableStringBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2021/1/20.
 */
class SpanableStringFragment : BaseBindingFragment<FragmentSpannableStringBinding>(FragmentSpannableStringBinding::inflate) {
    private lateinit var mChildrenPrivacyText: TextView
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mChildrenPrivacyText = findViewById(R.id.tv_content)
        val spannableStringBuilder = SpannableStringBuilder()
        //《儿童隐私政策》
        val cpp = SpannableString(requireContext().resources.getString(R.string.experience_info3))
        //若您是16周岁以下的未成年人，请确保您的父母或监护人已经仔细阅读%1$s，并进行确认。
        val formatStrings = setSpannableString(
            requireContext().resources.getString(R.string.experience_info2),
            cpp.toString()
        )
        cpp.setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
//                KidsUtils.onPrivacyPolicyClick(getContext());
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = resources.getColor(R.color.edu_color_blue)
                //                ds.setUnderlineText(false);
            }
        }, 0, cpp.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.append(formatStrings[0]).append(cpp).append(formatStrings[1])
        mChildrenPrivacyText.setText(spannableStringBuilder)
        mChildrenPrivacyText.setMovementMethod(LinkMovementMethod.getInstance())

    }



    private fun setSpannableString(text: String, format: String): Array<String> {
        var text = text
        text = String.format(text, format)
        return text.split(format.toRegex(), 2).toTypedArray()
    }
}
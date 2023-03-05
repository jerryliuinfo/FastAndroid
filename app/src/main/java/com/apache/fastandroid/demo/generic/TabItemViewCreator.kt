package com.apache.fastandroid.demo.generic

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.tesla.framework.support.bean.TabItem

/**
 * [ViewCreator] which creates [RewordTabItem]s.
 */
internal object TabItemViewCreator : ViewCreator<LinearLayout> {

    private const val ATTRIBUTE_TEXT = "text"
    private const val ATTRIBUTE_ANDROID_TEXT = "android:text"


    override val viewName = TabItem::class.qualifiedName.toString()

    override fun createView(context: Context, attrs: AttributeSet?): LinearLayout {

//        val extractedAttributes = attrs?.extractAttributes(supportedAttributes)
//        val textResourceId = extractedAttributes?.toList()?.firstOrNull()?.second
//
//        return RewordTabItem(context, attrs, textResourceId).apply {
//            setTag(R.id.view_tag, extractedAttributes)
//        }
        return LinearLayout(context,attrs)
    }
}
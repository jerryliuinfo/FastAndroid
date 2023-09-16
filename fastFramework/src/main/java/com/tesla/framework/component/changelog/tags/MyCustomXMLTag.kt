package com.tesla.framework.component.changelog.tags

import android.content.Context

/**
 * Created by Jerry on 2023/9/10.
 */
class MyCustomXMLTag: IChangelogTag {
    override fun getXmlTagName(): String {
        return "customTag"
    }

    override fun formatChangeLogRow(context: Context, changeText: String): String {
        val prefix = "<font color=\"#0000FF\"><b>Custom tag prefix: </b></font>"
        return prefix + changeText
    }
}
package com.tesla.framework.component.changelog.tags

import android.content.Context

/**
 * Created by Jerry on 2023/9/10.
 */
class ChangelogTagInfo: IChangelogTag {
    override fun getXmlTagName(): String {
        return "info"
    }

    override fun formatChangeLogRow(context: Context, changeText: String): String {
        return changeText
    }
}
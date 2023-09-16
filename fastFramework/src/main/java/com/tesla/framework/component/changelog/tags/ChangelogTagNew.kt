package com.tesla.framework.component.changelog.tags

import android.content.Context
import com.tesla.framework.R

/**
 * Created by Jerry on 2023/9/10.
 */
class ChangelogTagNew: IChangelogTag {
    override fun getXmlTagName(): String {
        return "new"
    }

    override fun formatChangeLogRow(context: Context, changeText: String): String {
        var prefix = context.resources.getString(R.string.changelog_bug_improvement)
        prefix = prefix.replace("\\[".toRegex(), "<").replace("\\]".toRegex(), ">")
        return prefix + changeText
    }
}
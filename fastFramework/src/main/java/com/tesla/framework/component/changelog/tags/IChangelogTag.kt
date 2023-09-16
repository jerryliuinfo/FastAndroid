package com.tesla.framework.component.changelog.tags

import android.content.Context

/**
 * Created by Jerry on 2023/9/10.
 */
interface IChangelogTag {

    fun getXmlTagName():String

    fun formatChangeLogRow(context:Context, changeText:String):String
}
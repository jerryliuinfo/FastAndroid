package com.apache.fastandroid.network.model

import java.io.Serializable


/**
 * Created with Android Studio.
 * Description: 界面展示的实体类,非界面展示的实体类不要定义在这里
 * @author: Wangjianxian
 * @date: 2020/02/25
 * Time: 21:10
 */
data class Article(
    var author: String = "",
    var shareUser: String = "",
    var chapterName: String? = "",
    var link: String = "",
    var title: String = "",
    var collect: Boolean = false,
    var superChapterName: String? = "",

    var niceDate: String = "",
    var fresh: Boolean = false,
    var top: Boolean = false,

    val loadAuthorInfo:() -> Unit?= {}
):Serializable
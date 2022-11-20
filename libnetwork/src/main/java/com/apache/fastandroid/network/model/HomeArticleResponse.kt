package com.apache.fastandroid.network.model

import java.io.Serializable


/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @date: 2020/02/26
 * Time: 10:58
 */
data class HomeArticleResponse(var curPage: Int,
                               var datas: List<ArticleApi>,
                               var offset: Int,
                               var over: Boolean,
                               var pageCount: Int,
                               var size: Int,
                               var total: Int):Serializable
package com.apache.fastandroid.home

import android.text.Html
import android.view.View
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.network.model.Article
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tesla.framework.common.util.N

/**
 * Created by Jerry on 2021/7/1.
 */
class ArticleAdapter(data: List<Article>?,val listener :(View,Int) -> Unit ? ) :
    BaseQuickAdapter<Article, BaseViewHolder>(R.layout.article_item, data) {


    override fun convert(holder: BaseViewHolder, it: Article) {

        holder.setText(R.id.item_article_author, handleAuthor(it))
            .setText(R.id.item_article_title, handleTitle(it))
            .setText(R.id.item_article_date, it.niceDate)
            .setText(R.id.item_article_type, handleCategory(it))
            .setImageResource(R.id.item_list_collect, isCollect(it))
            .addOnClickListener(R.id.item_list_collect)
            .setVisible(R.id.item_article_new, it.fresh)
            .setVisible(R.id.item_article_top_article, it.top)
            .setGone(R.id.item_article_top_article, it.top)

        holder.getView<TextView>(R.id.item_article_author).setOnClickListener {
            listener?.invoke(it, holder.bindingAdapterPosition)
        }
    }

    private fun handleTitle(article: Article?): String {
        return if (article != null) {
            Html.fromHtml(article.title, Html.FROM_HTML_MODE_COMPACT)
                .toString()
        } else ""
    }

    private fun handleAuthor(article: Article): String {
        if (N.isEmpty(article.author) && N.isEmpty(article.shareUser)) {
            return "匿名用户"
        } else if (N.isEmpty(article.author)) {
            return "作者" + article.shareUser
        } else if (N.isEmpty(article.shareUser)) {
            return "作者" + article.author
        }
        return ""
    }

    private fun handleCategory(article: Article): String {
        if (N.isEmpty(article.superChapterName) && N.isEmpty(article.chapterName)) {
            return ""
        } else if (N.isEmpty(article.superChapterName)) {
            return "作者" + article.chapterName
        } else if (N.isEmpty(article.chapterName)) {
            return "作者" + article.superChapterName
        }
        return ""
    }

    private fun isCollect(article: Article): Int {
        return if (article.collect) R.drawable.collect_selector_icon else R.drawable.uncollect_selector_icon
    }
}
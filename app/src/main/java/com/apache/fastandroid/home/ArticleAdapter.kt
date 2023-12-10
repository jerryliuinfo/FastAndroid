package com.apache.fastandroid.home

import android.text.Html
import android.view.View
import android.widget.TextView
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.ArticleItemBinding
import com.apache.fastandroid.demo.extension.isNotNullOrEmpty
import com.apache.fastandroid.network.model.Article
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.tesla.framework.common.util.N
import com.tesla.framework.common.util.buildSpannableString
import com.tesla.framework.kt.getColor

/**
 * Created by Jerry on 2021/7/1.
 * 警告：将 ViewModel 传入 RecyclerView 适配器是一种不妥的做法，因为它会将适配器与 ViewModel 类紧密耦合。
 */
class ArticleAdapter(
    data: List<Article>,
    val listener: (View, Int) -> Unit = { viwe, position -> }
) :
    BaseQuickAdapter<Article, BaseDataBindingHolder<ArticleItemBinding>>(
        R.layout.article_item,
        data.toMutableList()
    ),
    LoadMoreModule {

    init {
        setHasStableIds(true)
    }

    override fun convert(holder: BaseDataBindingHolder<ArticleItemBinding>, article: Article) {

        holder
            .setText(R.id.item_article_title, handleTitle(article))
            .setText(R.id.item_article_date, article.niceDate)
            .setText(R.id.item_article_type, handleCategory(article))
            .setImageResource(R.id.item_list_collect, isCollect(article))


        holder.getView<TextView>(R.id.item_article_author).buildSpannableString {
            if (article.top) {
                append("置顶  ") {
                    setColor(R.color.holo_red_light.getColor(context))
                }
            }
            if (article.fresh) {
                append("新  ") {
                    setColor(R.color.holo_red_light.getColor(context))
                }
            }
            append(handleAuthor(article))
        }

        //模拟卡顿
        // Thread.sleep(100)

        holder.getView<TextView>(R.id.item_article_author).setOnClickListener {
            //方式1 使用回调接口，在 fragment 中调用viewmodel 中的接口
            //
//            listener?.invoke(it, holder.layoutPosition)
            article.loadAuthorInfo()
        }
        holder.getView<TextView>(R.id.item_article_author).setOnClickListener {
//            listener?.invoke(it, holder.layoutPosition)
            article.loadAuthorInfo()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.id.item_article_type
    }


    private fun handleTitle(article: Article?): String {
        return if (article != null) {
            Html.fromHtml(article.title, Html.FROM_HTML_MODE_COMPACT)
                .toString()
        } else ""
    }

    private fun handleAuthor(article: Article): String {
        if (article.author.isNotNullOrEmpty() && article.shareUser.isNotNullOrEmpty()) {
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
        } else if (article.superChapterName.isNullOrEmpty()) {
            return "作者" + article.chapterName
        } else if (article.chapterName.isNullOrEmpty()) {
            return "作者" + article.superChapterName
        }
        return ""
    }

    private fun isCollect(article: Article): Int {
        return if (article.collect) R.drawable.collect_selector_icon else R.drawable.uncollect_selector_icon
    }


}
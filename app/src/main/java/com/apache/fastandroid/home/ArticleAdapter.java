package com.apache.fastandroid.home;

import android.text.Html;

import com.apache.fastandroid.R;
import com.apache.fastandroid.network.model.Article;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tesla.framework.common.util.N;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2021/7/1.
 */
public class ArticleAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {
    public ArticleAdapter( @Nullable List<Article> data) {
        super(R.layout.article_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Article it) {
        holder.setText(R.id.item_article_author, handleAuthor(it))
                .setText(R.id.item_article_title, handleTitle(it))
                .setText(R.id.item_article_date, it.getNiceDate())
                .setText(R.id.item_article_type, handleCategory(it))
                .setImageResource(R.id.item_list_collect, isCollect(it))
                .addOnClickListener(R.id.item_list_collect)
                .setVisible(R.id.item_article_new, it.getFresh())
                .setVisible(R.id.item_article_top_article, it.getTop())
                .setGone(R.id.item_article_top_article, it.getTop());
    }



    private String handleTitle( Article article)  {

        if (article != null){
            return Html.fromHtml(article.getTitle(), Html.FROM_HTML_MODE_COMPACT).toString();
        }
        return "";
    }

    private String handleAuthor( Article article) {
        if (N.isEmpty(article.getAuthor()) && N.isEmpty(article.getShareUser()) ){
            return "匿名用户";
        }else if (N.isEmpty(article.getAuthor())){
            return "作者"+ article.getShareUser();
        }else if (N.isEmpty(article.getShareUser())){
            return "作者"+ article.getAuthor();
        }
        return "";

    }

    private String handleCategory(Article article)  {
        if (N.isEmpty(article.getSuperChapterName()) && N.isEmpty(article.getChapterName()) ){
            return "";
        }else if (N.isEmpty(article.getSuperChapterName())){
            return "作者"+ article.getChapterName();
        }else if (N.isEmpty(article.getChapterName())){
            return "作者"+ article.getSuperChapterName();
        }
        return "";
    }

    private int isCollect(Article article) {
        return article.getCollect()? R.drawable.collect_selector_icon : R.drawable.uncollect_selector_icon;
    }
}

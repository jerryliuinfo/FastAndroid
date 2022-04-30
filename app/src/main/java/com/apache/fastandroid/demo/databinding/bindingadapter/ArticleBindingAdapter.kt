package com.apache.fastandroid.demo.databinding.bindingadapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.apache.fastandroid.network.model.Article
import com.bumptech.glide.Glide
import com.tesla.framework.common.util.N
import com.tesla.framework.common.util.log.NLog

/**
 * Created by Jerry on 2022/4/30.
 */
@BindingAdapter(value = ["articleauthor"], requireAll = false)
fun author(textView: TextView, article:Article ) {
    textView.text = handleAuthor(article)
}

 fun handleAuthor(article: Article): String {
    if (N.isEmpty(article.author) && N.isEmpty(article.shareUser)) {
        return "匿名用户"
    } else if (N.isEmpty(article.author)) {
        return "作者" + article.shareUser
    } else if (N.isEmpty(article.shareUser)) {
        return "作者" + article.author
    }
    return ""
}
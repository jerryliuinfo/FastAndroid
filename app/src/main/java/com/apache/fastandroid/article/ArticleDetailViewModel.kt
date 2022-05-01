package com.apache.fastandroid.article

import com.apache.fastandroid.jetpack.BaseViewModel
import androidx.databinding.ObservableField

/**
 * Created by Jerry on 2021/9/23.
 */
class ArticleDetailViewModel : BaseViewModel() {
    var title = ObservableField<String>()
    var url = ObservableField<String>()
}
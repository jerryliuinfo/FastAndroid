package com.apache.fastandroid.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ArticleModelFactory(private val repository: ArticleReporsitoryKt) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ArticleViewModel(repository) as T
    }
}
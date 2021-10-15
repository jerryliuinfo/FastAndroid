package com.apache.fastandroid.article;

import com.apache.fastandroid.jetpack.BaseViewModel;

import androidx.databinding.ObservableField;

/**
 * Created by Jerry on 2021/9/23.
 */
public class ArticleDetailViewModel extends BaseViewModel {
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> url = new ObservableField<>();
}

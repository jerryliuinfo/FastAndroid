package com.apache.fastandroid.home;

import com.apache.fastandroid.network.model.Article;
import com.apache.fastandroid.network.model.HomeArticleResponse;
import com.apache.fastandroid.jetpack.BaseViewModel;
import com.apache.fastandroid.jetpack.StateLiveData;

import java.util.List;

/**
 * Created by Jerry on 2021/7/1.
 */
public class HomeViewModel extends BaseViewModel {
    private StateLiveData<HomeArticleResponse> homeArticleLiveData = new StateLiveData<>();

    private StateLiveData<List<Article>> topArticleLiveData = new StateLiveData<>();

    private HomeReporsitory repository = new HomeReporsitory();

    public StateLiveData<HomeArticleResponse> getHomeArticleLiveData() {
        return homeArticleLiveData;
    }

    public StateLiveData<List<Article>> getTopArticleLiveData() {
        return topArticleLiveData;
    }

    private void loadTopArticleCo(){
        repository.loadTopArticleCo(topArticleLiveData);
    }

    public void loadHomeData(int pageNum){
        repository.loadHomeArticleCo(homeArticleLiveData,pageNum);
        if (pageNum == 0){
            loadTopArticleCo();
        }
    }
}

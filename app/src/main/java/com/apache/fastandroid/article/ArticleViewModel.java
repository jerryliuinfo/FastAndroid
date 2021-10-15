package com.apache.fastandroid.article;

import com.apache.fastandroid.bean.CollectBean;
import com.apache.fastandroid.home.HomeReporsitory;
import com.apache.fastandroid.jetpack.BaseViewModel;
import com.apache.fastandroid.jetpack.StateLiveData;

/**
 * Created by Jerry on 2021/10/14.
 */
public class ArticleViewModel extends BaseViewModel {

   public StateLiveData<CollectBean> stateLiveData = new StateLiveData<>();

   public void collect(int id){
      HomeReporsitory.newInstance().collect(stateLiveData,id);
   }

   public void unCollect(int id){
      HomeReporsitory.newInstance().uncollect(stateLiveData,id);
   }
}

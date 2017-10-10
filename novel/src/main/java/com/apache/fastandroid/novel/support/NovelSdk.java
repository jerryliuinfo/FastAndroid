package com.apache.fastandroid.novel.support;

import com.apache.fastandroid.artemis.BaseBizLogic;
import com.apache.fastandroid.novel.find.rank.bean.RankingList;
import com.apache.fastandroid.novel.find.rank.view.RankItemViewCreator;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.task.TaskException;

import java.util.ArrayList;

/**
 * Created by 01370340 on 2017/10/9.
 */

public class NovelSdk extends BaseBizLogic {
    @Override
    protected HttpConfig configHttpConfig() {
        HttpConfig config = new HttpConfig();
        config.baseUrl = NovelConstans.API_BASE_URL;
        return config;
    }

    public static NovelSdk newInstance(){
        return new NovelSdk();
    }

    public ArrayList<RankingList.MaleBean> getRankingList() throws TaskException{
        Setting action  = newSetting("getRankingList","ranking/gender","getRankingList");
        RankingList originalRankingList = doGet(getHttpConfig(),action,null,RankingList.class);

        RankingList newRankingList = new RankingList();
        if (originalRankingList != null && originalRankingList.male != null && originalRankingList.male.size() > 0){
            //cover: /ranking-cover/142319144267827
            RankingList.MaleBean maleGroupBean = new RankingList.MaleBean("男生");
            maleGroupBean.itemType = RankItemViewCreator.TYPE_GROUP;
            newRankingList.male.add(maleGroupBean);

            for (RankingList.MaleBean maleBean : originalRankingList.male) {
                maleBean.cover = NovelConstans.IMG_BASE_URL + maleBean.cover;
                if (!maleBean.collapse){
                    newRankingList.male.add(maleBean);
                }
            }

        }
        if (originalRankingList != null && originalRankingList.female != null && originalRankingList.female.size() > 0){
            RankingList.MaleBean femaleGroupBean = new RankingList.MaleBean("女生");
            femaleGroupBean.itemType = RankItemViewCreator.TYPE_GROUP;
            newRankingList.female.add(femaleGroupBean);

            for (RankingList.MaleBean femaleBean : originalRankingList.female) {
                femaleBean.cover = NovelConstans.IMG_BASE_URL + femaleBean.cover;
                if (!femaleBean.collapse){
                    newRankingList.female.add(femaleBean);
                }
            }

        }

        ArrayList<RankingList.MaleBean> list = new ArrayList<>();
        list.addAll(newRankingList.male);
        list.addAll(newRankingList.female);

        return list;
    }
}

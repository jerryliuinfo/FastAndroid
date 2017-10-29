package com.apache.fastandroid.novel.support.sdk;

import com.apache.fastandroid.novel.find.bean.BookDetail;
import com.apache.fastandroid.novel.find.bean.CollectionBeans;
import com.apache.fastandroid.novel.find.bean.HotReviewBean;
import com.apache.fastandroid.novel.find.bean.RankingList;
import com.apache.fastandroid.novel.find.bean.Rankings;
import com.apache.fastandroid.novel.find.bean.RecommandBeans;
import com.apache.fastandroid.novel.find.rank.view.RankItemViewCreator;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.apache.fastandroid.novel.support.sqlite.CollectionDB;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.network.biz.ABizLogic;
import com.tesla.framework.network.http.HttpConfig;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.network.task.TaskException;

import java.util.ArrayList;

/**
 * Created by 01370340 on 2017/10/9.
 */

public class NovelSdk extends ABizLogic {
    @Override
    protected HttpConfig configHttpConfig() {
        HttpConfig config = new HttpConfig();
        config.baseUrl = NovelConstans.API_BASE_URL;
        return config;
    }

    private NovelSdk() {
    }

    private NovelSdk(CacheMode cacheMode){
        super(cacheMode);
    }


    public static NovelSdk getInstance(){
        return new NovelSdk();
    }
    public static NovelSdk getInstance(CacheMode cacheMode){
        return new NovelSdk(cacheMode);
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





    /**
     * 获取单一排行榜
     * 周榜：rankingId->_id
     * 月榜：rankingId->monthRank
     * 总榜：rankingId->totalRank
     * http://api.zhuishushenqi.com/ranking/54d42d92321052167dfb75e3
     *
     * @return
     */
     public Rankings getRanking(String rankingId) throws TaskException{

         Setting setting = newSetting("getRanking", "ranking/"+rankingId, "获取单一排行榜");
         Rankings rankings = doGet(getHttpConfig(),setting,null,Rankings.class);
         return rankings;
     }


    /**
     * 获得书籍详情
     * @param bookId
     * @return
     * @throws TaskException
     */
     public BookDetail getBookDetail(String bookId) throws TaskException{
         Setting setting = newSetting("getBookDetail", "book/"+bookId, "获取书籍详情");
         return doGet(getHttpConfig(),setting,null,BookDetail.class);
     }

    /**
     * 获取推荐书籍列表
     * @param bookId
     * @param limit
     * @return
     * @throws TaskException
     */
     public RecommandBeans getRecommandBookList(String bookId, String limit)throws TaskException{
         Setting setting = newSetting("getRecommendBookList", "book-list/"+bookId +"/recommend", "获取书籍详情");
         Params params = new Params();
         params.addParameter("limit",limit);
         RecommandBeans bean =  doGet(getHttpConfig(),setting,params,RecommandBeans.class);
         if (bean == null || bean.booklists == null || bean.booklists.size() == 0){
             throw new TaskException("数据为null");
         }
         return bean;
     }


     /**
     * 热门评论
     *
     * @param bookId
     * @return
     */
     public HotReviewBean getHotReview(String bookId) throws TaskException{
         Setting action = newSetting("getHotReview","post/review/best-by-book","获取热门评论");
         Params params = new Params();
         params.addParameter("book",bookId);
         HotReviewBean hotReviewBean =  doGet(configHttpConfig(),action,params,HotReviewBean.class);
         if (hotReviewBean == null || hotReviewBean.reviews == null || hotReviewBean.reviews.size() == 0){
             throw new TaskException("数据为空");
         }

         return hotReviewBean;
     }



     public CollectionBeans getCollection() throws TaskException{
//        Setting setting = newSetting("getCollection", "","获取收藏列表");
//        setting.getExtras().put(CACHE_UTILITY, newSettingExtra("getCollection",CollectionDB.class.getName(),"获取收藏"));
//        CollectionBeans beans = doGet(getHttpConfig(),setting,null,CollectionBeans.class);

         return new CollectionBeans(CollectionDB.selectAll());
     }
}

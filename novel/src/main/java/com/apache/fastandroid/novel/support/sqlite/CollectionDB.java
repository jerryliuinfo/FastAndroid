package com.apache.fastandroid.novel.support.sqlite;

import com.apache.fastandroid.novel.find.bean.CollectionBeans;
import com.apache.fastandroid.novel.find.bean.RecommendBook;
import com.tesla.framework.common.util.date.FormatTimeUtil;
import com.tesla.framework.common.setting.Setting;
import com.tesla.framework.component.orm.extra.Extra;
import com.tesla.framework.network.biz.IResult;
import com.tesla.framework.network.cache.ICacheUtility;
import com.tesla.framework.network.http.Params;
import com.tesla.framework.support.db.FastAndroidDB;

import java.util.List;

/**
 * Created by 01370340 on 2017/10/25.
 */

public class CollectionDB implements ICacheUtility{

    public static void insert(RecommendBook book){
        Extra extra = new Extra();
        FastAndroidDB.getDB().insertOrReplace(extra, book);
    }

    public static List<RecommendBook> selectAll(){
        Extra extra = new Extra();
        return FastAndroidDB.getDB().select(extra,RecommendBook.class);
    }

    public static void update(RecommendBook book){
        Extra extra = new Extra();

        FastAndroidDB.getDB().update(extra,book);
    }

    public static void remove(String bookId){
        Extra extra = new Extra();
        FastAndroidDB.getDB().deleteById(extra,RecommendBook.class,bookId);
    }

    @Override
    public void addCache(Setting action, Params params, IResult iResult) {

    }

    @Override
    public IResult findCacheData(Setting action, Params params) {
        List<RecommendBook> books = selectAll();

        CollectionBeans beans = new CollectionBeans();
        beans.list = books;
        beans.setFramCache(true);
        return beans;
    }


    public static boolean isCollected(String bookId){
        List<RecommendBook> list = selectAll();
        if (list == null || list.isEmpty()){
            return false;
        }
        for (RecommendBook recommendBook : list) {
            if (bookId.equals(recommendBook._id)){
                return true;
            }
        }
        return false;
    }

    public static void setRecentReadingTime(String bookId){
        List<RecommendBook> list = selectAll();
        if (list == null || list.isEmpty()){
            return ;
        }
        for (RecommendBook bean : list) {
            if (bookId.equals(bean._id)){
                bean.recentReadingTime = FormatTimeUtil.getCurrentTimeString(FormatTimeUtil.FORMAT_DATE_TIME);
                update(bean);
                break;
            }
        }
    }
}

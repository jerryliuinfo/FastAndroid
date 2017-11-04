/**
 * Copyright 2016 JustWayward Team
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apache.fastandroid.novel.support.mananger;

import android.content.Context;
import android.text.TextUtils;

import com.apache.fastandroid.novel.find.bean.BookLists;
import com.apache.fastandroid.novel.find.bean.BookMixAToc;
import com.apache.fastandroid.novel.find.bean.ChapterRead;
import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.apache.fastandroid.novel.support.util.ChapterFileUtil;
import com.apache.fastandroid.novel.support.util.NovelLog;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.util.file.FileUtils;
import com.tesla.framework.common.util.sdcard.SdcardUtils;
import com.tesla.framework.common.util.sp.SharedPreferencesUtil;
import com.tesla.framework.support.cache.ACache;
import com.tesla.framework.ui.widget.ToastUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyh.
 * @date 2016/9/28.
 */
public class CacheManager {

    private ACache aCache;


    private static volatile CacheManager instance = null;
    private CacheManager(){

        aCache = ACache.get(new File(NovelConstans.BASE_PATH +File.separator +"novel"));
    }

    public static CacheManager getInstance() {
        if (instance == null) {
            synchronized (CacheManager.class) {
                if (instance == null){
                    instance = new CacheManager();
                }
            }
        }
        return instance;
    }

    public List<String> getSearchHistory() {
        return SharedPreferencesUtil.getInstance().getObject(getSearchHistoryKey(), List.class);
    }

    public void saveSearchHistory(Object obj) {
        SharedPreferencesUtil.getInstance().putObject(getSearchHistoryKey(), obj);
    }

    private String getSearchHistoryKey() {
        return "searchHistory";
    }

    /**
     * 获取我收藏的书单列表
     *
     * @return
     */
    public List<BookLists.BookListsBean> getCollectionList() {
        List<BookLists.BookListsBean> list = (ArrayList<BookLists.BookListsBean>) aCache.getAsObject(getCollectionKey());
        return list == null ? null : list;
    }

    public void removeCollection(String bookListId) {
        List<BookLists.BookListsBean> list = getCollectionList();
        if (list == null) {
            return;
        }
        for (BookLists.BookListsBean bean : list) {
            if (bean != null) {
                if (TextUtils.equals(bean._id, bookListId)) {
                    list.remove(bean);
                    aCache.put(getCollectionKey(), (Serializable) list);
                    break;
                }
            }
        }
    }

    public void addCollection(BookLists.BookListsBean bean) {
        List<BookLists.BookListsBean> list = getCollectionList();
        if (list == null) {
            list = new ArrayList<>();
        }
        for (BookLists.BookListsBean data : list) {
            if (data != null) {
                if (TextUtils.equals(data._id, bean._id)) {
                    ToastUtils.showMessage(FrameworkApplication.getContext(),"已经收藏过啦");
                    return;
                }
            }
        }
        list.add(bean);
        aCache.put(getCollectionKey(), (Serializable) list);
        ToastUtils.showMessage(FrameworkApplication.getContext(),"收藏成功");
    }

    private String getCollectionKey() {
        return "my_book_lists";
    }

    /**
     * 获取目录缓存
     *
     * @param mContext
     * @param bookId
     * @return
     */
    public List<BookMixAToc.mixToc.Chapters> getTocList(Context mContext, String bookId) {
        Object obj = aCache.getAsObject(getTocListKey(bookId));
        if (obj != null) {
            try {
                BookMixAToc data = (BookMixAToc) obj;
                List<BookMixAToc.mixToc.Chapters> list = data.mixToc.chapters;
                if (list != null && !list.isEmpty()) {
                    return list;
                }
            } catch (Exception e) {
                aCache.remove(getTocListKey(bookId));
            }
        }
        return null;
    }

    public void saveTocList(Context mContext, String bookId, BookMixAToc data) {
        aCache.put(getTocListKey(bookId), data);
    }

    public void removeTocList(Context mContext, String bookId) {
        aCache.remove(getTocListKey(bookId));
    }

    private String getTocListKey(String bookId) {
        return bookId + "-bookToc";
    }

    public File getChapterFile(String bookId, int chapter) {
        File file = ChapterFileUtil.getChapterFile(bookId, chapter);
        if (file != null && file.length() > 50)
            return file;
        return null;
    }

    public static void saveChapterFile(String bookId, int chapter, ChapterRead.Chapter data) {
        File file = ChapterFileUtil.getChapterFile(bookId, chapter);
        FileUtils.writeFile(file.getAbsolutePath(), formatContent(data.body), false);
    }

    /**
     * 获取缓存大小
     *
     * @return
     */
    public synchronized String getCacheSize() {
        long cacheSize = 0;

        try {
            String cacheDir = NovelConstans.BASE_PATH;
            cacheSize += FileUtils.getFolderSize(cacheDir);
            if (SdcardUtils.hasSDCard()) {
                String extCacheDir = FrameworkApplication.getContext().getExternalCacheDir().getPath();
                cacheSize += FileUtils.getFolderSize(extCacheDir);
            }
        } catch (Exception e) {
            NovelLog.e(e.toString());
        }

        return FileUtils.formatFileSizeToString(cacheSize);
    }

    /**
     * 清除缓存
     *
     * @param clearReadPos 是否删除阅读记录
     */
    public synchronized void clearCache(boolean clearReadPos, boolean clearCollect) {
        try {
            // 删除内存缓存
            String cacheDir = FrameworkApplication.getContext().getCacheDir().getPath();
            FileUtils.deleteFileOrDirectory(new File(cacheDir));
            if (SdcardUtils.hasSDCard()) {
                // 删除SD书籍缓存
                FileUtils.deleteFileOrDirectory(new File(NovelConstans.PATH_DATA));
            }
            // 删除阅读记录（SharePreference）
            if (clearReadPos) {
                //防止再次弹出性别选择框，sp要重写入保存的性别
                String chooseSex = SettingManager.getInstance().getUserChooseSex();
                SharedPreferencesUtil.getInstance().removeAll();
                SettingManager.getInstance().saveUserChooseSex(chooseSex);
            }
            // 清空书架
            if (clearCollect) {
                //CollectionsManager.getInstance().clear();
            }
            // 清除其他缓存
            aCache.clear();
        } catch (Exception e) {
            NovelLog.e(e.toString());
        }
    }



    /**
     * 格式化小说内容。
     * <p/>
     * <li>小说的开头，缩进2格。在开始位置，加入2格空格。
     * <li>所有的段落，缩进2格。所有的\n,替换为2格空格。
     *
     * @param str
     * @return
     */
    public static String formatContent(String str) {
        str = str.replaceAll("[ ]*", "");//替换来自服务器上的，特殊空格
        str = str.replaceAll("[ ]*", "");//
        str = str.replace("\n\n", "\n");
        str = str.replace("\n", "\n" + getTwoSpaces());
        str = getTwoSpaces() + str;
//        str = convertToSBC(str);
        return str;
    }
    /**
     * Return a String that only has two spaces.
     *
     * @return
     */
    public static String getTwoSpaces() {
        return "\u3000\u3000";
    }
}

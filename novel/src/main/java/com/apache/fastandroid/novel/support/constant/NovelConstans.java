package com.apache.fastandroid.novel.support.constant;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;

import com.tesla.framework.FrameworkApplication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01370340 on 2017/10/9.
 */

public class NovelConstans {
    public static final String IMG_BASE_URL = "http://statics.zhuishushenqi.com";

    public static final String API_BASE_URL = "http://api.zhuishushenqi.com/";

    public static final String SUFFIX_TXT = ".txt";
    public static final String SUFFIX_PDF = ".pdf";
    public static final String SUFFIX_EPUB = ".epub";
    public static final String SUFFIX_ZIP = ".zip";
    public static final String SUFFIX_CHM = ".chm";




    public static String PATH_DATA = createRootPath(FrameworkApplication.getContext()) + "/cache";

    public static String PATH_COLLECT = createRootPath(FrameworkApplication.getContext()) + "/collect";

    public static String PATH_TXT = PATH_DATA + "/book/";

    public static String PATH_EPUB = PATH_DATA + "/epub";

    public static String PATH_CHM = PATH_DATA + "/chm";

    public static List<String> bookTypeList = new ArrayList<String>() {{
        add(BookType.ALL);
        add(BookType.XHQH);
        add(BookType.WXXX);
        add(BookType.DSYN);
        add(BookType.LSJS);
        add(BookType.YXJJ);
        add(BookType.KHLY);
        add(BookType.CYJK);
        add(BookType.HMZC);
        add(BookType.XDYQ);
        add(BookType.GDYQ);
        add(BookType.HXYQ);
        add(BookType.DMTR);
    }};

    @Retention(RetentionPolicy.SOURCE)
    public @interface BookType {
        String ALL = "all";

        String XHQH = "xhqh";

        String WXXX = "wxxx";

        String DSYN = "dsyn";

        String LSJS = "lsjs";

        String YXJJ = "yxjj";

        String KHLY = "khly";

        String CYJK = "cyjk";

        String HMZC = "hmzc";

        String XDYQ = "xdyq";

        String GDYQ = "gdyq";

        String HXYQ = "hxyq";

        String DMTR = "dmtr";
    }

    /*public static Map<String, String> bookType = new Hashe<String, String>() {{
        put("qt", "其他");
        put(BookType.XHQH, "玄幻奇幻");
        put(BookType.WXXX, "武侠仙侠");
        put(BookType.DSYN, "都市异能");
        put(BookType.LSJS, "历史军事");
        put(BookType.YXJJ, "游戏竞技");
        put(BookType.KHLY, "科幻灵异");
        put(BookType.CYJK, "穿越架空");
        put(BookType.HMZC, "豪门总裁");
        put(BookType.XDYQ, "现代言情");
        put(BookType.GDYQ, "古代言情");
        put(BookType.HXYQ, "幻想言情");
        put(BookType.DMTR, "耽美同人");
    }};*/



    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };


    /**
     * 创建根缓存目录
     *
     * @return
     */
    public static String createRootPath(Context context) {
        String cacheRootPath = "";
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/cache
            cacheRootPath = context.getExternalCacheDir().getPath();
        } else {
            // /data/data/<application package>/cache
            cacheRootPath = context.getCacheDir().getPath();
        }
        return cacheRootPath;
    }

    public static boolean isSdCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
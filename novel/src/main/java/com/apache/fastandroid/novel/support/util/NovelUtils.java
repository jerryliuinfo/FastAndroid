package com.apache.fastandroid.novel.support.util;

import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.tesla.framework.common.util.file.FolderUtil;

import java.io.File;

/**
 * Created by 01370340 on 2017/10/27.
 */

public class NovelUtils {

    public static String getChapterPath(String bookId, int chapter) {
        return NovelConstans.PATH_TXT + bookId + File.separator + chapter + ".txt";
    }
    public static File getChapterFile(String bookId, int chapter) {
        File file = new File(getChapterPath(bookId, chapter));
        if (!file.exists()){
            FolderUtil.createFile(file);
        }
        return file;
    }
}

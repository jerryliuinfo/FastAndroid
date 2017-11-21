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


import com.apache.fastandroid.novel.support.constant.NovelConstans;
import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.common.util.SystemUtils;
import com.tesla.framework.common.util.dimen.DimensUtil;
import com.tesla.framework.common.util.sp.SPUtils;

import static com.tesla.framework.common.util.sp.SPUtils.putInt;

/**
 * @author yuyh.
 * @date 2016/9/23.
 */
public class SettingManager {

    private volatile static SettingManager manager;

    public static SettingManager getInstance() {
        return manager != null ? manager : (manager = new SettingManager());
    }

    /**
     * 保存书籍阅读字体大小
     *
     * @param bookId     需根据bookId对应，避免由于字体大小引起的分页不准确
     * @param fontSizePx
     * @return
     */
    public void saveFontSize(String bookId, int fontSizePx) {
        // 书籍对应
        putInt(getFontSizeKey(bookId), fontSizePx);
    }

    /**
     * 保存全局生效的阅读字体大小
     *
     * @param fontSizePx
     */
    public void saveFontSize(int fontSizePx) {
        saveFontSize("", fontSizePx);
    }

    public int getReadFontSize(String bookId) {
        return SPUtils.getInt(getFontSizeKey(bookId), DimensUtil.dp2px(FrameworkApplication.getContext(),16));
    }

    public int getReadFontSize() {
        return getReadFontSize("");
    }

    private String getFontSizeKey(String bookId) {
        return bookId + "-readFontSize";
    }

    public int getReadBrightness() {
        return SPUtils.getInt(getLightnessKey(),
                (int) SystemUtils.getScreenBrightness(FrameworkApplication.getContext()));
    }

    /**
     * 保存阅读界面屏幕亮度
     *
     * @param percent 亮度比例 0~100
     */
    public void saveReadBrightness(int percent) {
        putInt(getLightnessKey(), percent);
    }

    private String getLightnessKey() {
        return "readLightness";
    }

    public synchronized void saveReadProgress(String bookId, int currentChapter, int m_mbBufBeginPos, int m_mbBufEndPos) {
        putInt(getChapterKey(bookId), currentChapter);
        putInt(getStartPosKey(bookId), currentChapter);
        putInt(getEndPosKey(bookId), currentChapter);
    }

    /**
     * 获取上次阅读章节及位置
     *
     * @param bookId
     * @return
     */
    public int[] getReadProgress(String bookId) {
        int lastChapter = SPUtils.getInt(getChapterKey(bookId), 1);
        int startPos = SPUtils.getInt(getStartPosKey(bookId), 0);
        int endPos = SPUtils.getInt(getEndPosKey(bookId), 0);

        return new int[]{lastChapter, startPos, endPos};
    }


    private String getChapterKey(String bookId) {
        return bookId + "-chapter";
    }

    private String getStartPosKey(String bookId) {
        return bookId + "-startPos";
    }

    private String getEndPosKey(String bookId) {
        return bookId + "-endPos";
    }


    private String getBookMarksKey(String bookId) {
        return bookId + "-marks";
    }

    public void saveReadTheme(int theme) {
        SPUtils.putInt("readTheme", theme);
    }

    public int getReadTheme() {
        if (SPUtils.getBoolean(NovelConstans.ISNIGHT, false)) {
            return ThemeManager.NIGHT;
        }
        return SPUtils.getInt("readTheme", 3);
    }


    /**
     * 保存用户选择的性别
     *
     * @param sex male female
     */
    public void saveUserChooseSex(String sex) {
        SPUtils.putString("userChooseSex", sex);
    }

    /**
     * 获取用户选择性别
     *
     * @return
     */
    public String getUserChooseSex() {
        return SPUtils.getString("userChooseSex", NovelConstans.Gender.MALE);
    }

}

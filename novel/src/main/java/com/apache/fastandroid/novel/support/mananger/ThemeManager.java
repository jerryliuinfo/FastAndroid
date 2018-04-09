/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apache.fastandroid.novel.support.mananger;

import android.graphics.Bitmap;
import android.view.View;

import com.apache.fastandroid.novel.R;
import com.apache.fastandroid.novel.find.bean.ReadTheme;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.common.util.dimen.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuyh.
 * @date 2016/9/23.
 */
public class ThemeManager {

    public static final int NORMAL = 0;
    public static final int YELLOW = 1;
    public static final int GREEN = 2;
    public static final int LEATHER = 3;
    public static final int GRAY = 4;
    public static final int NIGHT = 5;

    public static void setReaderTheme(int theme, View view) {
        switch (theme) {
            case NORMAL:
                view.setBackgroundResource(R.drawable.theme_white_bg);
                break;
            case YELLOW:
                view.setBackgroundResource(R.drawable.theme_yellow_bg);
                break;
            case GREEN:
                view.setBackgroundResource(R.drawable.theme_green_bg);
                break;
            case LEATHER:
                view.setBackgroundResource(R.drawable.theme_leather_bg);
                break;
            case GRAY:
                view.setBackgroundResource(R.drawable.theme_gray_bg);
                break;
            case NIGHT:
                view.setBackgroundResource(R.drawable.theme_night_bg);
                break;
            default:
                break;
        }
    }

    public static Bitmap getThemeDrawable(int theme) {
        Bitmap bmp = Bitmap.createBitmap(ScreenUtil.getScreenWidth(FrameworkApplication.getContext()), ScreenUtil.getScreenHeight(), Bitmap.Config.ARGB_8888);
        switch (theme) {

            case NORMAL:
                bmp.eraseColor(ResUtil.getColor(R.color.read_theme_white));
                break;
            case YELLOW:
                bmp.eraseColor(ResUtil.getColor(R.color.read_theme_yellow));
                break;
            case GREEN:
                bmp.eraseColor(ResUtil.getColor(R.color.read_theme_green));

                break;
            case LEATHER:
                bmp.eraseColor(ResUtil.getColor(R.drawable.theme_leather_bg));

                break;
            case GRAY:
                bmp.eraseColor(ResUtil.getColor(R.color.read_theme_gray));
                break;
            case NIGHT:
                bmp.eraseColor(ResUtil.getColor(R.color.read_theme_night));
                break;
            default:
                break;
        }
        return bmp;
    }

    public static List<ReadTheme> getReaderThemeData(int curTheme) {
        int[] themes = {NORMAL, YELLOW, GREEN, LEATHER, GRAY, NIGHT};
        List<ReadTheme> list = new ArrayList<>();
        ReadTheme theme;
        for (int i = 0; i < themes.length; i++) {
            theme = new ReadTheme();
            theme.theme = themes[i];
            list.add(theme);
        }
        return list;
    }

}

package com.tesla.framework.common.util.dimen;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.common.util.sp.ActivityHelper;

import java.lang.reflect.Method;

/**
 * Created by jerryliu on 2017/6/3.
 */

public class ScreenUtil {
    private static int screenWidth;

    private static int screenHeight;

    private static float density;

    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";
    private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";
    private static final String NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width";

    private static String sNavBarOverride;

    static {
        // Android allows a system property to override the presence of the
        // navigation bar.
        // Used by the emulator.
        // See
        // https://github.com/android/platform_frameworks_base/blob/master/policy/src/com/android/internal/policy/impl/PhoneWindowManager.java#L1076
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                @SuppressWarnings("rawtypes")
                Class c = Class.forName("android.os.SystemProperties");
                @SuppressWarnings("unchecked") Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
                sNavBarOverride = null;
            }
        }
    }

    public enum NetWorkType {
        none, mobile, wifi
    }

    private static void setScreenInfo(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        density = dm.density;
    }

    public static int getScreenWidth(Context context) {
        if (screenWidth == 0)
            setScreenInfo(context);
        return screenWidth;
    }


    public static int getScreenHeight(Context context) {
        if (screenHeight == 0)
            setScreenInfo(context);
        return screenHeight;
    }
    public static int getScreenHeight() {

        return getScreenHeight(FrameworkApplication.getContext());
    }

    public static int getTitleBarHeight(Activity activity) {
        int contentTop = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        //statusBarHeight是上面所求的状态栏的高度
        int titleBarHeight = contentTop - getStatusBarHeight(activity);

        return titleBarHeight;
    }

    public static float getDensity(Context context) {
        if (density == 0.0f)
            setScreenInfo(context);
        return density;
    }


    /**
     * 获取屏幕密度
     *
     * @param context
     * @return
     */
    public static String getScreenDensityStr(final Context context) {
        String densityStr;
        final int density = context.getResources().getDisplayMetrics().densityDpi;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                densityStr = "LDPI";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                densityStr = "MDPI";
                break;
            case DisplayMetrics.DENSITY_TV:
                densityStr = "TVDPI";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                densityStr = "HDPI";
                break;
            //todo uncomment in android sdk 25
            case DisplayMetrics.DENSITY_260:
            case DisplayMetrics.DENSITY_280:
            case DisplayMetrics.DENSITY_300:
            case DisplayMetrics.DENSITY_XHIGH:
                densityStr = "XHDPI";
                break;
            //todo uncomment in android sdk 25
            case DisplayMetrics.DENSITY_340:
            case DisplayMetrics.DENSITY_360:
            case DisplayMetrics.DENSITY_400:
            case DisplayMetrics.DENSITY_420:
            case DisplayMetrics.DENSITY_XXHIGH:
                densityStr = "XXHDPI";
                break;
            case DisplayMetrics.DENSITY_560:
            case DisplayMetrics.DENSITY_XXXHIGH:
                densityStr = "XXXHDPI";
                break;
            default:
                densityStr = "other";
                break;
        }
        return densityStr;
    }




    public static int getScreenHeight(Activity paramActivity) {
        Display display = paramActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static int getKeyboardHeight(Activity paramActivity) {

        int height = getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity)
                - getAppHeight(paramActivity);
        if (height == 0) {
            height = ActivityHelper.getIntShareData(FrameworkApplication.getContext(), "KeyboardHeight", 400);
        }
        else {
            ActivityHelper.putIntShareData(FrameworkApplication.getContext(), "KeyboardHeight", height);
        }


        return height;
    }

    public static boolean isKeyBoardShow(Activity paramActivity) {
        int height = getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity)
                - getAppHeight(paramActivity);
        return height != 0;
    }

    @TargetApi(14)
    public static int getActionBarHeight(Context context) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            TypedValue tv = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
            result = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return result;
    }

    public static boolean inPortarit(Resources res) {
        return (res.getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    }

    @TargetApi(14)
    public static int getNavigationBarHeight(Context context) {
        Resources res = context.getResources();
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavigationBar(context)) {
                String key;
                if (inPortarit(res)) {
                    key = NAV_BAR_HEIGHT_RES_NAME;
                } else {
                    key = NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME;
                }
                return getInternalDimensionSize(res, key);
            }
        }
        return result;
    }

    @TargetApi(14)
    public static int getNavigationBarWidth(Context context) {
        Resources res = context.getResources();
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (hasNavigationBar(context)) {
                return getInternalDimensionSize(res, NAV_BAR_WIDTH_RES_NAME);
            }
        }
        return result;
    }

    @TargetApi(14)
    public static boolean hasNavigationBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier(SHOW_NAV_BAR_RES_NAME, "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag (see static block)
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    public static int getStatusBarHeight(Context context) {
        return getInternalDimensionSize(context.getResources(), STATUS_BAR_HEIGHT_RES_NAME);
    }

    // below actionbar, above softkeyboard
    public static int getAppContentHeight(Activity paramActivity) {
        return getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity)
                - getActionBarHeight(paramActivity) - getKeyboardHeight(paramActivity);
    }

    // below status bar,include actionbar, above softkeyboard
    public static int getAppHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.height();
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }



    /**
     * 获取屏幕的物理尺寸(包括状态栏和导航栏)
     *
     * @param context
     * @return
     */
    public static Point getScreenRealSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= 17) {
            Point point = new Point();
            display.getRealSize(point);
            return point;
        } else if (Build.VERSION.SDK_INT >= 14) {
            int width = 0;
            int height = 0;
            try {
                width = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                height = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception e) {
            }
            if (width != 0 && height != 0) {
                return new Point(width, height);
            }

        }

        Point point = new Point();
        display.getSize(point);
        return point;
    }


    public static String getResolution(final Context context) {
        Point point = getScreenRealSize(context);
        return point.x + "x" + point.y;
    }







}

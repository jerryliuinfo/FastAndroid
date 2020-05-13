package com.apache.fastandroid.aop.debounce;

import android.os.SystemClock;
import android.view.View;
import com.apache.fastandroid.util.MainLogUtil;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * author: 01370340
 * data: 2019/7/15
 * description:
 */
public class DeboucedClickPredicotor {

    public static long FROZEN_WINDOW_MILLIS = 600L;
    private static Map<View,FrozenView> sFrozenViewHashMap = new HashMap<>();

    public static boolean shouldDoClick(View targetView){
        FrozenView frozenView = sFrozenViewHashMap.get(targetView);
        if (frozenView == null){
            frozenView = new FrozenView(targetView);
            sFrozenViewHashMap.put(targetView,frozenView);
            MainLogUtil.d("第一次点击,不拦截");
            return true;
        }
        long now = SystemClock.uptimeMillis();
        if (now >= frozenView.getFrozenWindowTime()){
            frozenView.setFrozenWindowTime(now + FROZEN_WINDOW_MILLIS);
            MainLogUtil.d("非第一次点击，间隔大于 %s ms,不拦截",FROZEN_WINDOW_MILLIS);
            return true;
        }
        MainLogUtil.d("非第一次点击，但是间隔小于 %s ms,拦截",FROZEN_WINDOW_MILLIS);

        return false;
    }

    public static class FrozenView extends WeakReference<View>{

        private long frozenWindowTime;

        public FrozenView(View referent) {
            super(referent);
        }

        public void setFrozenWindowTime(long expireTime) {
            this.frozenWindowTime = expireTime;
        }

        public long getFrozenWindowTime() {
            return frozenWindowTime;
        }
    }
}

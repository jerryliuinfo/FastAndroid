package com.apache.fastandroid.aop.debounce;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.os.SystemClock;
import android.view.View;

import com.tesla.framework.common.util.log.FastLog;

/**
 * author: 01370340
 * data: 2019/7/15
 * description:
 */
public class DeboucedClickPredicotor {
    public static final String TAG = DeboucedClickPredicotor.class.getSimpleName();

    public static long FROZEN_WINDOW_MILLIS = 600L;
    private static Integer preLastTargetViewHashId;
    private static Map<Integer,FrozenView> sFrozenViewHashMap = new HashMap<>();

    public static boolean shouldDoClick(View targetView){
        int targetHashCode = targetView.hashCode();
        FrozenView frozenView = sFrozenViewHashMap.get(targetHashCode);
        FastLog.d(TAG, "DeboucedClickPredicotor shouldDoClick targetView hashCode: %s, frozenView: %s, sFrozenViewHashMap size: %s",targetView.hashCode(),frozenView,sFrozenViewHashMap.size());
        if (frozenView == null){
            frozenView = new FrozenView(targetView);
            sFrozenViewHashMap.put(targetHashCode,frozenView);
            removeView(targetHashCode);
            return true;
        }
        long now = SystemClock.uptimeMillis();
        if (now >= frozenView.getFrozenWindowTime()){
            frozenView.setFrozenWindowTime(now + FROZEN_WINDOW_MILLIS);
            FastLog.d(TAG, "非第一次点击，间隔大于 %s ms,不拦截",FROZEN_WINDOW_MILLIS);
            //移除非当前点击的View
            removeView(targetHashCode);
            return true;
        }
        FastLog.d(TAG, "非第一次点击，但是间隔小于 %s ms,拦截",FROZEN_WINDOW_MILLIS);

        return false;
    }

    /**
     *
     * @param hashCode
     */
    private static void removeView(Integer hashCode){
        Iterator<Entry<Integer, FrozenView>> iterator = sFrozenViewHashMap.entrySet().iterator();
        while (iterator.hasNext()){
            Integer key = iterator.next().getKey();
            if (key != hashCode){
                iterator.remove();
                FastLog.d(TAG, " remove hashCode: %s", hashCode);
            }
        }
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

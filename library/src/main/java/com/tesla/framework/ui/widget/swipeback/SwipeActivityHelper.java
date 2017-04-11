package com.tesla.framework.ui.widget.swipeback;

import android.os.Bundle;
import android.view.View;

import com.tesla.framework.ui.activity.BaseActivityHelper;


/**
 * Created by Jerry on 15/4/14.
 */
public class SwipeActivityHelper extends BaseActivityHelper implements SwipeBackActivityBase {

    public boolean swipeback = true;

    private SwipeBackActivityHelper mHelper;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() instanceof EnableSwipeback) {
            swipeback = ((EnableSwipeback) getActivity()).canSwipe();
        }

//        swipeback = false;
        if (swipeback) {
            mHelper = new SwipeBackActivityHelper(getActivity());
            mHelper.onActivityCreate();

            mSwipeBackLayout = getSwipeBackLayout();
            mSwipeBackLayout.addSwipeListener(new SwipeBackLayout.SwipeListener() {

                @Override
                public void onScrollStateChange(int state, float scrollPercent) {

                }

                @Override
                public void onEdgeTouch(int edgeFlag) {
                }

                @Override
                public void onScrollOverThreshold() {
                }

            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (swipeback)
            setSwipebackEdgeMode();

        setScreenOrientation();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if (swipeback)
            mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        if (swipeback) {
            View v = super.findViewById(id);
            if (v == null && mHelper != null)
                return mHelper.findViewById(id);
            return v;
        }

        return null;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(getActivity());
        getSwipeBackLayout().scrollToFinishActivity();
    }

    public void setSwipebackEdgeMode() {
        if (swipeback) {
           /* int mode = AppSettings.getSwipebackEdgeMode();
            switch (mode) {
                case 0:
                    mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
                    break;
                case 1:
                    mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
                    break;
                case 2:
                    mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_BOTTOM);
                    break;
                case 3:
                    mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_ALL);
                    break;
            }*/

            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        }
    }

    /*@Override
    protected int configTheme() {
        return ThemeUtils.themeArr[AppSettings.getThemeColor()][0];
    }*/

    private void setScreenOrientation() {
        // 开启屏幕旋转
       /* if (AppSettings.isScreenRotate()) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
        else {
            if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR)
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }*/
    }

    public interface EnableSwipeback {

        boolean canSwipe();

    }

}

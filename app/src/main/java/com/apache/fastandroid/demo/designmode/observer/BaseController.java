package com.apache.fastandroid.demo.designmode.observer;

import java.util.LinkedHashMap;
import java.util.Map;

import androidx.annotation.CallSuper;

/**
 * Created by Jerry on 2021/8/11.
 */
public class BaseController {
    //保存了所有的控制组件
    protected LinkedHashMap<IControlComponent, Boolean> mControlComponents = new LinkedHashMap<>();

    //播放器包装类，集合了MediaPlayerControl的api和IVideoController的api
    protected ControlWrapper mControlWrapper;



    public void addControlComponent(IControlComponent... component) {
        for (IControlComponent item : component) {
            addControlComponent(item, false);
        }
    }

    public void addControlComponent(IControlComponent component, boolean isDissociate) {
        mControlComponents.put(component, isDissociate);
        if (mControlWrapper != null) {
            component.attach(mControlWrapper);
        }
    }


    /**
     * 移除某个控制组件
     */
    public void removeControlComponent(IControlComponent component) {
        mControlComponents.remove(component);
    }

    /**
     * 移除所有控制组件
     */
    public void removeAllControlComponent() {
        for (Map.Entry<IControlComponent, Boolean> next : mControlComponents.entrySet()) {
        }
        mControlComponents.clear();
    }


    public void setMediaPlayer() {
        mControlWrapper = new ControlWrapper();
        //绑定ControlComponent和Controller
        for (Map.Entry<IControlComponent, Boolean> next : mControlComponents.entrySet()) {
            IControlComponent component = next.getKey();
            component.attach(mControlWrapper);
        }
    }



    /**
     *  当前播放状态
     */
    private int playState;
    /**
     * 调用此方法向控制器设置播放状态
     */
    @CallSuper
    public void setPlayState(int playState) {
        this.playState = playState;
        for (Map.Entry<IControlComponent, Boolean> next
                : mControlComponents.entrySet()) {
            IControlComponent component = next.getKey();
            component.onPlayStateChanged(playState);
        }
        onPlayStateChanged(playState);
    }


    /**
     * 子类可重写此方法并在其中更新控制器在不同播放状态下的ui
     */
    @CallSuper
    protected void onPlayStateChanged(int playState) {
        switch (playState) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}

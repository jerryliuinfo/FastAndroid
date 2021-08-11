package com.apache.fastandroid.demo.designmode.observer;

/**
 * Created by Jerry on 2021/8/11.
 */
public interface IControlComponent {

    void attach(ControlWrapper mControlWrapper);

    void onPlayStateChanged(int playState);
}

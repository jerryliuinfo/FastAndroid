package com.apache.fastandroid.artemis.mvp.rx;

import com.apache.fastandroid.artemis.mvp.BaseMvpFragment;
import com.apache.fastandroid.artemis.mvp.view.IView;

/**
 * Created by 01370340 on 2017/12/9.
 * 基于RXJava的MVPActivity封装
 */

public abstract class BaseRxMvpFragment<P extends RxPresenterNew<V>,V extends IView> extends BaseMvpFragment<P,V> {

}

package com.tesla.framework.support.action;

import android.app.Activity;

/**
 * Created by jerryliu on 2017/3/28.
 */

public class IAction {
    public static final String TAG = IAction.class.getSimpleName();

    private IAction mParent;
    private IAction mChild;
    private Activity mContext;


    public IAction(Activity context, IAction parent){
        this.mContext = context;
        this.mParent = parent;
        if (parent != null){
            parent.setChild(this);
        }
    }

    /**
     * 是否拦截
     * @return
     */
    public boolean interrupt(){
        return false;
    }


    public void run(){
        if (mParent == null || !mParent.interrupt()){
            doAction();
        }
    }

    public void doAction(){
        if (mChild != null){
            mChild.run();
        }
    }

    public void doInterrupt(){

    }

    public IAction getParent() {
        return mParent;
    }

    public void setParent(IAction mPparent) {
        this.mParent = mPparent;
    }

    public IAction getChild() {
        return mChild;
    }

    public void setChild(IAction mChild) {
        this.mChild = mChild;
    }

    public Activity getContext(){
        return mContext;
    }
}


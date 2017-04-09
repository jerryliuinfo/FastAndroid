package com.tesla.framework.support.action;

import android.app.Activity;

/**
 * Created by jerryliu on 2017/3/28.
 */

public class IAction {
    public static final String TAG = IAction.class.getCanonicalName();

    private IAction mPparent;
    private IAction mChild;
    private Activity mContext;


    public IAction(Activity context, IAction parent){
        this.mContext = context;
        this.mPparent = parent;
        if (parent != null){
            parent.setChild(this);
        }
    }

    public boolean interrupt(){
        return false;
    }


    public void run(){
        if (mPparent == null || !mPparent.interrupt()){
            doAction();
        }
    }

    public void doAction(){
        if (mChild != null){
            mChild.run();
        }
    }

    public void doInterupt(){

    }



    public IAction getPparent() {
        return mPparent;
    }

    public void setPparent(IAction mPparent) {
        this.mPparent = mPparent;
    }

    public IAction getChild() {
        return mChild;
    }

    public void setChild(IAction mChild) {
        this.mChild = mChild;
    }
}


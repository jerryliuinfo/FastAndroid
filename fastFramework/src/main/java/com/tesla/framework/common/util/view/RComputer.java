package com.tesla.framework.common.util.view;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * @author wangtianbao
 * @Description: 半径计算器
 * @date 2016/11/4 14:35
 * @copyright TCL-MIG
 */

public class RComputer {
    private float mTotalR;
    private final float mRemember;
    private float mCenterX;
    private float mCenterY;
    public RComputer(float totalR, float centerx, float centery){
        this.mTotalR=totalR;
        this.mCenterX=centerx;
        this.mCenterY=centery;
        this.mRemember=totalR;
    }

    public float getCorrectR(Paint paint){
       return getCorrectR(paint,true);
    }

    public RectF getCorrectRect(Paint paint){
        return getCorrectRect(paint,true);
    }

    public RectF getCorrectRect(Paint paint, boolean consume){
        float r=getCorrectR(paint,consume);
        return new RectF(mCenterX-r,mCenterY-r,mCenterX+r,mCenterY+r);
    }

    public RectF increaseAndGetRect(Paint paint){
        float r=increaseAndGetR(paint);
        return new RectF(mCenterX-r,mCenterY-r,mCenterX+r,mCenterY+r);
    }

    //获取剩下的颜色半径
    public float getRemain(){
        return mTotalR;
    }

    public Rect getRemainRect(){
        return  new Rect((int)(mCenterX-mTotalR),(int)(mCenterY-mTotalR),(int)(mCenterX+mTotalR),(int)(mCenterY+mTotalR));
    }

    /**
     *
     * @param paint
     * @param consume 是否消耗这个画笔距离
     * @return
     */
    public float getCorrectR(Paint paint, boolean consume){
        float strokeWidth=paint.getStrokeWidth();
        float halfWidth=strokeWidth-(int)strokeWidth/2;
        float result=mTotalR-halfWidth;
        if(consume) {
            mTotalR -= halfWidth*2;
        }
        return Math.max(result,0);
    }

    private static float ceil(float f){
        return (float) Math.ceil(f);
    }
    /**
     * 先增加相当于画笔宽度，再返回半径
     * @return
     */
    public float increaseAndGetR(Paint paint){
        float strokeWidth=ceil(paint.getStrokeWidth());
        float tmp=mTotalR;
        mTotalR+=strokeWidth;
        float r= getCorrectR(paint);
        mTotalR=tmp;
        return r;
    }

    /**
     * 消费掉一段距离，比如计算gap
     * @param d
     */
    public void consume(float d){
        mTotalR-=d;
    }

    public void increase(float d){
        mTotalR+=d;
    }

    public void reset(){
        mTotalR=mRemember;
    }
}

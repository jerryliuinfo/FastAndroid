package com.kidsedu.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.apache.fastandroid.R;
import com.base.utils.DimenUtils;
import com.tesla.framework.common.util.ResUtil;
import com.tesla.framework.common.util.log.NLog;

import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2020/11/9.
 * Icon 中间 显示下载过程中进度和下载完成的View
 */
public class DownloadCenterView extends View {
    public static final String TAG = DownloadCenterView.class.getSimpleName();

    private int mWidth, mHeight;

    private int mCurrentArcProgress;

    /**
     * 线条宽度
     */
    private int strokeWidth;

    private RectF mArcRect = new RectF();

    /**
     * 圆弧动画 Animator
     */
    ValueAnimator mArcAnimator;

    /**
     * 绘制对勾（√）的动画
     */
    private ValueAnimator mAnimatorDrawOk;

    /**
     * 是否开始绘制对勾
     */
    private boolean startDrawOk = false;


    private AnimatorSet mAnimatorSet = new AnimatorSet();

    /**
     * 下载完成状态展示
     */
    private CompleteStatus mCompleteStatus ;

    /**
     * 下载过程中展示
     */
    private DownloadingStatus mDownloadingtatus;


    /**
     * 当前下载进度
     */
    private int mDownloadProgress;

    //当前状态
    private Status mCurrentStatus;


    public DownloadCenterView(Context context) {
        this(context, null);
    }

    public DownloadCenterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownloadCenterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {

        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.DownloadStatusAttrs);
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            NLog.d(TAG, "name:" + attrs.getAttributeName(i) + "  value:" + attrs.getAttributeValue(i));
        }

        mDownloadingtatus = new DownloadingStatus();
        mCompleteStatus = new CompleteStatus();

        strokeWidth = ta.getInteger(R.styleable.DownloadStatusAttrs_download_stroke_width,DimenUtils.dp2px(8));

        mDownloadingtatus.mDownloadFinishedColor = ta.getColor(R.styleable.DownloadStatusAttrs_download_color_download_handed, ResUtil.getColor(R.color.color_download_handed));
        mDownloadingtatus.mDownloadRestColor = ta.getColor(R.styleable.DownloadStatusAttrs_download_color_download_rest, ResUtil.getColor(R.color.color_download_rest));
        mDownloadingtatus.mDownloadedProgressStartAngel = ta.getInteger(R.styleable.DownloadStatusAttrs_download_downloaded_progress_start_angel,-90);

//        mCompleteStatus.mGapDegress = ta.getInteger(R.styleable.DownloadStatusAttrs_download_gap_degress, 45);

        mDownloadingtatus.init();
        mCompleteStatus.init();

        ta.recycle();
        initAnimatorSet();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        NLog.d(TAG, "onSizeChanged w: %s, h: %s, padding left: %s, padding top: %s", getWidth(),getHeight(),getPaddingLeft(),getPaddingTop());
        mArcRect.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight() , getHeight() - getPaddingBottom());
        mCompleteStatus.initOkPath();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //下载过程中
        if (mCurrentStatus == Status.DOWNLOADING) {
            mDownloadingtatus.onDraw(canvas);
        }
        //下载完成
        else if (mCurrentStatus == Status.FINISHED) {
            mCompleteStatus.onDraw(canvas);
        }
    }



    /**
     * 设置下载过程中状态
     * @param progress
     */
    public void setDownloadingStatus(int progress){
        show();
        mCurrentStatus = Status.DOWNLOADING;
        if (progress >= 0 || progress <= 100){
            mDownloadProgress = progress;
        }
        invalidate();
    }


    /**
     * 设置下载完成状态
     */
    public void setDownloadFinishedStatus(){
        show();
        mCurrentStatus = Status.FINISHED;
        startOkAnimation();
    }

    public void startOkAnimation() {
        show();
        startDrawOk = false;
        if (mAnimatorSet.isRunning()){
            mAnimatorSet.cancel();
        }
        mAnimatorSet.start();
    }

    private void initAnimatorSet() {
        mCompleteStatus.initArcAnimation();
        mCompleteStatus.initOkPath();
        mCompleteStatus.initOkAnimation();
        mAnimatorSet.playSequentially(mArcAnimator, mAnimatorDrawOk);
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                //动画结束
                if (mAnimationListener != null){
                    mAnimationListener.animationFinish();
                }
            }
        });
    }


    public void show() {
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
    }

    public void hide() {
        setVisibility(GONE);
        cancelAnimation();
    }

    private void cancelAnimation(){
        if (mAnimatorSet.isRunning()){
            mAnimatorSet.cancel();
        }
    }


    interface IStatusView {
        void onDraw(Canvas canvas);
    }


    /**
     * 下载过程中展示
     */
    private class DownloadingStatus implements IStatusView {



        /**
         * 下载进度 已下载部分 圆弧画笔
         */
        private Paint mDownloaingArcDownloadedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        /**
         * 下载进度 剩余下载部分 圆弧画笔
         */
        private Paint mDownloadingArcRestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        /**
         * 已下载部分颜色
         */
        public int mDownloadFinishedColor;

        /**
         * 剩余下载部分颜色
         */
        public int mDownloadRestColor;


        /**
         * 已下载进度的开始角度
         */
        private int mDownloadedProgressStartAngel;


        public void init(){
            mDownloaingArcDownloadedPaint.setStyle(Style.STROKE);
            mDownloaingArcDownloadedPaint.setStrokeWidth(strokeWidth);
            mDownloaingArcDownloadedPaint.setStrokeCap(Cap.ROUND);
            mDownloaingArcDownloadedPaint.setColor(mDownloadFinishedColor);


            mDownloadingArcRestPaint.set(mDownloaingArcDownloadedPaint);
            mDownloadingArcRestPaint.setColor(mDownloadRestColor);
        }


        /**
         * 下载进度转换成圆弧角度
         * @return
         */
        private int convertDegress() {
            return (int) (mDownloadProgress / 100.f * 360);
        }


        @Override
        public void onDraw(Canvas canvas) {
            int downloadDegress = convertDegress();
            //画未下载部分
            canvas.drawArc(mArcRect, (mDownloadedProgressStartAngel + downloadDegress), (360 - downloadDegress), false, mDownloadingArcRestPaint);
            //画已下载部分
            canvas.drawArc(mArcRect, mDownloadedProgressStartAngel, downloadDegress, false, mDownloaingArcDownloadedPaint);
        }
    }


    /**
     * 下载完成展示
     */
    private class CompleteStatus implements IStatusView {
        private int mStartAngel = 135;
        /**
         * 下载完成状态 圆弧画笔
         */
        private Paint mCompleteCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        /**
         * 下载完成状态 绘制对勾（√）的画笔
         */
        private Paint mCompleteOkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        private int color = Color.parseColor("#40F132");

        /**
         * 对路径处理实现绘制动画效果
         */
        private PathEffect effect;
        /**
         * 动画执行时间
         */
        private int duration = 800;


        /**
         * 路径--用来获取对勾的路径
         */
        private Path path = new Path();
        /**
         * 取路径的长度
         */
        private PathMeasure pathMeasure;

        /**
         * 缺口角度
         */
        public int mGapDegress = 45; //完成下载状态的缺口

        public void init(){
            mCompleteCirclePaint.setStyle(Style.STROKE);
            mCompleteCirclePaint.setStrokeWidth(strokeWidth);
            mCompleteCirclePaint.setStrokeCap(Cap.ROUND);
            mCompleteCirclePaint.setColor(color);

            mCompleteOkPaint.set(mCompleteCirclePaint);
        }


        /**
         * 绘制对勾
         */
        private void initOkPath() {
            path.reset();
            //对勾的路径
            path.moveTo(mWidth / 8 * 3, mHeight / 2);
            path.lineTo(mWidth / 2, mHeight / 5 * 3);
            path.lineTo(mWidth / 3 * 2, mHeight / 5 * 2);

            pathMeasure = new PathMeasure(path, true);
        }

        public void initArcAnimation() {
            mArcAnimator = ValueAnimator.ofInt(0, (360 - mGapDegress));
            mArcAnimator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mCurrentArcProgress = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mArcAnimator.setDuration(1000);
        }


        public void initOkAnimation() {
            mAnimatorDrawOk = ValueAnimator.ofFloat(1, 0);
            mAnimatorDrawOk.setDuration(duration);
            mAnimatorDrawOk.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    startDrawOk = true;
                    float value = (Float) animation.getAnimatedValue();

                    effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                    mCompleteStatus.mCompleteOkPaint.setPathEffect(effect);
                    invalidate();
                }
            });
        }

        @Override
        public void onDraw(Canvas canvas) {
            canvas.drawArc(mArcRect, mStartAngel, mCurrentArcProgress, false, mCompleteCirclePaint);
            if (startDrawOk) {
                canvas.drawPath(path, mCompleteOkPaint);
            }
        }
    }

    private IAnimationListener mAnimationListener;

    public void setDownloadStatusListener(IAnimationListener animationListener) {
        this.mAnimationListener = animationListener;
    }

    public interface IAnimationListener{
        /**
         * 动画完成回调
         */
        void animationFinish();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAnimation();
    }
}

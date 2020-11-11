package com.kidsedu.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.apache.fastandroid.R;
import com.base.utils.DimenUtils;
import com.kidsedu.ui.widget.DownloadCenterView.IAnimationListener;
import com.tesla.framework.common.util.handler.HandlerUtil;
import com.tesla.framework.common.util.log.NLog;

import androidx.annotation.Nullable;

/**
 * Created by Jerry on 2020/11/9.
 */
public class DownloadStatusView extends FrameLayout {
    public static final String TAG = DownloadStatusView.class.getSimpleName();

    //当前状态
    private Status mCurrentStatus;
    private Paint mNormalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Context mContext;

    //等待下载
    private ImageView mWaintingIcon;
    //删除按钮
    private ImageView mDeleteView;

    //下载完成
    private DownloadCenterView mCompleteView;

    private View mMaskView;


    private IDownloadStatusListener mListener;

    /**
     * 橡皮擦Bitmap
     */
    private Bitmap mEraserBitmap;
    /**
     * 橡皮擦Cavas
     */
    private Canvas mEraserCanvas;
    /**
     * 蒙层背景画笔
     */
    private  Paint mFullingPaint;

    public DownloadStatusView(Context context) {
        this(context, null);
    }

    private int maskColor = Color.parseColor("#7F000000");

    public DownloadStatusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

        mFullingPaint = new Paint();
        mFullingPaint.setColor(maskColor);
//        mFullingPaint.setAlpha(150);
    }

    /*@Override
    protected void dispatchDraw(Canvas canvas) {
        final long drawingTime = getDrawingTime();
        try {
            View child;
            for (int i = 0; i < getChildCount(); i++) {
                child = getChildAt(i);
                drawChild(canvas, child, drawingTime);
            }
        } catch (NullPointerException e) {

        }
    }*/

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        NLog.d(TAG, "dispatchDraw status: %s", mCurrentStatus);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        NLog.d(TAG, "onDraw DownloadStatusView");
//        canvas.drawColor(Color.parseColor("#7F000000"));

//        canvas.save();
//        canvas.clipRect(100,100,400,700);
//        canvas.drawColor(Color.parseColor("#FF0000"));
//        canvas.restore();




    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mEraserBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mEraserCanvas = new Canvas(mEraserBitmap);
    }

    public DownloadStatusView(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init(attrs);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_status_image_view, this);
        mWaintingIcon = rootView.findViewById(R.id.iv_waiting);
        mDeleteView = rootView.findViewById(R.id.icon_delete);

        mMaskView  = rootView.findViewById(R.id.maskView);

        mDeleteView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NLog.d(TAG, "mDeleteView onClick");
                if (mListener != null) {
                    mListener.onPauseDownload();
//                    ToastUtils.showToast(context, "点击暂停下载");
                }

                //隐藏中间进度
                hideDeleteAndWaiting();
               //Wink.get().stop(((KidsResource) iKidsResource).getKey());
            }
        });
        mCompleteView = rootView.findViewById(R.id.iv_download_complete);
        mCompleteView.setDownloadStatusListener(new IAnimationListener() {
            @Override
            public void animationFinish() {
                if (mListener != null) {
                    mListener.animationFinish();
                }
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDownloadingStatus() || isWaitingStatus()){
                    mDeleteView.setVisibility(VISIBLE);

                    HandlerUtil.getUIHandler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideDeleteIcon();
                        }
                    }, 3000);

                }
            }
        });

    }


    /**
     * 下载过程中 或者 等待下载 过程中 右上角显示删除按钮
     */
    /*@Override
    public void onStopClick() {
        mDeleteView.setVisibility(VISIBLE);
        HandlerUtil.getUIHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideDeleteIcon();
            }
        }, 3000);
    }*/

/*    @Override
    public void onStatusChange(DownloadInfo downloadInfo, byte status) {
        byte buttonStatus = dataModel.getStatus(iKidsResource, downloadInfo);
        switch (buttonStatus) {
            case ButtonStatus.VIDEO:
            case ButtonStatus.WEB:
            case ButtonStatus.DOWNLOAD:
            case ButtonStatus.DOWNLOAD_PAUSE:
            case ButtonStatus.OPEN:
                // todo 正常显示， gone掉加载view
                break;
            case ButtonStatus.WAIT:
                // todo 显示WAIT状态
                setWaitingStatus();
                break;
            case ButtonStatus.DOWNLOAD_FAILED:

            case ButtonStatus.DOWNLOADING:
                // TODO: 显示下载状态
                setProgress(downloadInfo.getDownloadProgress());
                setDownloadingStatus(downloadInfo.getDownloadProgress());
                break;
            case ButtonStatus.INSTALL:

                if (0 < lastTimeProgress && lastTimeProgress < 100) {
                    setDownloadFinishedStatus();
                } else {
                    hideDeleteAndWaiting();
                }
                break;
        }
        lastTimeProgress = downloadInfo.getDownloadProgress();
    }*/


    /**
     * 设置等待下载状态
     */
    public void setWaitingStatus() {
        updateDownloadStatus(Status.WAITING);
        //隐藏
        mCompleteView.hide();
        mWaintingIcon.setVisibility(VISIBLE);
        mMaskView.setVisibility(VISIBLE);
    }


    /**
     * 设置下载过程中状态
     *
     * @param progress
     */
    public void setDownloadingStatus(int progress) {
        updateDownloadStatus(Status.DOWNLOADING);
        if (progress < 0 || progress > 100) {
            throw new IllegalArgumentException("非法进度值");
        }
        mWaintingIcon.setVisibility(GONE);
        mCompleteView.setDownloadingStatus(progress);
        mMaskView.setVisibility(VISIBLE);
    }

    /**
     * 设置下载完成状态
     */
    public void setDownloadFinishedStatus() {
        updateDownloadStatus(Status.FINISHED);
        hideDeleteAndWaiting();
        mCompleteView.setDownloadFinishedStatus();
        mMaskView.setVisibility(GONE);
    }

    /**
     * 下载过程中调用
     *
     * @param downloadStatus
     * @param downloadStatus
     */
    private void updateDownloadStatus(Status downloadStatus) {
        this.mCurrentStatus = downloadStatus;
    }

    private void init(AttributeSet attrs) {
        mContext = getContext();
        mNormalPaint.setStyle(Style.STROKE);
        mNormalPaint.setColor(Color.WHITE);
        mNormalPaint.setStrokeWidth(DimenUtils.dp2px(mContext, 5));
        mNormalPaint.setStrokeCap(Cap.ROUND);
    }


    private void hideDeleteIcon() {
        if (mDeleteView.getVisibility() == VISIBLE) {
            mDeleteView.setVisibility(GONE);
        }
//        mMaskView.setVisibility(GONE);
    }

    private void hideWaitingIcon() {
        if (mWaintingIcon.getVisibility() == VISIBLE) {
            mWaintingIcon.setVisibility(GONE);
        }
    }

    private void hideDeleteAndWaiting() {
        hideDeleteIcon();
        hideWaitingIcon();
        mCompleteView.hide();

    }

    private boolean isDownloadingStatus() {
        return mCurrentStatus == Status.DOWNLOADING;
    }

    private boolean isWaitingStatus() {
        return mCurrentStatus == Status.WAITING;
    }


    private boolean isFinishStatus() {
        return mCurrentStatus == Status.FINISHED;
    }

    public interface IDownloadStatusListener {

        /**
         * 点击了右上角的x 按钮暂停下载
         */
        void onPauseDownload();

        /**
         * 动画完成回调
         */
        void animationFinish();
    }

    public void setDownloadStatusListener(IDownloadStatusListener iCallback) {
        this.mListener = iCallback;
    }
}

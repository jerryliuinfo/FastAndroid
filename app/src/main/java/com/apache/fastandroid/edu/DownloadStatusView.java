package com.apache.fastandroid.edu;

import android.content.Context;
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
import com.apache.fastandroid.edu.DownloadCenterView.IAnimationListener;
import com.tesla.framework.common.util.dimen.DimensUtil;
import com.tesla.framework.common.util.handler.HandlerUtil;
import com.tesla.framework.common.util.log.NLog;
import com.tesla.framework.ui.widget.ToastUtils;

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

//    private int mDownloadProgress;
    //等待下载
    private ImageView mWaintingIcon;
    //删除按钮
    private ImageView mDeleteView;

    //下载完成
    private DownloadCenterView mCompleteView;





    private IDownloadStatusListener mListener;

    public DownloadStatusView(Context context) {
        this(context,null);
    }

    public DownloadStatusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DownloadStatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_status_image_view,this);
        mWaintingIcon = rootView.findViewById(R.id.iv_waiting);
        mDeleteView =  rootView.findViewById(R.id.icon_delete);

        mDeleteView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                NLog.d(TAG, "mDeleteView onClick");
                if (mListener != null){
                    mListener.onPauseDownload();
                    ToastUtils.showSingleToast("点击暂停下载");
                }
                //隐藏中间进度
                hideDeleteAndWaiting();

            }
        });
        mCompleteView = rootView.findViewById(R.id.iv_download_complete);
        mCompleteView.setDownloadStatusListener(new IAnimationListener() {
            @Override
            public void animationFinish() {
                if (mListener != null){
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
     * 设置等待下载状态
     */
    public void setWaitingStatus(){
        updateDownloadStatus(Status.WAITING);
        //隐藏
        mCompleteView.hide();
        mWaintingIcon.setVisibility(VISIBLE);
    }


    /**
     * 设置下载过程中状态
     * @param progress
     */
    public void setDownloadingStatus(int progress){
        updateDownloadStatus(Status.DOWNLOADING);
        if (progress < 0 || progress > 100){
            throw new IllegalArgumentException("非法进度值");
        }
        mWaintingIcon.setVisibility(GONE);
        mCompleteView.setDownloadingStatus(progress);
    }

    /**
     * 设置下载完成状态
     */
    public void setDownloadFinishedStatus(){
        updateDownloadStatus(Status.FINISHED);
        hideDeleteAndWaiting();
        mCompleteView.setDownloadFinishedStatus();
    }

    /**
     * 下载过程中调用
     * @param downloadStatus
     * @param downloadStatus
     */
    private void updateDownloadStatus(Status downloadStatus) {
        this.mCurrentStatus = downloadStatus;
    }

    private void init(AttributeSet attrs){
        mContext = getContext();
        mNormalPaint.setStyle(Style.STROKE);
        mNormalPaint.setColor(Color.WHITE);
        mNormalPaint.setStrokeWidth(DimensUtil.dp2px(mContext,5));
        mNormalPaint.setStrokeCap(Cap.ROUND);
    }



    private void hideDeleteIcon(){
        if (mDeleteView.getVisibility() == VISIBLE){
            mDeleteView.setVisibility(GONE);
        }
    }

    private void hideWaitingIcon(){
        if (mWaintingIcon.getVisibility() == VISIBLE){
            mWaintingIcon.setVisibility(GONE);
        }
    }

    private void hideDeleteAndWaiting(){
        hideDeleteIcon();
        hideWaitingIcon();
    }

    private boolean isDownloadingStatus(){
        return mCurrentStatus == Status.DOWNLOADING;
    }

    private boolean isWaitingStatus(){
        return mCurrentStatus == Status.WAITING;
    }


    private boolean isFinishStatus(){
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

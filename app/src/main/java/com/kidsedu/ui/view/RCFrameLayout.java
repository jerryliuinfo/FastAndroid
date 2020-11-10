

package com.kidsedu.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.apache.fastandroid.R;


/**
 * 圆角相对布局
 *
 */
public class RCFrameLayout extends FrameLayout {


    public float[] radii = new float[8];   // top-left, top-right, bottom-right, bottom-left
    public Path mClipPath;                 // 剪裁区域路径
    public Paint mPaint;                   // 画笔
    public boolean mRoundAsCircle = false; // 圆形
    public int mDefaultStrokeColor;        // 默认描边颜色
    public int mStrokeColor;               // 描边颜色
    public ColorStateList mStrokeColorStateList;// 描边颜色的状态
    public int mStrokeWidth;               // 描边半径
    public boolean mClipBackground;        // 是否剪裁背景
    public Region mAreaRegion;             // 内容区域
    public RectF mLayer;                   // 画布图层大小

    public RCFrameLayout(Context context) {
        this(context, null);
    }

    public RCFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RCFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        onSizeChanged(this, w, h);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.saveLayer(mLayer, null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        onClipDraw(canvas);
        canvas.restore();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mClipBackground) {
            canvas.saveLayer(mLayer, null, Canvas.ALL_SAVE_FLAG);
            super.draw(canvas);
            clipLayout(canvas);
            canvas.restore();
        } else {
            super.draw(canvas);
        }
    }

    private void clipLayout(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawPath(mClipPath, mPaint);
        } else {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

            final Path path = new Path();
            path.addRect(-1, -1, (int) mLayer.width()+2, (int) mLayer.height()+2, Path.Direction.CW);
            path.op(mClipPath, Path.Op.DIFFERENCE);
            canvas.drawPath(path, mPaint);
        }
    }



    public void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RCAttrs);
        mRoundAsCircle = ta.getBoolean(R.styleable.RCAttrs_round_as_circle, false);
        mStrokeColorStateList = ta.getColorStateList(R.styleable.RCAttrs_stroke_color);
        if (null != mStrokeColorStateList) {
            mStrokeColor = mStrokeColorStateList.getDefaultColor();
            mDefaultStrokeColor = mStrokeColorStateList.getDefaultColor();
        } else {
            mStrokeColor = Color.WHITE;
            mDefaultStrokeColor = Color.WHITE;
        }
        mStrokeWidth = ta.getDimensionPixelSize(R.styleable.RCAttrs_stroke_width, 0);
        mClipBackground = ta.getBoolean(R.styleable.RCAttrs_clip_background, false);
        int roundCorner = ta.getDimensionPixelSize(R.styleable.RCAttrs_round_corner, 0);
        int roundCornerTopLeft = ta.getDimensionPixelSize(
                R.styleable.RCAttrs_round_corner_top_left, roundCorner);
        int roundCornerTopRight = ta.getDimensionPixelSize(
                R.styleable.RCAttrs_round_corner_top_right, roundCorner);
        int roundCornerBottomLeft = ta.getDimensionPixelSize(
                R.styleable.RCAttrs_round_corner_bottom_left, roundCorner);
        int roundCornerBottomRight = ta.getDimensionPixelSize(
                R.styleable.RCAttrs_round_corner_bottom_right, roundCorner);
        ta.recycle();

        radii[0] = roundCornerTopLeft;
        radii[1] = roundCornerTopLeft;

        radii[2] = roundCornerTopRight;
        radii[3] = roundCornerTopRight;

        radii[4] = roundCornerBottomRight;
        radii[5] = roundCornerBottomRight;

        radii[6] = roundCornerBottomLeft;
        radii[7] = roundCornerBottomLeft;

        mLayer = new RectF();
        mClipPath = new Path();
        mAreaRegion = new Region();
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    public void onSizeChanged(View view, int w, int h) {
        mLayer.set(0, 0, w, h);
        refreshRegion(view);
    }

    public void refreshRegion(View view) {
        int w = (int) mLayer.width();
        int h = (int) mLayer.height();
        RectF areas = new RectF();
        areas.left = view.getPaddingLeft();
        areas.top = view.getPaddingTop();
        areas.right = w - view.getPaddingRight();
        areas.bottom = h - view.getPaddingBottom();
        mClipPath.reset();
        if (mRoundAsCircle) {
            float d = areas.width() >= areas.height() ? areas.height() : areas.width();
            float r = d / 2;
            PointF center = new PointF(w / 2, h / 2);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
                mClipPath.addCircle(center.x, center.y, r, Path.Direction.CW);

                mClipPath.moveTo(0, 0);  // 通过空操作让Path区域占满画布
                mClipPath.moveTo(w, h);
            } else {
                float y = h / 2 - r;
                mClipPath.moveTo(areas.left, y);
                mClipPath.addCircle(center.x, y + r, r, Path.Direction.CW);
            }
        } else {
            mClipPath.addRoundRect(areas, radii, Path.Direction.CW);
        }
        Region clip = new Region((int) areas.left, (int) areas.top,
                (int) areas.right, (int) areas.bottom);
        mAreaRegion.setPath(mClipPath, clip);
    }

    public void onClipDraw(Canvas canvas) {
        if (mStrokeWidth > 0) {
            // 支持半透明描边，将与描边区域重叠的内容裁剪掉
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            mPaint.setColor(Color.WHITE);
            mPaint.setStrokeWidth(mStrokeWidth * 2);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mClipPath, mPaint);
            // 绘制描边
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            mPaint.setColor(mStrokeColor);
            mPaint.setStyle(Paint.Style.STROKE);
            canvas.drawPath(mClipPath, mPaint);
        }
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1) {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            canvas.drawPath(mClipPath, mPaint);
        } else {
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

            final Path path = new Path();
            //扩大面积
            path.addRect(-1, -1, (int) mLayer.width()+2, (int) mLayer.height()+2, Path.Direction.CW);
            path.op(mClipPath, Path.Op.DIFFERENCE);
            canvas.drawPath(path, mPaint);
        }
    }


    @Override
    public void invalidate() {

        refreshRegion(this);
        super.invalidate();
    }


}

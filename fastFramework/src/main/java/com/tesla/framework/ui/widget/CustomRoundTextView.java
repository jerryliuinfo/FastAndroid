package com.tesla.framework.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import com.tesla.framework.R;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.view.ViewCompat;

/**
 * Created by Jerry on 2021/4/22.
 */
public class CustomRoundTextView extends AppCompatTextView {
    private  int selectorNormalColor,selectorPressedColor,selectorDisableColor,selectorSelectedColor;
    float radius,topLeftRadius,topRightRadius,bottomLeftRadius,bottomRightRadius;
    private int strokeWidth = -1;
    private int strokeColor = -1;
    private float strokeDashWidth = 0.0f;
    private float strokeDashGap = 0.0f;

    public CustomRoundTextView(Context context) {
        this(context, null);
    }

    public CustomRoundTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRoundTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundTextView, defStyleAttr, 0);

        if (attributes != null) {
            strokeWidth = attributes.getDimensionPixelSize(R.styleable.RoundTextView_rv_strokeWidth, 0);
            strokeColor = attributes.getColor(R.styleable.RoundTextView_rv_strokeColor, Color.BLACK);

            radius = attributes.getDimension(R.styleable.RoundTextView_rv_cornerRadius, 0);
            topLeftRadius = attributes.getDimension(R.styleable.RoundTextView_rv_cornerRadius_TL, 0);
            topRightRadius = attributes.getDimension(R.styleable.RoundTextView_rv_cornerRadius_TR, 0);
            bottomLeftRadius = attributes.getDimension(R.styleable.RoundTextView_rv_cornerRadius_BL, 0);
            bottomRightRadius = attributes.getDimension(R.styleable.RoundTextView_rv_cornerRadius_BR, 0);


            selectorNormalColor = attributes.getColor(R.styleable.RoundTextView_rv_backgroundColor, Color.WHITE);
            selectorPressedColor = attributes.getColor(R.styleable.RoundTextView_rv_backgroundPressColor, Color.WHITE);
            selectorDisableColor = attributes.getColor(R.styleable.RoundTextView_rv_backgroundDisabledColor, Color.WHITE);
            selectorSelectedColor = attributes.getColor(R.styleable.RoundTextView_rv_backgroundSelectedColor, Color.WHITE);

            attributes.recycle();

            into();
        }
    }

    /**
     * 设置边框  宽度  颜色  虚线  间隙
     */
    private void setBorder( GradientDrawable gradientDrawable) {
        if (strokeWidth >= 0) {
            gradientDrawable.setStroke(strokeWidth, strokeColor, strokeDashWidth, strokeDashGap);
        }
    }



    /**
     * 设置Selector的不同状态的颜色
     *
     * @param state 按钮状态
     */
    private void setSelectorColor(GradientDrawable gradientDrawable, int state) {
        if (state == android.R.attr.state_pressed){
            gradientDrawable.setColor(selectorPressedColor);
        }else if (state == android.R.attr.state_selected){
            gradientDrawable.setColor(selectorSelectedColor);
        }else if (state == -android.R.attr.state_enabled){
            gradientDrawable.setColor(selectorDisableColor);
        }else if (state == android.R.attr.state_enabled){
            gradientDrawable.setColor(selectorNormalColor);
        }
    }


    private GradientDrawable getDrawable(int state){
        GradientDrawable gd = new GradientDrawable();//创建drawable
        setBorder(gd);
        setRadius(gd);
        setSelectorColor(gd,state);
        return gd;
    }

    public void into(){
        ViewCompat.setBackground(this, getStateListDrawable());
    }

    private StateListDrawable getStateListDrawable(){
        StateListDrawable stateListDrawable = new StateListDrawable();

        stateListDrawable.addState(new int[]{-android.R.attr.state_selected},getDrawable(android.R.attr.state_enabled));//负值表示false

        stateListDrawable.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, getDrawable(android.R.attr.state_pressed));

        stateListDrawable.addState(new int[]{android.R.attr.state_selected},getDrawable(android.R.attr.state_selected));

        stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, getDrawable(-android.R.attr.state_enabled));
        stateListDrawable.addState(new int[]{}, getDrawable(android.R.attr.state_enabled));
        return stateListDrawable;
    }

    private void setRadius(GradientDrawable gd) {
        if (radius != 0 ) {
            gd.setCornerRadius(radius);
        } else {
            float[] radii = new float[]{
                    topLeftRadius, topLeftRadius,
                    topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius,
                    bottomLeftRadius, bottomLeftRadius
            };
            gd.setCornerRadii(radii);
        }
    }

    public void setNormalColor(@ColorInt int color) {
       this.selectorNormalColor = color;

    }

    public void setPressedColor(@ColorInt int color) {
        this.selectorPressedColor = color;
    }

    public void setRadius(float radius){
        this.radius = radius;
    }

    public void setTopLeftRadius(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
    }

    public void setTopRightRadius(float topRightRadius) {
        this.topRightRadius = topRightRadius;
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
    }
}


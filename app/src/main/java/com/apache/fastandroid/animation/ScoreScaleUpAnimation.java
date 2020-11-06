package com.apache.fastandroid.animation;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.Transformation;

public class ScoreScaleUpAnimation extends Animation {

    private int centerX;
    private int centerY;

    @Override
    public void initialize(int width, int height, int parentWidth,
                           int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        centerX = width / 2;
        centerY = height / 2;
        setDuration(300);
        setInterpolator(new CycleInterpolator(0.5f));
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix = t.getMatrix();
        matrix.preScale(interpolatedTime / 3 + 1, interpolatedTime / 3 + 1);
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}

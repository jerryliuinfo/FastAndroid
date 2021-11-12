package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

import androidx.annotation.Nullable;

public class Practice07MatrixTranslateView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 100);
    Point point2 = new Point(600, 100);
    Point point3 = new Point(1000, 100);
    Matrix matrix = new Matrix();

    public Practice07MatrixTranslateView(Context context) {
        super(context);
    }

    public Practice07MatrixTranslateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice07MatrixTranslateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap,point1.x,point1.y,paint);

        canvas.save();
        matrix.reset();
        matrix.postTranslate(-100,0);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap,point2.x,point2.y,paint);

        canvas.restore();


        canvas.save();
        matrix.reset();
        matrix.postTranslate(200,0);
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap,point3.x,point3.y,paint);

        canvas.restore();
    }
}
package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

import androidx.annotation.Nullable;

public class Practice11CameraRotateView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 100);
    Point point2 = new Point(600, 100);
    Point point3 = new Point(1300, 100);
    Camera camera = new Camera();

    public Practice11CameraRotateView(Context context) {
        super(context);
    }

    public Practice11CameraRotateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11CameraRotateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        camera.save();
        camera.rotateX(30);
        camera.applyToCanvas(canvas);

        camera.restore();

        canvas.drawBitmap(bitmap,point2.x,point2.y,paint);

        canvas.restore();


        int point3CenterX = point3.x + bitmap.getWidth() / 2;
        int point3CenterY = point3.y + bitmap.getHeight() / 2;

        canvas.save();
        camera.save();
        camera.rotateX(30);

        //旋转之后把投影移动回来
        canvas.translate(point3CenterX,point3CenterY);

        camera.applyToCanvas(canvas);
        //旋转之前把绘制内容移动到轴心（原点）
        canvas.translate(-point3CenterX,-point3CenterY);

        camera.restore();

        canvas.drawBitmap(bitmap,point3.x,point3.y,paint);

        canvas.restore();

    }
}

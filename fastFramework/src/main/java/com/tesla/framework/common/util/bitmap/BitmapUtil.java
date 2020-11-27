package com.tesla.framework.common.util.bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.tesla.framework.common.util.log.NLog;

public class BitmapUtil {

	private static final String TAG = BitmapUtil.class.getSimpleName();





	public static Bitmap setImageCorner(Bitmap source, float roundPx) {

		Bitmap result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(result);

		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setXfermode(null);
		paint.setAlpha(255);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(source, 0, 0, paint);

		source.recycle();
		return result;
	}



















	public static Bitmap zoomBitmap(Bitmap source, int width) {
		float scale = width * 1.0f / source.getWidth();
		return zoomBitmap(source,scale);
	}

	/**
	 * 按比例缩放图片
	 *
	 * @param source
	 * @param scale
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap source, float scale) {
		int bitmapWidth = source.getWidth();
		int bitmapHeight = source.getHeight();

		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap newBitmap = Bitmap.createBitmap(source, 0, 0, bitmapWidth, bitmapHeight, matrix, true);
		return newBitmap;
	}








	
	public static Bitmap rotateBitmap(Bitmap source, int degrees) {
		Matrix matrix = new Matrix();
		matrix.setRotate(degrees, source.getWidth() / 2, source.getHeight() / 2);
		Bitmap result = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
		try {
			NLog.d(
					TAG,
					String.format("rotate bitmap, source(%d,%d) result(%d,%d)", source.getWidth(), source.getHeight(), result.getWidth(),
							result.getHeight()));
		} catch (Exception e) {
		}
		source.recycle();
		return result;
	}

	public static Bitmap getFromDrawableAsBitmap(Context context, String resName) {
		try {
			String packageName = context.getPackageName();
			Resources resources = context.getPackageManager().getResourcesForApplication(packageName);

			int resId = resources.getIdentifier(resName, "drawable", packageName);

			try {
				if (resId != 0)
					return BitmapFactory.decodeResource(context.getResources(), resId);
				else{

				}
					NLog.e("",String.format("配置的图片ResourceId=%s不存在", resName));
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 目前只支持JPG图片
	 * 
	 * @param resName
	 * @return
	 */
	public static InputStream getFromDrawableAsStream(Context context, String resName) {
		try {
			String packageName = context.getPackageName();
			Resources resources = context.getPackageManager().getResourcesForApplication(packageName);

			int resId = resources.getIdentifier(resName, "drawable", packageName);

			if (resId != 0)
				return Bitmap2InputStream(BitmapFactory.decodeResource(resources, resId));
			else
				NLog.e("",String.format("配置的图片ResourceId=%s不存在", resName));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 将byte[]转换成InputStream
	public static InputStream Byte2InputStream(byte[] b) {
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		return bais;
	}

	// 将InputStream转换成byte[]
	public static byte[] InputStream2Bytes(InputStream is) {
		String str = "";
		byte[] readByte = new byte[1024];
		@SuppressWarnings("unused")
		int readCount = -1;
		try {
			while ((readCount = is.read(readByte, 0, 1024)) != -1) {
				str += new String(readByte).trim();
			}
			return str.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 将Bitmap转换成InputStream
	public static InputStream Bitmap2InputStream(Bitmap bm) {
		return bitmap2InputStream(bm,100);
	}

	// 将Bitmap转换成InputStream
	public static InputStream bitmap2InputStream(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	// 将InputStream转换成Bitmap
	public static Bitmap InputStream2Bitmap(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}







	// Drawable转换成InputStream
	public static InputStream Drawable2InputStream(Drawable d) {
		Bitmap bitmap = drawable2Bitmap(d);
		return Bitmap2InputStream(bitmap);
	}

	// InputStream转换成Drawable
	public static Drawable InputStream2Drawable(Context context, InputStream is) {
		Bitmap bitmap = InputStream2Bitmap(is);
		return bitmap2Drawable(context, bitmap);
	}







	// Drawable转换成byte[]
	public static byte[] Drawable2Bytes(Drawable d) {
		Bitmap bitmap = drawable2Bitmap(d);
		return Bitmap2Bytes(bitmap);
	}

	// byte[]转换成Drawable
	public static Drawable Bytes2Drawable(Context context, byte[] b) {
		Bitmap bitmap = Bytes2Bitmap(b);
		return bitmap2Drawable(context, bitmap);
	}

	// Bitmap转换成byte[]
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	// byte[]转换成Bitmap
	public static Bitmap Bytes2Bitmap(byte[] b) {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}
		return null;
	}

	// Drawable转换成Bitmap
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(w, h, drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	// Bitmap转换成Drawable
	public static Drawable bitmap2Drawable(Context context, Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
		Drawable d = (Drawable) bd;
		return d;
	}

}

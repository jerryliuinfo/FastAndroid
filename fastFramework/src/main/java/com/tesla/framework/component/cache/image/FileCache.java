// SPDX-License-Identifier: GPL-3.0-or-later

package com.tesla.framework.component.cache.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class FileCache {
    private static final long sLastModifiedDate = System.currentTimeMillis() - 604_800_000;

    private final File mCacheDir;

    public FileCache() {
        if (Utils.getApp().getExternalCacheDir() != null) {
            mCacheDir = new File(Utils.getApp().getExternalCacheDir(), "images");
        } else {
            mCacheDir = new File(Utils.getApp().getCacheDir(), "images");
        }

        if (!mCacheDir.exists()) mCacheDir.mkdirs();
    }

    public void putImage(String name, InputStream inputStream) throws IOException {
        File iconFile = getImageFile(name);
        try (OutputStream os = new FileOutputStream(iconFile)) {
//            FileUtils.copy(inputStream, os);
        }
    }

    public void putImage(String name, Drawable drawable) throws IOException {
        putImage(name, ImageUtils.drawable2Bitmap(drawable));
    }

    public void putImage(String name, Bitmap bitmap) throws IOException {
        File iconFile = getImageFile(name);
        try (OutputStream os = new FileOutputStream(iconFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
        }
    }

    @Nullable
    public Bitmap getImage(@NonNull String name) {
        File iconFile = getImageFile(name);
        if (iconFile.lastModified() >= sLastModifiedDate) {
            try (FileInputStream fis = new FileInputStream(iconFile)) {
                return BitmapFactory.decodeStream(fis);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    @Nullable
    public Drawable getImageDrawable(@NonNull String name) {
        File iconFile = getImageFile(name);
        if (iconFile.lastModified() >= sLastModifiedDate) {
            try (FileInputStream fis = new FileInputStream(iconFile)) {
                return Drawable.createFromStream(fis, name);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    @NonNull
    private File getImageFile(@NonNull String name) {
        return new File(mCacheDir, name + ".png");
    }

    public void clear() {
        File[] files = mCacheDir.listFiles();
        if (files == null) return;
        int count = 0;
        for (File f : files) {
            if (f.lastModified() < sLastModifiedDate) {
                if (f.delete()) ++count;
            }
        }
        Log.d("Cache", "Deleted " + count + " images.");
    }
}

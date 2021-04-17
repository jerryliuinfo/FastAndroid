package com.tesla.framework.common.util.file;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.blankj.utilcode.util.PathUtils;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.common.util.FrameworkLogUtil;
import com.tesla.framework.common.util.ZipUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by jerryliu on 2017/8/3.
 */

public class AssetUtil {
    public static final String TAG = AssetUtil.class.getSimpleName();

    public static String readAssetsFile(String fileName, Context context) {
        StringBuffer sb = new StringBuffer();
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String readLine = null;
            while ((readLine = reader.readLine()) != null) {
                sb.append(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }














    /**
     * Comparator of files.
     */
    public interface FileComparator {
        boolean equals(File lhs, File rhs);
    }

    /**
     * Simple file comparator which only depends on file length and modification time.
     */
    public final static FileComparator SIMPLE_COMPARATOR = new FileComparator() {
        @Override
        public boolean equals(File lhs, File rhs) {
            return (lhs.length() == rhs.length()) && (lhs.lastModified() == rhs.lastModified());
        }
    };



    /**
     * Comparator of asset and target file.
     */
    public interface AssetFileComparator {
         boolean equals(Context context, String assetPath, File dstFile);
    }

    /**
     * Simple asset file comparator which only depends on asset file length.
     */
    public final static AssetFileComparator SIMPLE_ASSET_COMPARATOR = new AssetFileComparator() {
        @Override
        public boolean equals(Context context, String assetPath, File dstFile) {
            long assetFileLength = getAssetLength(context, assetPath);
            return assetFileLength != -1 && assetFileLength == dstFile.length();
        }
    };


    private final static int ASSET_SPLIT_BASE = 0;

    private final static int BUFFER_SIZE = 8192;




    /**
     * Copy asset files. If assetName is a file, the it will be copied to file dst. Notice, a {@link #SIMPLE_ASSET_COMPARATOR} is used.
     *
     * @param context   application context.
     * @param assetName asset name to copy. 注意这里的 assetName 不能有子目录
     * @param dst       destination file. 文件全路径，不能是文件夹
     */
    public static boolean copyAssets(Context context, String assetName, String dst) {
        return copyAssets(context, assetName, dst, SIMPLE_ASSET_COMPARATOR);
    }

    /**
     * Copy asset files. If assetName is a file, the it will be copied to file dst.
     *
     * @param context    application context.
     * @param assetName  asset name to copy.
     * @param dst        destination file.
     * @param comparator a asset file comparator to determine whether asset & dst are equal files. Null to overwrite all dst files.
     */
    public static boolean copyAssets(Context context, String assetName, String dst, AssetFileComparator comparator) {
        return performCopyAssetsFile(context, assetName, dst, comparator);
    }

    private static boolean performCopyAssetsFile(Context context, String assetPath, String dstPath, AssetFileComparator comparator) {
        if (isEmpty(assetPath) || isEmpty(dstPath)) {
            return false;
        }

        AssetManager assetManager = context.getAssets();
        File dstFile = new File(dstPath);

        boolean succeed = false;
        InputStream in = null;
        OutputStream out = null;
        try {
            if (dstFile.exists()) {
                if (comparator != null && comparator.equals(context, assetPath, dstFile)) {
                    return true;
                } else {
                    // file will be overwrite later.
                    if (dstFile.isDirectory()) {
                        delete(dstFile);
                    }
                }
            }

            File parent = dstFile.getParentFile();
            if (parent.isFile()) {
                delete(parent);
            }
            if (!parent.exists() && !parent.mkdirs()) {
                return false;
            }

            in = assetManager.open(assetPath);
            out = new BufferedOutputStream(new FileOutputStream(dstFile), BUFFER_SIZE);
            byte[] buf = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            succeed = true;

        } catch (Throwable e) {
            FrameworkLogUtil.d("fail to copy assets file", e);
            // delete broken file.
            delete(dstFile);
        } finally {
            closeSilently(in);
            closeSilently(out);
        }
        return succeed;
    }

    public static long getAssetLength(Context context, String assetPath) {
        AssetManager assetManager = context.getAssets();
        // try to determine whether or not copy this asset file, using their size.
        try {
            AssetFileDescriptor fd = assetManager.openFd(assetPath);
            return fd.getLength();

        } catch (IOException e) {
            // this file is compressed. cannot determine it's size.
        }

        // try stream.
        InputStream tmpIn = null;
        try {
            tmpIn = assetManager.open(assetPath);
            return tmpIn.available();

        } catch (IOException e) {
            // do nothing.
        } finally {
            closeSilently(tmpIn);
        }
        return -1;
    }

    public static void delete(String path) {
        delete(path, false);
    }

    public static void delete(String path, boolean ignoreDir) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File f = new File(path);
        FileDirUtil.delete(f, ignoreDir);
    }

    /**
     * Delete corresponding path, file or directory.
     *
     * @param file path to delete.
     */
    public static void delete(File file) {
        FileDirUtil.delete(file, false);
    }



    private static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                // empty.
            }
        }
    }

    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }



    /**
     * 将 asset目录下某个zip文件拷贝到sd卡并解压
     * @param relativeZipFilePath
     * @return
     */
    //更新本地内置版本的情况下需要新增该版本号
    private final static String INNER_VERSION = "_1";

    /**
     *
     * @param relativeZipFilePath "effectaudio/anim/anim3.zip"
     * @return
     */
    public static String prepareZipResources(String relativeZipFilePath) {
        //获取sd卡中的目录 /storage/emulated/0/Android/data/com.apache.fastandroid/files/anueffectaudio/anim/anim1.zip
        String localZipPath = getResourcePath(relativeZipFilePath);
        //获得zip解压目录，/storage/emulated/0/Android/data/com.apache.fastandroid/files/anueffectaudio/anim/anim1_1/
        String localUnzipPath = localZipPath.substring(0, localZipPath.lastIndexOf(".")) + INNER_VERSION + File.separator;
        //storage/emulated/0/Android/data/com.apache.fastandroid/files/anueffectaudio/anim/anim1_1
        File unzipDest = new File(localUnzipPath);
        if (unzipDest.exists()) {
            return unzipDest.getAbsolutePath();
        }
        boolean isSuccess = AssetUtil.copyAssets(FrameworkApplication.getContext(), relativeZipFilePath, localZipPath);
        if (!isSuccess) {
            //throw new IllegalStateException("can not copy asset from " + relativeZipFilePath + " to " + localZipPath);
            FrameworkLogUtil.d( "can not copy asset from " + relativeZipFilePath + " to " + localZipPath);
            return "";
        }

        isSuccess = unzipDest.mkdirs();
        if (!isSuccess) {
            //throw new IllegalStateException("can not create unzip dir " + localUnzipPath);
            FrameworkLogUtil.d( "can not create unzip dir " + localUnzipPath);
            return "";
        }

        isSuccess = ZipUtils.unZipFile(new File(localZipPath), unzipDest);
        if (!isSuccess) {
            //throw new IllegalStateException("can not unzip from " + localZipPath + " to " + localUnzipPath);
            FrameworkLogUtil.d( "can not unzip from " + localZipPath + " to " + localUnzipPath);
            return "";
        }

        return unzipDest.getAbsolutePath();
    }

    /**
     *
     * @param relativeFilePath "effectaudio/anim/anim3.zip"
     * @return
     */
    public static String getResourcePath(String relativeFilePath) {
        return getResourceFile(relativeFilePath).getAbsolutePath();
    }

    private static File getResourceFile(String relativeFilePath) {
        return new File(getAnuEffectAudioDir() + File.separator + relativeFilePath.replace("effectaudio/", ""));
    }

    /**
     */
    public static String getAnuEffectAudioDir() {
        String d = PathUtils.getExternalAppFilesPath() + File.separator + "anueffectaudio";
        File f = new File(d);
        if (!f.exists()) {
            boolean ret = false;
            try {
                ret = f.mkdirs();
            } catch (SecurityException exception) {
                FrameworkLogUtil.d( "exception happen when mkdirs");
            }
            if (!ret) {
                FrameworkLogUtil.d(  "mkdirs failed:" + d);
            }
        }
        return d;
    }

}

package com.tesla.framework.common.util.file;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.tesla.framework.common.util.FrameworkLogUtil;
import com.tesla.framework.common.util.file.AssetUtil.FileComparator;

/**
 * author: jerry
 * created on: 2020/10/17 7:06 AM
 * description: 文件路径相关工作方法
 */
public class FileDirUtil {

    private static final BytesBufferPool sBytesBufferPool = new BytesBufferPool(2, 8 * 1024);
    private final static int BUFFER_SIZE = 8192;



    //****系统文件目录**********************************************************************************************

    /**
     * @return 程序系统文件目录
     * data/data/pkgName/files
     */
    public static String getFileDir(Context context) {
        return String.valueOf(context.getFilesDir());
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 程序系统文件目录绝对路径
     */
    public static String getFileDir(Context context, String customPath) {
        String path = context.getFilesDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }

//****系统缓存目录**********************************************************************************************

    /**
     * @return 程序系统缓存目录
     */
    public static String getCacheDir(Context context) {
        return String.valueOf(context.getCacheDir());
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 程序系统缓存目录
     */
    public static String getCacheDir(Context context, String customPath) {
        String path = context.getCacheDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }

//****Sdcard文件目录**********************************************************************************************

    /**
     * @return 内存卡文件目录
     */
    public static String getExternalFileDir(Context context) {
        return context.getExternalFilesDir("").getAbsolutePath();
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 内存卡文件目录
     */
    public static String getExternalFileDir(Context context, String customPath) {
        String path = context.getExternalFilesDir("") + formatPath(customPath);
        mkdir(path);
        return path;
    }

//****Sdcard缓存目录**********************************************************************************************

    /**
     * @return 内存卡缓存目录
     */
    public static String getExternalCacheDir(Context context) {
        return String.valueOf(context.getExternalCacheDir());
    }

    /**
     * @param context    上下文
     * @param customPath 自定义路径
     * @return 内存卡缓存目录
     */
    public static String getExternalCacheDir(Context context, String customPath) {
        String path = context.getExternalCacheDir() + formatPath(customPath);
        mkdir(path);
        return path;
    }

//****公共文件夹**********************************************************************************************

    /**
     * @return 公共下载文件夹
     */
    public static String getPublicDownloadDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

//****相关工具**********************************************************************************************

    /**
     * 创建文件夹
     *
     * @param DirPath 文件夹路径
     */
    public static void mkdir(String DirPath) {
        File file = new File(DirPath);
        if (!(file.exists() && file.isDirectory())) {
            file.mkdirs();
        }
    }

    /**
     * 格式化文件路径
     * 示例：  传入 "sloop" "/sloop" "sloop/" "/sloop/"
     * 返回 "/sloop"
     */
    private static String formatPath(String path) {
        if (!path.startsWith("/"))
            path = "/" + path;
        while (path.endsWith("/"))
            path = new String(path.toCharArray(), 0, path.length() - 1);
        return path;
    }


    /**
     * 拷贝文件
     * @param fromFile
     * @param toFile
     * @return
     */
    public static int copySdcardFile(String fromFile, String toFile)
    {
        try
        {
            InputStream fosfrom = new FileInputStream(fromFile);
            OutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0)
            {
                fosto.write(bt, 0, c);
            }
            fosfrom.close();
            fosto.close();
            return 0;

        } catch (Exception ex) {
            return -1;
        }
    }





    /**
     * 通过uri获取文件的绝对路径
     */
    public static String getFileAbsolutePath(Context context,Uri uri) {
        if (uri == null) {
            return null;
        }
        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    //未识别的类型，直接返回
                    return null;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
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
     * Copy files. If src is a directory, then all it's sub files will be copied into directory dst.
     * If src is a file, then it will be copied to file dst. Notice, a {@link #SIMPLE_COMPARATOR} is used.
     *
     * @param src file or directory to copy.
     * @param dst destination file or directory.
     * @return true if copy complete perfectly, false otherwise (more than one file cannot be copied).
     */
    public static boolean copyFiles(File src, File dst) {
        return copyFiles(src, dst, null);
    }

    /**
     * Copy files. If src is a directory, then all it's sub files will be copied into directory dst.
     * If src is a file, then it will be copied to file dst. Notice, a {@link #SIMPLE_COMPARATOR} is used.
     *
     * @param src    file or directory to copy.
     * @param dst    destination file or directory.
     * @param filter a file filter to determine whether or not copy corresponding file.
     * @return true if copy complete perfectly, false otherwise (more than one file cannot be copied).
     */
    public static boolean copyFiles(File src, File dst, FileFilter filter) {
        return copyFiles(src, dst, filter, SIMPLE_COMPARATOR);
    }

    /**
     * Copy files. If src is a directory, then all it's sub files will be copied into directory dst.
     * If src is a file, then it will be copied to file dst.
     *
     * @param src        file or directory to copy.
     * @param dst        destination file or directory.
     * @param filter     a file filter to determine whether or not copy corresponding file.
     * @param comparator a file comparator to determine whether src & dst are equal files. Null to overwrite all dst files.
     * @return true if copy complete perfectly, false otherwise (more than one file cannot be copied).
     */
    public static boolean copyFiles(File src, File dst, FileFilter filter, FileComparator comparator) {
        if (src == null || dst == null) {
            return false;
        }

        if (!src.exists()) {
            return false;
        }
        if (src.isFile()) {
            return performCopyFile(src, dst, filter, comparator);
        }

        File[] paths = src.listFiles();
        if (paths == null) {
            return false;
        }
        // default is true.
        boolean result = true;
        for (File sub : paths) {
            if (!copyFiles(sub, new File(dst, sub.getName()), filter)) {
                result = false;
            }
        }
        return result;
    }

    private static boolean performCopyFile(File srcFile, File dstFile, FileFilter filter, FileComparator comparator) {
        if (srcFile == null || dstFile == null) {
            return false;
        }
        if (filter != null && !filter.accept(srcFile)) {
            return false;
        }

        FileChannel inc = null;
        FileChannel ouc = null;
        try {
            if (!srcFile.exists() || !srcFile.isFile()) {
                return false;
            }

            if (dstFile.exists()) {
                if (comparator != null && comparator.equals(srcFile, dstFile)) {
                    // equal files.
                    return true;
                } else {
                    // delete it in case of folder.
                    delete(dstFile);
                }
            }

            File toParent = dstFile.getParentFile();
            if (toParent.isFile()) {
                delete(toParent);
            }
            if (!toParent.exists() && !toParent.mkdirs()) {
                return false;
            }

            inc = (new FileInputStream(srcFile)).getChannel();
            ouc = (new FileOutputStream(dstFile)).getChannel();

            ouc.transferFrom(inc, 0, inc.size());

        } catch (Throwable e) {
            FrameworkLogUtil.d("fail to copy file", e);
            // exception occur, delete broken file.
            delete(dstFile);
            return false;
        } finally {
            closeSilently(inc);
            closeSilently(ouc);
        }
        return true;
    }

    public static boolean copyFile(String srcPath, OutputStream out) {
        if (TextUtils.isEmpty(srcPath) || out == null)
            return false;

        InputStream in = null;
        BytesBufferPool.BytesBuffer bytesBuffer = sBytesBufferPool.get();
        try {
            File fromFile = new File(srcPath);
            if (!fromFile.exists() || !fromFile.isFile())
                return false;

            in = new FileInputStream(fromFile);
            int count = 0;
            while ((count = in.read(bytesBuffer.data, 0, bytesBuffer.data.length)) > 0) {
                out.write(bytesBuffer.data, 0, count);
            }
        } catch (Throwable e) {
            Log.e("FileUtils", "exception when copy file!", e);
            return false;
        } finally {
            sBytesBufferPool.recycle(bytesBuffer);
            if (in != null) {
                try {
                    in.close();
                } catch (Throwable e2) {
                }
            }
        }
        return true;
    }

    /**
     * Copy file corresponding stream to des file. The source input stream will be left open.
     *
     * @param source source input stream.
     * @param dst    destination file.
     * @return true if copy complete perfectly, false otherwise.
     */
    public static boolean copyFile(InputStream source, File dst) {
        return copyFile(source, dst, false);
    }

    /**
     * Copy file corresponding stream to des file.
     *
     * @param source          source input stream.
     * @param dst             destination file.
     * @param closeWhenFinish whether closeWhenFinish source input stream when operation finished.
     * @return true if copy complete perfectly, false otherwise.
     */
    public static boolean copyFile(InputStream source, File dst, boolean closeWhenFinish) {
        if (source == null || dst == null) {
            return false;
        }
        OutputStream ous = null;
        try {
            ous = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
            return performCopyStream(source, ous);

        } catch (Throwable e) {
            FrameworkLogUtil.d( "fail to copy file", e);
        } finally {
            if (closeWhenFinish) {
                closeSilently(source);
            }
            closeSilently(ous);
        }
        return false;
    }

    private static boolean performCopyStream(InputStream ins, OutputStream ous) {
        try {
            byte[] buffer = new byte[BUFFER_SIZE];
            int count;
            while ((count = ins.read(buffer)) > 0) {
                ous.write(buffer, 0, count);
            }
            return true;

        } catch (Throwable e) {
            FrameworkLogUtil.d( "fail to copy stream", e);
        }
        return false;
    }


    /**
     * Delete corresponding path, file or directory.
     *
     * @param file path to delete.
     */
    public static void delete(File file) {
        delete(file, false);
    }

    /**
     * Delete corresponding path, file or directory.
     *
     * @param file      path to delete.
     * @param ignoreDir whether ignore directory. If true, all files will be deleted while directories is reserved.
     */
    public static void delete(File file, boolean ignoreDir) {
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }

        File[] fileList = file.listFiles();
        if (fileList == null) {
            return;
        }

        for (File f : fileList) {
            delete(f, ignoreDir);
        }
        // delete the folder if need.
        if (!ignoreDir) file.delete();
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









}

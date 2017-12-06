package com.tesla.framework.common.util.file;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

import com.tesla.framework.common.util.log.NLog;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileUtils {
	private FileUtils() {
	}

	private static final String TAG = FileUtils.class.getSimpleName();

	public static String readFileToString(File file) {
		StringBuffer sb = new StringBuffer();
		try {
			InputStream is = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String readLine;
			while ((readLine = reader.readLine()) != null) {
				sb.append(readLine);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		String content = sb.toString();
		NLog.d(TAG, String.format("read file's content = %s", content.length() >= 150 ? content.substring(0, 150) : content));
		return sb.toString();
	}
	public static byte[] readFileToBytes(File file) {
		try {
			return readStreamToBytes(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readStreamToBytes(InputStream in) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 8];
		int length = -1;
		while ((length = in.read(buffer)) != -1) {
			out.write(buffer, 0, length);
		}
		out.flush();
		byte[] result = out.toByteArray();
		in.close();
		out.close();
		return result;
	}

	public static boolean writeFile(File file, String content) {
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(content.getBytes());
			out.flush();

			NLog.d(TAG, String.format("write file's content = %s", content.length() >= 150 ? content.substring(0, 150) : content));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e2) {
				}
		}

		return true;
	}






	/**
	 * 将内容写入文件
	 *
	 * @param filePath eg:/mnt/sdcard/demo.txt
	 * @param content  内容
	 * @param isAppend 是否追加
	 */
	public static void writeFile(String filePath, String content, boolean isAppend) {
		try {
			FileOutputStream fout = new FileOutputStream(filePath, isAppend);
			byte[] bytes = content.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}






	
	public static boolean writeFile(File file, byte[] bytes) {
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();

		FileOutputStream out = null;
		ByteArrayInputStream in = new ByteArrayInputStream(bytes);
		try {
			out = new FileOutputStream(file);

			byte[] buffer = new byte[1024 * 8];
			int length;
			
			while ((length = in.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e2) {
				}
		}

		return true;
	}

	public static void copyFile(File sourceFile, File targetFile) throws Exception {
		FileInputStream in = new FileInputStream(sourceFile);
		byte[] buffer = new byte[128 * 1024];
		int len = -1;
		FileOutputStream out = new FileOutputStream(targetFile);
		while ((len = in.read(buffer)) != -1)
			out.write(buffer, 0, len);
		out.flush();
		out.close();
		in.close();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T readObject(File file, Class<T> clazz) throws Exception {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(file));
			
			return (T) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				in.close();
		}
		
		return null;
	}
	
	public static void writeObject(File file, Serializable object) throws Exception {
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(file));
			
			out.writeObject(object);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
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
		return String.valueOf(context.getExternalFilesDir(""));
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









}

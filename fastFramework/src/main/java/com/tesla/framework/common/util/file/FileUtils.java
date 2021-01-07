package com.tesla.framework.common.util.file;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.tesla.framework.common.util.log.FastLog;

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
		FastLog.d(TAG, String.format("read file's content = %s", content.length() >= 150 ? content.substring(0, 150) : content));
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
		if (!file.getParentFile().exists()){
			file.getParentFile().mkdirs();
		}


		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(content.getBytes());
			out.flush();

			FastLog.d(TAG, String.format("write file's content = %s", content.length() >= 150 ? content.substring(0, 150) : content));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (out != null){
				try {
					out.close();
				} catch (Exception e2) {
				}
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















}

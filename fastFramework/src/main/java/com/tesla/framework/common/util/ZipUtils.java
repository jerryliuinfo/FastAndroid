package com.tesla.framework.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;


public class ZipUtils {
    static final String TAG = "UnZipUtils";
    protected static final int BUFF_SIZE = 1024 * 1024; // 1M Byte


    /**
     * 解压缩一个文件
     *
     * @param zipFile    压缩文件
     * @param folderPath 解压缩的目标目录
     * @throws IOException 当解压缩过程出错时抛出
     */
    public static boolean unZipFile(File zipFile, String folderPath) throws Exception {
        if (!folderPath.endsWith(File.separator)) {
            folderPath += File.separator;
        }

        ZipFile zfile = new ZipFile(zipFile);
        Enumeration zList = zfile.entries();
        ZipEntry ze = null;
        byte[] buf = new byte[1024];
        while (zList.hasMoreElements()) {
            ze = (ZipEntry) zList.nextElement();
            if (ze.getName().contains("../")) {
                FrameworkLogUtil.d( "unZipFil unsecurity zipfile!! please check is valid!!");
                throw new Exception("unsecurity zipfile!! please check is valid!!");
            }
            if (ze.isDirectory()) { //dir
                FrameworkLogUtil.d("upZipFile ze.getName() = " + ze.getName());
                String dirstr = folderPath + ze.getName();
                //dirstr.trim();
                dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                FrameworkLogUtil.d("upZipFile str = " + dirstr);
                File f = new File(dirstr);
                f.mkdir();
                continue;
            }
            //file
            //QLog.d("upZipFile", QLog.CLR, "ze.getName() = "+ze.getName());
            File newFile = new File(folderPath + ze.getName());//getRealFileName(folderPath, ze.getName());
            //QLog.d("upZipFile", QLog.CLR, "new file:" + newFile.toString());
            OutputStream os = new BufferedOutputStream(new FileOutputStream(newFile));
            InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
            int readLen = 0;
            while ((readLen = is.read(buf, 0, 1024)) != -1) {
                os.write(buf, 0, readLen);
            }
            is.close();
            os.close();
        }
        zfile.close();
        return true;
    }

    public static boolean unZipFile(File zipFile, File unzipDest) {
        try {
            unZipFile(zipFile, unzipDest.getAbsolutePath());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 获取压缩包解压后的内存大小
     *
     * @param filePath 文件路径
     * @return 返回内存long类型的值
     */
    public static long getZipTrueSize(String filePath) {
        long size = 0;
        ZipFile f = null;
        try {
            f = new ZipFile(filePath);
            Enumeration<? extends ZipEntry> en = f.entries();
            while (en.hasMoreElements()) {
                size += en.nextElement().getSize();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (f != null) {
                try {
                    f.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }


    /**
     * 解压缩一个文件
     *
     * @param
     * @param folderPath 解压缩的目标目录
     * @throws IOException 当解压缩过程出错时抛出
     */
    public static boolean unZipFolder(String zipFilePath, String folderPath, UnZipListener listener) {
        FrameworkLogUtil.d( "new file:" + zipFilePath + ", " + folderPath);
        long sumLength = 0;
        long zipLength = getZipTrueSize(zipFilePath);

        File desDir = new File(folderPath);
        if (!desDir.exists()) {
            desDir.mkdirs();
        }
        File zipFile = new File(zipFilePath);
        ZipFile zf;
        try {
            zf = new ZipFile(zipFile);
        } catch (ZipException e) {
            e.printStackTrace();
            FrameworkLogUtil.d( "ZipException");
            return false;
        } catch (IOException e) {
            FrameworkLogUtil.d( "IOException");
            e.printStackTrace();
            return false;
        }
        boolean isFail = false;
        try {
            for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                if (entry.getName().contains("../")) {
                    FrameworkLogUtil.d("unZipFile", "unsecurity zipfile!! please check is valid!!");
                    throw new Exception("unsecurity zipfile!! please check is valid!!");
                }
                if (entry.isDirectory()) {
                    String szName = entry.getName();
                    File entryFile = new File(folderPath + File.separator + szName);
                    if (!entryFile.exists()) {
                        entryFile.mkdirs();
                    }

                } else {
                    InputStream in = zf.getInputStream(entry);
                    String str = folderPath + File.separator + entry.getName();
                    str = new String(str.getBytes("8859_1"), "GB2312");
                    File desFile = new File(str);
                    if (!desFile.exists()) {
                        File fileParentDir = desFile.getParentFile();
                        if (!fileParentDir.exists()) {
                            fileParentDir.mkdirs();
                        }
                        desFile.createNewFile();
                    }
                    OutputStream out = new FileOutputStream(desFile);
                    byte buffer[] = new byte[4096];
                    int realLength;
                    while ((realLength = in.read(buffer)) > 0) {
                        sumLength += realLength;
                        if (listener != null && zipLength > 0) {
                            listener.zipProgress((float) (sumLength * 100) / zipLength, sumLength, zipLength);
                        }
                        out.write(buffer, 0, realLength);
                    }
                    in.close();
                    out.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            isFail = true;
            FrameworkLogUtil.e( e.getClass().getSimpleName(), e);
        }

        try {
            zf.close();
        } catch (IOException e) {
            e.printStackTrace();
            isFail = true;
        }
        return !isFail;
    }


    /**
     * 获得压缩文件内文件列表
     *
     * @param zipFile 压缩文件
     * @return 压缩文件内文件名称
     * @throws ZipException 压缩文件格式有误时抛出
     * @throws IOException  当解压缩过程出错时抛出
     */
    public static ArrayList<String> getEntriesNames(File zipFile) throws ZipException, IOException {
        ArrayList<String> entryNames = new ArrayList<String>();
        Enumeration<?> entries = getEntriesEnumeration(zipFile);
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry) entries.nextElement());
            entryNames.add(new String(getEntryName(entry).getBytes("GB2312"), "8859_1"));
        }
        return entryNames;
    }


    /**
     * 获得压缩文件内压缩文件对象以取得其属性
     *
     * @param zipFile 压缩文件
     * @return 返回一个压缩文件列表
     */
    public static Enumeration<?> getEntriesEnumeration(File zipFile){
        ZipFile zf = null;
        Enumeration result = null;
        try {
            zf = new ZipFile(zipFile);
            result = zf.entries();
        } catch (Exception e) {
            FrameworkLogUtil.e( "getEntriesEnumeration", e);
        } finally {
            if (zf != null) {
                try {
                    zf.close();
                } catch (IOException e){
                    FrameworkLogUtil.e( "getEntriesEnumeration", e);
                }
            }
        }
        return result;
    }

    /**
     * 取得压缩文件对象的注释
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的注释
     * @throws UnsupportedEncodingException
     */
    public static String getEntryComment(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getComment().getBytes("GB2312"), "8859_1");
    }

    /**
     * 取得压缩文件对象的名称
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的名称
     * @throws UnsupportedEncodingException
     */
    public static String getEntryName(ZipEntry entry) throws UnsupportedEncodingException {
        if (!entry.getName().contains("../")) {
            return new String(entry.getName().getBytes("GB2312"), "8859_1");
        }
        return "";
    }

    public interface UnZipListener {
        /**
         * 解压进度
         */
        void zipProgress(float progress, long current, long total);
    }



}
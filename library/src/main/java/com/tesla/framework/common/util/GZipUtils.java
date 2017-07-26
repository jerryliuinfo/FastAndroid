package com.tesla.framework.common.util;


import com.tesla.framework.common.util.log.NLog;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * GZIP工具
 */
public abstract class GZipUtils {

    public static final int BUFFER = 1024;
    public static final String EXT = ".gz";

    /**
     * 数据压缩
     *
     * @param data
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static byte[] compress(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 压缩
        compress(bais, baos);

        byte[] output = baos.toByteArray();

        try {
            baos.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        try {
            baos.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        try {
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    /**
     * 文件压缩(压缩后删除源文件)
     *
     * @param file 待压文件
     * @throws Exception
     */
    public static void compress(File file) {
        compress(file, true);
    }

    /**
     * 文件压缩
     *
     * @param file   待压文件
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    public static void compress(File file, boolean delete) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(file.getPath() + EXT);
            compress(fis, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.flush();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (delete) {
            file.delete();
        }
    }

    /**
     * 数据压缩
     *
     * @param is
     * @param os
     * @throws IOException
     * @throws Exception
     */
    public static void compress(InputStream is, OutputStream os) {
        GZIPOutputStream gos = null;
        try {
            gos = new GZIPOutputStream(os);

            int count;
            byte data[] = new byte[BUFFER];
            while ((count = is.read(data, 0, BUFFER)) != -1) {
                gos.write(data, 0, count);
            }

            gos.finish();
            gos.flush();
        } catch (Exception e) {
            NLog.printStackTrace(e);
        } finally {
            try {
                if (gos != null) {
                    gos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 文件压缩(压缩后删除源文件)
     *
     * @param path 待压文件路径
     * @throws Exception
     */
    public static void compress(String path) {
        compress(path, true);
    }

    /**
     * 文件压缩
     *
     * @param path   待压文件路径
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    public static void compress(String path, boolean delete) {
        File file = new File(path);
        compress(file, delete);
    }

    /**
     * 数据解压缩
     *
     * @param data
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static byte[] decompress(byte[] data) {
        ByteArrayInputStream bais = null;
        ByteArrayOutputStream baos = null;
        try {
            bais = new ByteArrayInputStream(data);
            baos = new ByteArrayOutputStream();

            // 解压缩
            decompress(bais, baos);
            data = baos.toByteArray();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                baos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try {
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return data;
    }

    /**
     * 文件解压缩
     *
     * @param file 待解压文件
     * @throws Exception
     */
    public static void decompress(File file) {
        decompress(file, true);
    }

    /**
     * 文件解压缩
     *
     * @param file   待解压文件
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    public static void decompress(File file, boolean delete) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(file);
            fos = new FileOutputStream(file.getPath().replace(EXT, ""));
            decompress(fis, fos);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.flush();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (delete) {
            file.delete();
        }
    }

    /**
     * 文件解压缩
     */
    public static void decompress(File in, File out) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(in);
            fos = new FileOutputStream(out);
            decompress(fis, fos);
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (fos != null) {
                    fos.flush();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 数据解压缩
     *
     * @param is
     * @param os
     * @throws IOException
     * @throws Exception
     */
    public static void decompress(InputStream is, OutputStream os) {
        GZIPInputStream gis = null;
        try {
            gis = new GZIPInputStream(is);

            int count;
            byte data[] = new byte[BUFFER];
            while ((count = gis.read(data, 0, BUFFER)) != -1) {
                os.write(data, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (gis != null) {
                    gis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 文件解压缩(解压完删掉源文件)
     *
     * @param path 待解压文件路径
     * @throws Exception
     */
    public static void decompress(String path) {
        decompress(path, true);
    }

    /**
     * 文件解压缩
     *
     * @param path   待解压文件路径
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    public static void decompress(String path, boolean delete) {
        File file = new File(path);
        decompress(file, delete);
    }

    /**
     * 解压到指定目录
     *
     * @param zipPath
     * @param descDir
     * @author isea533
     */
    public static void unZipFiles(String zipPath, String descDir) throws IOException {
        unZipFiles(new File(zipPath), descDir);
    }

    /**
     * 解压文件到指定目录
     *
     * @param zipFile
     * @param descDir
     * @author isea533
     */
    public static void unZipFiles(File zipFile, String descDir) throws IOException {
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = new ZipFile(zipFile);
        for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            String outPath = (descDir + File.separator + zipEntryName).replaceAll("\\*", "/");
            //判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            //判断文件全路径是否为文件夹
            if (new File(outPath).isDirectory()) {
                continue;
            }

            InputStream in = null;
            OutputStream out = null;
            try {
                in = zip.getInputStream(entry);
                out = new FileOutputStream(outPath);
                byte[] buf1 = new byte[1024];
                int len;
                while ((len = in.read(buf1)) > 0) {
                    out.write(buf1, 0, len);
                }
            } finally {
                if (in != null) {
                    in.close();
                }

                if (out != null) {
                    out.close();
                }
            }
        }
    }

    /**
     * 输出指定文件byte[]
     *
     * @param zipFile
     * @param fileName
     * @return
     * @throws IOException
     */

    public static byte[] unZipFilesToByte(File zipFile, String fileName)
            throws IOException {
        ZipFile zip = new ZipFile(zipFile);
        for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();

            if (!zipEntryName.equals(fileName)) {
                continue;
            }

            InputStream in = null;
            byte[] buffer;

            try {
                in = zip.getInputStream(entry);

                buffer = new byte[1024 * 1024 * 2];
                byte[] buf = new byte[1024];
                int offset = 0;
                int len;

                while ((len = in.read(buf)) >= 0) {
                    if (offset > buffer.length) {
                        throw new IOException("file max length 1024 * 1024 * 2");
                    }

                    System.arraycopy(buf, 0, buffer, offset, buf.length);
                    offset = offset + len;
                }

            } finally {
                if (in != null) {
                    in.close();
                }
            }

            return buffer;
        }

        return null;
    }

    public static String readZipFile(File zipFile, String fileName) throws IOException {
        BufferedReader reader = null;
        ZipInputStream zin = null;
        InputStream in = null;
        StringBuilder buffer = new StringBuilder();
        try {
            ZipFile file = new ZipFile(zipFile);
            in = new BufferedInputStream(new FileInputStream(zipFile));
            zin = new ZipInputStream(in);
            ZipEntry ze;
            while ((ze = zin.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    continue;
                }

                if (!ze.getName().equals(fileName)) {
                    continue;
                }

                long size = ze.getSize();
                if (size > 0) {

                    reader = new BufferedReader(new InputStreamReader(file.getInputStream(ze)));
                    String line = reader.readLine(); // 读取第一行
                    while (line != null) { // 如果 line 为空说明读完了
                        buffer.append(line); // 将读到的内容添加到 buffer 中
                        buffer.append("\n"); // 添加换行符
                        line = reader.readLine(); // 读取下一行
                    }
                }
            }
        } finally {
            if (reader != null) {
                reader.close();
            }

            if (zin != null) {
                zin.closeEntry();
            }

            if (in != null) {
                in.close();
            }
        }

        return buffer.toString();
    }
}

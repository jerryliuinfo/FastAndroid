package com.tesla.framework.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author zeming_liu
 * @Description:
 * @date 2016/9/7.
 * @copyright TCL-MIG
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
     * @param file 待压文件
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
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }

        }

        if (delete) {
            boolean flag = file.delete();
            if(flag == false){
                //System.out.println("Delete error");
            }
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
//            gos.flush();
        } catch (IOException e) {
            e.printStackTrace();
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
     * @param path 待压文件路径
     * @param delete 是否删除原始文件
     * @throws Exception
     */
    public static void compress(String path, boolean delete) {
        File file = new File(path);
        compress(file, delete);
    }

//    /**
//     * 数据解压缩
//     *
//     * @param data
//     * @return
//     * @throws IOException
//     * @throws Exception
//     */
//    public static byte[] decompress(byte[] data) {
//        ByteArrayInputStream bais = null;
//        ByteArrayOutputStream baos = null;
//        try {
//            bais = new ByteArrayInputStream(data);
//            baos = new ByteArrayOutputStream();
//            // 解压缩
//            decompress(bais, baos);
//            data = baos.toByteArray();
//        } finally {
//            try {
//                if (baos != null) {
//                    baos.flush();
//                }
//            } catch (IOException e2) {
//            }
//            try {
//                if (baos != null) {
//                    baos.close();
//                }
//            } catch (IOException e1) {
//            }
//            try {
//                if (bais != null) {
//                    bais.close();
//                }
//            } catch (IOException e) {
//            }
//        }
//
//        return data;
//    }

    /**
     * 文件解压缩
     * 
     * @param file 待解压文件
     * @param delete 是否删除原始文件
     * @throws Exception
     */
//    public static void decompress(File file, boolean delete) {
//        FileInputStream fis = null;
//        FileOutputStream fos = null;
//        try {
//            fis = new FileInputStream(file);
//            fos = new FileOutputStream(file.getPath().replace(EXT, ""));
//            decompress(fis, fos);
//        } catch (FileNotFoundException e2) {
//            e2.printStackTrace();
//        } finally {
//            try {
//                if (fis != null) {
//                    fis.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                if (fos != null) {
//                    fos.flush();
//                }
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            try {
//                if (fos != null) {
//                    fos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if (delete) {
//            boolean flag = file.delete();
//            if(flag == false){
//                System.out.println("Delete error");
//            }
//        }
//    }

//    /**
//     * 数据解压缩
//     *
//     * @param is
//     * @param os
//     * @throws IOException
//     * @throws Exception
//     */
//    public static void decompress(InputStream is, OutputStream os) {
//        GZIPInputStream gis = null;
//        try {
//            gis = new GZIPInputStream(is);
//
//            int count;
//            byte data[] = new byte[BUFFER];
//            while ((count = gis.read(data, 0, BUFFER)) != -1) {
//                os.write(data, 0, count);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (gis != null) {
//                    gis.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}

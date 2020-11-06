package com.apache.fastandroid.artemis.util;

import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.util.regex.Pattern;

/**
 * author: jerry
 * created on: 2020/7/28 11:44 AM
 * description:
 */
class CpuUtil {
    private static final String TAG = "CpuUtil";
    private static final float THRESHOLD_LOW_FREQ_CPU = 1.2F;
    private static Float mMaxCpuFrep = 0.0F;
    private static int coresNum = 0;
    private static boolean isGet = false;

    public CpuUtil() {
    }

    public static boolean isLowFreq() {
        float cpuFreq = getMaxCpuFreq();
        return cpuFreq > 0.0F && cpuFreq < 1.2F;
    }

    public static Float getMaxCpuFreq() {
        if (isGet) {
            return mMaxCpuFrep;
        } else {
            isGet = true;
            String result = "";

            try {
                String[] args = new String[]{"/system/bin/cat", "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq"};
                ProcessBuilder cmd = new ProcessBuilder(args);
                Process process = cmd.start();
                InputStream in = process.getInputStream();

                for(byte[] re = new byte[24]; in.read(re) != -1; result = result + new String(re)) {
                }

                in.close();
                mMaxCpuFrep = (float)Long.parseLong(result.trim()) / 1000000.0F;
            } catch (Exception var6) {
                BaseLibLogUtil.e( "读取cpu频率 转换出错" + var6.getStackTrace().toString());
                mMaxCpuFrep = 0.0F;
            } catch (OutOfMemoryError var7) {
                BaseLibLogUtil.e("读取cpu频率 oom" + var7.getStackTrace().toString());
                mMaxCpuFrep = 0.0F;
            }

            return mMaxCpuFrep;
        }
    }

    public static int getNumCores() {
        if (coresNum > 0) {
            return coresNum;
        } else {
            try {
                File dir = new File("/sys/devices/system/cpu/");

                class CpuFilter implements FileFilter {
                    CpuFilter() {
                    }

                    public boolean accept(File pathname) {
                        return Pattern.matches("cpu[0-9]", pathname.getName());
                    }
                }

                File[] files = dir.listFiles(new CpuFilter());
                if (files != null) {
                    coresNum = files.length;
                }

                return coresNum;
            } catch (Exception var2) {
                return 1;
            }
        }
    }
}

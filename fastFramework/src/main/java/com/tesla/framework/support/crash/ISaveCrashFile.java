package com.tesla.framework.support.crash;

/**
 * Created by 01370340 on 2017/12/26.
 */

public interface ISaveCrashFile {

    boolean saveFile(Thread thread, Throwable ex,String dir, String crashInfo);

}

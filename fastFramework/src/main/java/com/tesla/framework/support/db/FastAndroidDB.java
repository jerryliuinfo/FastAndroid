package com.tesla.framework.support.db;

import android.os.SystemClock;

import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.component.orm.SqliteUtility;
import com.tesla.framework.component.orm.SqliteUtilityBuilder;

/**
 * Created by jerryliu on 2017/4/8.
 */

public class FastAndroidDB {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "fastAndroid_db";
    public static void setDB(){
        SystemClock.sleep(300);
        new SqliteUtilityBuilder().configVersion(DB_VERSION).configDBName(DB_NAME).build(FrameworkApplication.getContext());
    }

    public static SqliteUtility getDB(){
        return SqliteUtility.getInstance(DB_NAME);
    }


}

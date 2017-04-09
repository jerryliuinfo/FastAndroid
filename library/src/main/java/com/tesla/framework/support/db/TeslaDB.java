package com.tesla.framework.support.db;

import com.tesla.framework.FrameworkApplication;
import com.tesla.framework.component.orm.SqliteUtility;
import com.tesla.framework.component.orm.SqliteUtilityBuilder;

/**
 * Created by jerryliu on 2017/4/8.
 */

public class TeslaDB {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "tesla_db";
    public static void setDB(){
        new SqliteUtilityBuilder().configVersion(DB_VERSION).configDBName(DB_NAME).build(FrameworkApplication.getContext());
    }

    public static SqliteUtility getDB(){
        return SqliteUtility.getInstance(DB_NAME);
    }


}

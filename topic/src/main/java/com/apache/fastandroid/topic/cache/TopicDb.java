package com.apache.fastandroid.topic.cache;

import android.content.Context;

import com.tesla.framework.component.orm.SqliteUtility;
import com.tesla.framework.component.orm.SqliteUtilityBuilder;

/**
 * Created by 01370340 on 2017/12/22.
 */

public class TopicDb {
    public static final String DB_NAME = "topic.db";
    public static final int DB_VERSION = 1;


    public static void initDb(Context context){
        new SqliteUtilityBuilder().configVersion(DB_VERSION).configDBName(DB_NAME).build(context);

    }

    public static SqliteUtility getDb(){
        return SqliteUtility.getInstance(DB_NAME);
    }
}

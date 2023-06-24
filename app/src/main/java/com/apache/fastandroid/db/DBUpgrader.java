package com.apache.fastandroid.db;

import android.database.sqlite.SQLiteDatabase;

class DBUpgrader {
    /**
     * Upgrades the given database to a new schema version
     */
    static void upgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
       /* if (oldVersion <= 1) {
            db.execSQL("ALTER TABLE " + PodDBAdapter.TABLE_NAME_FEEDS + " ADD COLUMN "
                    + PodDBAdapter.KEY_TYPE + " TEXT");
        }
        if (oldVersion <= 2) {
            db.execSQL("ALTER TABLE " + PodDBAdapter.TABLE_NAME_SIMPLECHAPTERS
                    + " ADD COLUMN " + PodDBAdapter.KEY_LINK + " TEXT");
        }
        */
    }

}

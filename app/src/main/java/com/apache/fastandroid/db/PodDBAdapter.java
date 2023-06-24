package com.apache.fastandroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.blankj.utilcode.util.FileUtils;

import java.io.File;

/**
 * Created by Jerry on 2023/6/23.
 */
public class PodDBAdapter {

    private static final String TAG = "PodDBAdapter";


    public static final String DATABASE_NAME = "Antennapod.db";

    private static Context context;
    private static PodDBAdapter instance;

    private final SQLiteDatabase db;
    private final PodDBHelper dbHelper;

    private static final String CREATE_TABLE_FEEDS = "";

    public static void init(Context context) {
        PodDBAdapter.context = context.getApplicationContext();
    }

    public static synchronized PodDBAdapter getInstance() {
        if (instance == null) {
            instance = new PodDBAdapter();
        }
        return instance;
    }

    private PodDBAdapter() {
        dbHelper = new PodDBHelper(PodDBAdapter.context, DATABASE_NAME, null);
        db = openDb();
    }

    private SQLiteDatabase openDb() {
        SQLiteDatabase newDb;
        try {
            newDb = dbHelper.getWritableDatabase();
            newDb.disableWriteAheadLogging();
        } catch (SQLException ex) {
            Log.e(TAG, Log.getStackTraceString(ex));
            newDb = dbHelper.getReadableDatabase();
        }
        return newDb;
    }

    public synchronized PodDBAdapter open() {
        // do nothing
        return this;
    }

    public synchronized void close() {
        // do nothing
    }


    public final Cursor getAllFeedsCursor() {
//        final String query = "SELECT " + KEYS_FEED
//                + " FROM " + TABLE_NAME_FEEDS
//                + " ORDER BY " + TABLE_NAME_FEEDS + "." + KEY_TITLE + " COLLATE NOCASE ASC";

        String query = "";
        return db.rawQuery(query, null);
    }


    public long setDownloadStatus() {
        ContentValues values = new ContentValues();
//        db.update(TABLE_NAME_DOWNLOAD_LOG, values, KEY_ID + "=?",
//                new String[]{String.valueOf(status.getId())});
        return 1;
    }




    private static class PodDBHelper extends SQLiteOpenHelper {
        public static final int VERSION = 3010000;

        /**
         * Constructor.
         *
         * @param context Context to use
         * @param name    Name of the database
         * @param factory to use for creating cursor objects
         */
        public PodDBHelper(final Context context, final String name, final SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory, VERSION, new PodDbErrorHandler());
        }

        @Override
        public void onCreate(final SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_FEEDS);

        }

        @Override
        public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
            Log.w("DBAdapter", "Upgrading from version " + oldVersion + " to " + newVersion + ".");
            DBUpgrader.upgrade(db, oldVersion, newVersion);


        }

    }

    public static class PodDbErrorHandler implements DatabaseErrorHandler {
        @Override
        public void onCorruption(SQLiteDatabase db) {
            Log.e(TAG, "Database corrupted: " + db.getPath());

            File dbPath = new File(db.getPath());
            File backupFolder = PodDBAdapter.context.getExternalFilesDir(null);
            File backupFile = new File(backupFolder, "CorruptedDatabaseBackup.db");
            try {
                FileUtils.copy(dbPath, backupFile, new FileUtils.OnReplaceListener() {
                    @Override
                    public boolean onReplace(File srcFile, File destFile) {
                        return false;
                    }
                });
                Log.d(TAG, "Dumped database to " + backupFile.getPath());
            } catch (Exception e) {
                Log.d(TAG, Log.getStackTraceString(e));
            }

            new DefaultDatabaseErrorHandler().onCorruption(db); // This deletes the database
        }
    }
}

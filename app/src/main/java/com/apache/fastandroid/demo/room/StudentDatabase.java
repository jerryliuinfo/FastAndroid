package com.apache.fastandroid.demo.room;

import com.apache.fastandroid.app.FastAndroidApplication;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by Jerry on 2021/3/17.
 */
@Database(entities = {Student.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase {
    private static final String DB_NAME = "student";
    private static StudentDatabase database;

    public static synchronized StudentDatabase getInstance(){
        if (database == null){
            database = Room.databaseBuilder(FastAndroidApplication.getContext(),StudentDatabase.class,DB_NAME).build();
        }
        return database;
    }

    public abstract StudentDao studentDao();
}

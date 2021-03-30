package com.apache.fastandroid.demo.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/**
 * Created by Jerry on 2021/3/17.
 */
@Dao
public interface StudentDao {
    @Insert
    void insertStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Update
    void updateStudent(Student student);

    @Query("SELECT * from student where id = :id")
    Student queryById(String id);

    @Query("SELECT * from student")
    List<Student> queryAll();
}

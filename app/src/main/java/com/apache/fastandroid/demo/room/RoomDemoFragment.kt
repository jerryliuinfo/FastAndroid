package com.apache.fastandroid.demo.room

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.artemis.base.BaseFragment
import com.tesla.framework.common.util.log.NLog
import kotlinx.android.synthetic.main.fragment_jetpack_room.*

/**
 * Created by Jerry on 2021/3/17.
 */
class RoomDemoFragment:BaseFragment() {
    var instance = StudentDatabase.getInstance()
    override fun inflateContentView(): Int {
        return R.layout.fragment_jetpack_room
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        btn_insert.setOnClickListener {
           instance.studentDao().insertStudent(Student(1,"Zhangsan",10))
        }
        btn_delete.setOnClickListener {
            instance.studentDao().deleteStudent(Student(1,"Zhangsan",10))
        }
        btn_modify.setOnClickListener {
            instance.studentDao().updateStudent(Student(1,"lisi",12))
        }
        btn_query_by_id.setOnClickListener {
            var student = instance.studentDao().queryById("1")
            NLog.d(TAG, "student: %s", student)
        }
        btn_query_all.setOnClickListener {
            var list = instance.studentDao().queryAll()
            NLog.d(TAG, "list: %s", list)
        }
    }
}
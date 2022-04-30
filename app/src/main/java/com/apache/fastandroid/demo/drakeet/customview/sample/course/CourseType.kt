package com.seiko.demo.course

enum class CourseType {
    Stage,        // 舞台
    WhiteBoard,   // 白板
    Musical,      // 乐器
    CourseWare;   // 课件

    companion object {
        @JvmStatic
        val DEFAULT = Stage
    }
}
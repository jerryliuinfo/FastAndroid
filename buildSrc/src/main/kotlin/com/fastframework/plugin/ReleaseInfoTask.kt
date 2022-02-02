package com.fastframework.plugin

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction




/**
 * Created by Jerry on 2022/2/2.
 */
open class ReleaseInfoTask: DefaultTask() {
    init {
        //1、在构造器中配置了该 Task 对应的 Task group，即 Task 组，并为其添加上了对应的描述信息
        group = "version_manager"
        description = "release info updae"
    }

    // 2、在 gradle 执行阶段执行
    @TaskAction
    fun doAction() {
       println("ReleaseInfoTask doAction")
    }

}
package com.fastframework.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Jerry on 2022/2/2.
 */
open class CustomPlugin:Plugin<Project> {
    override fun apply(project: Project) {
        val extensions = project.extensions.create("releaseInfo", ReleaseInfoNew::class.java)
        project.afterEvaluate {
            println("CustomPlugin plugin versionCode: ${extensions.versionCode},versionName: ${extensions.versionName}")
        }
        project.task("hello") {
            println("Hello from the GreetingPlugin")

        }
        project.tasks.create("createReleaseInfoTask",ReleaseInfoTask::class.java)
    }
}
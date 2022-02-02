package com.fastframework.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Jerry on 2022/2/2.
 */
open class CustomPlugin:Plugin<Project> {
    override fun apply(target: Project) {
        val extensions = target.extensions.create("releaseInfo", ReleaseInfoNew::class.java)
        target.afterEvaluate {
            println("CustomPlugin afterEvaluate pluginName: ${extensions.name}")
        }
    }
}
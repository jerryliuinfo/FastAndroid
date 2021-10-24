package com.fastandroid.plugin


import org.gradle.api.Project
/**
 * Created by Jerry on 2021/10/23.
 */
class HenCoder implements org.gradle.api.Plugin<Project>{
    @Override
    void apply(Project target) {
        def extension = target.extensions.create("hencoder",HencoderExtension)

        target.afterEvaluate {
            println "Hello ${extension.name}"
        }
    }
}

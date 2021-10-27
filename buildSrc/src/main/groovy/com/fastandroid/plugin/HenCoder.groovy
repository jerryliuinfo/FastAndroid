package com.fastandroid.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
/**
 * Created by Jerry on 2021/10/23.
 */
class HenCoder implements org.gradle.api.Plugin<Project>{
    @Override
    void apply(Project target) {
        //所有的扩展
        def extension = target.extensions.create("hencoder",HencoderExtension)

        target.afterEvaluate {
            println "Hello name: ${extension.name}, value:${extension.value}"
        }

        def transform = new HenCodeTransorm()
        //拿到com.android.application 这个插件的扩展，去干预 android 的打包流程
        def baseExtension = target.extensions.getByType(BaseExtension)
        //将 HenCodeTransorm 注入到打包过程
//        baseExtension.registerTransform(transform)
    }
}

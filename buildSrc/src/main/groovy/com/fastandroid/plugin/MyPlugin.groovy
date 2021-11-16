package com.fastandroid.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
/**
 * Created by Jerry on 2021/10/23.
 */
class MyPlugin implements org.gradle.api.Plugin<Project>{
    @Override
    void apply(Project target) {
        //所有的扩展
        def extension = target.extensions.create("myPlugin",ExtensionBean)

        target.afterEvaluate {
            println "Hello MyPlugin: ${extension.name}, value:${extension.value}"
        }

        def transform = new MyTransorm()
        //拿到com.android.application 这个插件的扩展，去干预 android 的打包流程
        def baseExtension = target.extensions.getByType(BaseExtension)
        //将 HenCodeTransorm 注入到打包过程
//        baseExtension.registerTransform(transform)
    }
}

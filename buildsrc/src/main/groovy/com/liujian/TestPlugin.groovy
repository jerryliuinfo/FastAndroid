package com.liujian

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class TestPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        System.out.println("========================")
        System.out.println("这是第TestPlugin插件!")
        def android = project.extensions.getByType(AppExtension)
        AsmInjectTrans asmInjectTrans = new AsmInjectTrans()
        android.registerTransform(asmInjectTrans)
    }
}
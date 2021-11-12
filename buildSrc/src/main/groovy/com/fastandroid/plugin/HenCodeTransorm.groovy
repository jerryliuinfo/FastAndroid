package com.fastandroid.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils;

/**
 * Created by Jerry on 2021/10/25.
 */
class HenCodeTransorm extends Transform{
    @Override
    String getName() {
        return "hencoder"
    }

    /**
     * 定义 Transform的输入范围为 class 或者 jar 文件
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 定义项目范围，例如主项目、子项目、第三方库、所有?
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        def inputs = transformInvocation.inputs
        def outputProvider = transformInvocation.outputProvider
        inputs.each {
            it.jarInputs.each {
                File dest = outputProvider.getContentLocation(it.name,it.contentTypes,it.scopes, Format.JAR)
                def msg = "Jar: ${it.file}" +" , dest:${dest}"
                println(msg)
                FileUtils.copyFile(it.file,dest)
            }

            it.directoryInputs.each {
                File dest = outputProvider.getContentLocation(it.name,it.contentTypes,it.scopes, Format.DIRECTORY)
//                rintln("Dir: ${it.file},Dest:${dest}")
                def msg = "Dir: ${it.file}" +" , dest:${dest}"
                println(msg)


                FileUtils.copyDirectory(it.file,dest)
            }
        }
    }
}

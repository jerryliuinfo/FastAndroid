package com.liujian

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

public class AsmInjectTrans extends Transform{


    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
        transformInvocation.inputs.each { TransformInput input ->
            //对类型为“文件夹”的input进行遍历
            input.directoryInputs.each { DirectoryInput directoryInput ->
                //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
                if (directoryInput.file.isDirectory()) {
                    // println "==== directoryInput.file = " + directoryInput.file
                    directoryInput.file.eachFileRecurse { File file ->
                        def name = file.name
                        //  println "==== directoryInput file name ==== " + file.getAbsolutePath()
                        if (name.endsWith(".class")
                                && !name.endsWith("R.class")
                                && !name.endsWith("BuildConfig.class")
                                && !name.contains("R\$")) {
                            println "==== directoryInput file name ==== " + file.getAbsolutePath()

                            ClassReader classReader = new ClassReader(file.bytes)
                            ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                            AsmClassVisitor classVisitor = new AsmClassVisitor(Opcodes.ASM5, classWriter)
                            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
                            byte[] bytes = classWriter.toByteArray()
                            File destFile = new File(file.parentFile.absoluteFile, name)
                            //project.logger.error "==== 重新写入的位置->lastFilePath === " + destFile.getAbsolutePath()
                            FileOutputStream fileOutputStream = new FileOutputStream(destFile)
                            fileOutputStream.write(bytes)
                            fileOutputStream.close()
                        }
                    }
                }


                // 获取output目录
                def dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)

                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
            //对类型为jar文件的input进行遍历
            input.jarInputs.each { JarInput jarInput ->

                //jar文件一般是第三方依赖库jar文件
                // 重命名输出文件（同目录copyFile会冲突）
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")) {
                    jarName = jarName.substring(0, jarName.length() - 4)
                }
                //生成输出路径
                def dest = transformInvocation.outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                //将输入内容复制到输出
                FileUtils.copyFile(jarInput.file, dest)
            }
        }
    }

    //用于指明本Transform的名字，也是代表该Transform的task的名字
    @Override
    String getName() {
        return "AsmInject"
    }

    //指明Transform的输入类型，可以作为输入过滤的手段
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    //指明Transform的作用域
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    //指明是否是增量构建
    @Override
    boolean isIncremental() {
        return false
    }
}
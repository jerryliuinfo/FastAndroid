package com.liujian;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


/**
 * Created by liujian on 2018/4/12.
 */

public class AsmClassVisitor extends ClassVisitor {

    public AsmClassVisitor(int api) {
        super(api);
    }

    public AsmClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MyLog.log("=====---------- visitMethod ----------=====");
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        AsmMethodVisitor mMethodVisitor = new AsmMethodVisitor(Opcodes.ASM5, mv, access, name, desc);
        return mMethodVisitor;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}

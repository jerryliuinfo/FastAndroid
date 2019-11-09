package com.liujian;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

/**
 * Created by liujian on 2018/4/12.
 */

public class AsmMethodVisitor extends AdviceAdapter{
    private String methodName;
    private String methodDes;

    public AsmMethodVisitor(int api, MethodVisitor mv, int access, String name, String desc) {
        super(api, mv, access, name, desc);
        methodName = name;
        methodDes = desc;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    @Override
    protected void onMethodEnter() {
        if(/*"launch".equals(methodName) || */("onClick".equals(methodName) && "(Landroid/view/View;)V".equals(methodDes))  ){
            // mv.visitLdcInsn("liujian");
            MyLog.log(String.format("***********************onClick method methodName = %s, methodDes = %s***********************", methodName,methodDes));

            mv.visitVarInsn(ALOAD, 1);
            // mv.visitInsn(ICONST_1);
            //mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/view/View", "getId", "()I", false);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/apache/fastandroid/LogUtils", "system", "(Landroid/view/View;)V", false);

        }
    }

    @Override
    protected void onMethodExit(int opcode) {
        super.onMethodExit(opcode);
    }
}

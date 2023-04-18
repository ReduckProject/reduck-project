package net.reduck.asm.transfer;

import org.objectweb.asm.*;

public class TransferDump implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        RecordComponentVisitor recordComponentVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, "net/reduck/asm/transfer/Transfer", null, "java/lang/Object", null);

        classWriter.visitSource("Transfer.java", null);

        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(7, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(RETURN);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLocalVariable("this", "Lnet/reduck/asm/transfer/Transfer;", null, label0, label1, 0);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_STATIC, "tranfer", "(Lnet/reduck/asm/transfer/Apple;)Lnet/reduck/asm/transfer/AppleTO;", null, null);
            methodVisitor.visitParameter("apple", 0);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(10, label0);
            methodVisitor.visitTypeInsn(NEW, "net/reduck/asm/transfer/AppleTO");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "net/reduck/asm/transfer/AppleTO", "<init>", "()V", false);
            methodVisitor.visitVarInsn(ASTORE, 1);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(11, label1);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/Apple", "getName", "()Ljava/lang/String;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/AppleTO", "setName", "(Ljava/lang/String;)V", false);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(12, label2);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/Apple", "getHeight", "()I", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/AppleTO", "setHeight", "(I)V", false);
            Label label3 = new Label();
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLineNumber(13, label3);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/Apple", "getWidth", "()J", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/AppleTO", "setWidth", "(J)V", false);
            Label label4 = new Label();
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLineNumber(14, label4);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/Apple", "getPassword", "()Ljava/lang/String;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/AppleTO", "setPassword", "(Ljava/lang/String;)V", false);
            Label label5 = new Label();
            methodVisitor.visitLabel(label5);
            methodVisitor.visitLineNumber(15, label5);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitInsn(ARETURN);
            Label label6 = new Label();
            methodVisitor.visitLabel(label6);
            methodVisitor.visitLocalVariable("apple", "Lnet/reduck/asm/transfer/Apple;", null, label0, label6, 0);
            methodVisitor.visitLocalVariable("appleTO", "Lnet/reduck/asm/transfer/AppleTO;", null, label1, label6, 1);
            methodVisitor.visitMaxs(3, 2);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}

package net.reduck.asm.transfer;

import net.reduck.asm.DynamicClassLoader;
import org.objectweb.asm.*;

import java.lang.reflect.Method;

public class AppleTransformerDump implements Opcodes {

    public static byte[] dump() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        RecordComponentVisitor recordComponentVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, "net/reduck/asm/transfer/AppleTransformer", "Ljava/lang/Object;Lnet/reduck/asm/transfer/ObjectTransformer<Lnet/reduck/asm/transfer/Apple;Lnet/reduck/asm/transfer/AppleTO;>;", "java/lang/Object", new String[]{"net/reduck/asm/transfer/ObjectTransformer"});

        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "transfer", "(Lnet/reduck/asm/transfer/Apple;)Lnet/reduck/asm/transfer/AppleTO;", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitTypeInsn(NEW, "net/reduck/asm/transfer/AppleTO");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "net/reduck/asm/transfer/AppleTO", "<init>", "()V", false);
            methodVisitor.visitVarInsn(ASTORE, 2);

            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/Apple", "getName", "()Ljava/lang/String;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/AppleTO", "setName", "(Ljava/lang/String;)V", false);

            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/Apple", "getHeight", "()I", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/AppleTO", "setHeight", "(I)V", false);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/Apple", "getWidth", "()J", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/AppleTO", "setWidth", "(J)V", false);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitVarInsn(ALOAD, 1);

            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/Apple", "getPassword", "()Ljava/lang/String;", false);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/AppleTO", "setPassword", "(Ljava/lang/String;)V", false);
            methodVisitor.visitVarInsn(ALOAD, 2);
            methodVisitor.visitInsn(ARETURN);
            methodVisitor.visitMaxs(3, 3);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, "transfer", "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitTypeInsn(CHECKCAST, "net/reduck/asm/transfer/Apple");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/AppleTransformer", "transfer", "(Lnet/reduck/asm/transfer/Apple;)Lnet/reduck/asm/transfer/AppleTO;", false);
            methodVisitor.visitInsn(ARETURN);
            methodVisitor.visitMaxs(2, 2);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }

    public static byte[] dump2() throws Exception {

        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        RecordComponentVisitor recordComponentVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER, "net/reduck/asm/transfer/AppleTransformer", "Ljava/lang/Object;Lnet/reduck/asm/transfer/ObjectTransformer<Lnet/reduck/asm/transfer/Apple;Lnet/reduck/asm/transfer/AppleTO;>;", "java/lang/Object", new String[]{"net/reduck/asm/transfer/ObjectTransformer"});

        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitMaxs(1, 1);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "transfer", "(Lnet/reduck/asm/transfer/Apple;)Lnet/reduck/asm/transfer/AppleTO;", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitInsn(ARETURN);
            methodVisitor.visitMaxs(1, 2);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, "transfer", "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitTypeInsn(CHECKCAST, "net/reduck/asm/transfer/Apple");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/AppleTransformer", "transfer", "(Lnet/reduck/asm/transfer/Apple;)Lnet/reduck/asm/transfer/AppleTO;", false);
            methodVisitor.visitInsn(ARETURN);
            methodVisitor.visitMaxs(2, 2);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        String name = "net.reduck.asm.transfer.AppleTransformer";
        DynamicClassLoader loader = new DynamicClassLoader(Thread.currentThread().getContextClassLoader());
//        TransformerBuilder builder = new TransformerBuilder(name, Apple.class, AppleTO.class);
//        byte[] data = builder.start().transfer().end().build();
        byte[] data = dump2();
        loader.loadClass(name, data);
        Method transferMethod = loader.loadClass(name).getMethods()[0];

        System.out.println(transferMethod.getName());
    }
}

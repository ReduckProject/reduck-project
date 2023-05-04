package net.reduck.asm.transfer;

import org.objectweb.asm.MethodVisitor;

import java.beans.PropertyDescriptor;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author Gin
 * @since 2023/2/3 17:35
 */
public class AsmHelper {

    public static void instance(String name, MethodVisitor methodVisitor) {
        methodVisitor.visitTypeInsn(NEW, name);
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, name, "<init>", "()V", false);
        methodVisitor.visitVarInsn(ASTORE, 2);
    }

    public static void set(PropertyDescriptor get, PropertyDescriptor set, MethodVisitor methodVisitor) {
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/Apple", "getName", "()Ljava/lang/String;", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/reduck/asm/transfer/AppleTO", "setName", "(Ljava/lang/String;)V", false);

    }

    private static void setInt() {

    }

    private String obtainPathName(String name) {
        return name.replace(".", "/");
    }

    private String obtainPathName(Class<?> type) {
        return type.getName().replace(".", "/");
    }

}

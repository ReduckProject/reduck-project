package net.reduck.core;

import net.reduck.core.desc.InstanceDesc;
import net.reduck.core.desc.TypeDesc;
import net.reduck.core.internal.asm.MethodVisitor;
import net.reduck.core.internal.asm.Opcodes;

/**
 * @author Gin
 * @since 2023/2/6 15:06
 */
public class AsmClassCreator implements Opcodes {

    private static InstanceDesc newInstance(MethodVisitor methodVisitor, Class<?> targetType, int index) {
        String type = getType(targetType);
        methodVisitor.visitCode();
        methodVisitor.visitTypeInsn(NEW, type);
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, type, "<init>", "()V", false);
        methodVisitor.visitVarInsn(ASTORE, index);

        return new InstanceDesc(new TypeDesc(type), index);
    }

    public void invokeSetter(MethodVisitor target) {

    }
    private static String getType(Class<?> targetType) {
        return targetType.getName().replace(".", "/");
    }
}

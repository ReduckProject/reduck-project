package net.reduck.core.desc;

import net.reduck.core.internal.asm.MethodVisitor;
import net.reduck.core.internal.asm.Opcodes;

/**
 * @author Gin
 * @since 2023/2/7 14:37
 */
public class PrimitiveHelper implements Opcodes {

    public static void boxing(Class<?> sourceType, Class<?> targetType, MethodVisitor methodVisitor) {
        if (sourceType.isPrimitive() && targetType.isPrimitive()) {
            return;
        }

        switch (sourceType.getName()) {
            case "java.lang.String":
                return;
            case "long":
                methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                break;
            case "java.lang.Long":
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
                break;
            case "int":
                methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            case "java.lang.Integer":
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
                return;
            case "byte":
            case "java.lang.Byte":
                return;
            case "short":
            case "java.lang.Short":
                return;
            case "float":
            case "java.lang.Float":
                return;
            case "double":
            case "java.lang.Double":
            case "boolean":
            case "java.lang.Boolean":
                return;
            case "char":
            case "java.lang.Character":
                return;
            case "java.math.BigDecimal":
                return;
            case "java.sql.Time":
                return;
        }
    }

    public static class Box {
        public static void boxing(Class<?> boxingType) {
//            switch (boxingType) {
//                case long.class:
//
//            }
        }

        public static void unboxing(Class<?> sourceType, Class<?> targetType) {
            if (sourceType.isPrimitive() && targetType.isPrimitive()) {
                return;
            }
        }
    }
}


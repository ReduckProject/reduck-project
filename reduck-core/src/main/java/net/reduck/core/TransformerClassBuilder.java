package net.reduck.core;

import net.reduck.core.desc.InstanceDesc;
import net.reduck.core.desc.TypeDesc;
import net.reduck.core.desc.TypeHelper;
import net.reduck.core.internal.asm.ClassWriter;
import net.reduck.core.internal.asm.MethodVisitor;
import net.reduck.core.internal.asm.Opcodes;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gin
 * @since 2023/2/6 16:50
 */
public class TransformerClassBuilder implements Opcodes {
    private final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

    private final String name;
    private final Class<?> sourceClass;
    private final Class<?> targetClass;
    private MethodVisitor methodVisitor;

    public TransformerClassBuilder(String name, Class<?> sourceClass, Class<?> targetClass) {
        this.name = name;
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    public TransformerClassBuilder construct() {
        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER
                , obtainPathName(name)
                , "Ljava/lang/Object;L" + TypeHelper.getType(ObjectTransformer.class) + "<" + TypeHelper.getDesc(sourceClass) + TypeHelper.getType(targetClass) + ">;"
                , "java/lang/Object"
                , new String[]{ TypeHelper.getType(ObjectTransformer.class)}
        );

        methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();
        return this;
    }

    public byte[] build() {
        classWriter.visitEnd();
        return classWriter.toByteArray();
    }

    public TransformerClassBuilder transfer() {
        Map<String, PropertyDescriptor> sourceMap = getProperties(sourceClass);
        Map<String, PropertyDescriptor> targetMap = getProperties(targetClass);

        methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "transfer", "(" + TypeHelper.getDesc(sourceClass) + ")" + TypeHelper.getDesc(targetClass), null, null);
        methodVisitor.visitCode();
//        methodVisitor.visitInsn(ACONST_NULL);
//        methodVisitor.visitInsn(ARETURN);
//        methodVisitor.visitMaxs(0, 0);
//        methodVisitor.visitEnd();

        InstanceDesc desc = newInstance(targetClass, 2);

        for (Map.Entry<String, PropertyDescriptor> entry : sourceMap.entrySet()) {
            if(targetMap.containsKey(entry.getKey())) {
                methodVisitor.visitVarInsn(ALOAD, 2);
                methodVisitor.visitVarInsn(ALOAD, 1);
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, TypeHelper.getType(sourceClass), entry.getValue().getReadMethod().getName(), TypeHelper.getGetterDesc(entry.getValue().getPropertyType()), false);
                methodVisitor.visitMethodInsn(INVOKEVIRTUAL, TypeHelper.getType(targetClass), entry.getValue().getWriteMethod().getName(), TypeHelper.getSetterDesc(entry.getValue().getPropertyType()));
            }
        }

        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(0, 0);
        methodVisitor.visitEnd();

        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, "transfer", "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
            methodVisitor.visitCode();
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitTypeInsn(CHECKCAST, TypeHelper.getType(sourceClass));
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, obtainPathName(name), "transfer", "(" + TypeHelper.getDesc(sourceClass) + ")" + TypeHelper.getDesc(targetClass), false);
            methodVisitor.visitInsn(ARETURN);
            methodVisitor.visitMaxs(2, 2);
            methodVisitor.visitEnd();
        }
        return this;
    }

    private InstanceDesc newInstance(Class<?> targetType, int index) {
        String type = TypeHelper.getType(targetType);
        methodVisitor.visitTypeInsn(NEW, type);
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, type, "<init>", "()V", false);
        methodVisitor.visitVarInsn(ASTORE, index);

        return new InstanceDesc(new TypeDesc(type), index);
    }

    private String obtainPathName(String name) {
        return name.replace(".", "/");
    }

    static Map<String, PropertyDescriptor> getProperties(Class<?> target) {
        PropertyDescriptor[] propertyDescriptors;
        try {
            propertyDescriptors = Introspector.getBeanInfo(target).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }

        Map<String, PropertyDescriptor> propertyDescriptorMap = new HashMap<>();

        Arrays.asList(propertyDescriptors)
                .forEach(propertyDescriptor -> propertyDescriptorMap.put(propertyDescriptor.getName(), propertyDescriptor));

        // remove Object#getClass
        propertyDescriptorMap.remove("class");
        return propertyDescriptorMap;
    }


}

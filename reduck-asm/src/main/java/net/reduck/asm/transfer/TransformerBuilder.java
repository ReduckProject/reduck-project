package net.reduck.asm.transfer;

import net.reduck.asm.DynamicClassLoader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Gin
 * @since 2023/2/3 16:43
 */
public class TransformerBuilder implements Opcodes{
    private final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    private final String name;
    private final Class<?> sourceType;

    private final Class<?> targetType;

    private MethodVisitor methodVisitor;

    private final Map<String, PropertyDescriptor> targetMap ;
    private final Map<String, PropertyDescriptor> sourceMap;

    public TransformerBuilder(String name, Class<?> sourceType, Class<?> targetType) {
        this.name = name;
        this.sourceType = sourceType;
        this.targetType = targetType;
        this.targetMap = MapperUtils.getProperties(targetType);
        this.sourceMap = MapperUtils.getProperties(sourceType);
    }

    public TransformerBuilder start() {
        classWriter.visit(V1_8, ACC_PUBLIC | ACC_SUPER
                , obtainPathName(name)
                , "Ljava/lang/Object;Lnet/reduck/asm/transfer/ObjectTransformer" + "<L" + obtainPathName(sourceType) + ";L" + obtainPathName(targetType) + ";>;"
                , "java/lang/Object"
                , new String[]{"net/reduck/asm/transfer/ObjectTransformer"}
        );

        methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
        return this;
    }

    public TransformerBuilder transfer(){
        methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "transfer", getDescriptor(), null, null);
        methodVisitor.visitCode();

        AsmHelper.instance(obtainPathName(targetType), methodVisitor);

        methodVisitor.visitInsn(ACONST_NULL);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(1, 2);
        methodVisitor.visitEnd();
        return this;
    }

    public TransformerBuilder end() {
        methodVisitor = classWriter.visitMethod(ACC_PUBLIC | ACC_BRIDGE | ACC_SYNTHETIC, "transfer", "(Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitTypeInsn(CHECKCAST, obtainPathName(sourceType));
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, obtainPathName(name), "transfer", getDescriptor(), false);
        methodVisitor.visitInsn(ARETURN);
        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();

        return this;
    }

    private String getDescriptor() {
        return "(L" + obtainPathName(sourceType) + ";)L" + obtainPathName(targetType) + ";";
    }

    public byte[] build() {
        classWriter.visitEnd();
        return classWriter.toByteArray();
    }

    private String obtainPathName(String name) {
        return name.replace(".", "/");
    }

    private String obtainPathName(Class<?> type) {
        return type.getName().replace(".", "/");
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String name = "net.reduck.asm.transfer.TestBuild";
        DynamicClassLoader loader = new DynamicClassLoader(Apple.class.getClassLoader());
        TransformerBuilder builder = new TransformerBuilder(name, Apple.class, AppleTO.class);
        byte[] data = builder.start().transfer().end().build();
        Class<?> transferType = loader.loadClass(name, data);
        Method transferMethod = transferType.getMethods()[0];
        Object result = transferMethod.invoke(transferType.newInstance(), new Apple());
        System.out.println(transferMethod.getName());
        System.out.println(result);

        Map<String, PropertyDescriptor> descriptorMap = MapperUtils.getProperties(Apple.class);

        System.out.println();
    }
}

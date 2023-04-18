package net.reduck.asm.demo;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.FieldVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.commons.LocalVariablesSorter;

import java.io.FileOutputStream;
import java.io.IOException;

import static jdk.internal.org.objectweb.asm.Opcodes.*;
//import static sun.reflect.ClassDefiner.defineClass;

/**
 * @author Reduck
 * @since 2022/12/6 14:38
 */
public class ClassWriterTest {

    public static void main(String[] args) {
        // 创建ClassWriter对象，定义一个你所创建class的全限定名、父类、权限等
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

        //生成默认的构造方法
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
//生成构造方法的字节码指令
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        //生成成员变量
        FieldVisitor fv = cw.visitField(ACC_PRIVATE, "name", "Ljava/lang/String;", null, null);
        fv.visitEnd();
        fv = cw.visitField(ACC_PRIVATE, "age", "J", null, null);
        fv.visitEnd();

        //生成静成员变量
        fv = cw.visitField(ACC_PUBLIC + ACC_STATIC, "num", "J", null, 101L);
        fv.visitEnd();

        //生成常量
        fv = cw.visitField(ACC_PUBLIC + ACC_STATIC + ACC_FINAL, "ADMIN_NAME", "Ljava/lang/String;", null, "赵进伟");
        fv.visitEnd();

        //生成成员方法
        mv = cw.visitMethod(ACC_PUBLIC, "method", "(Ljava/lang/String;)I", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitVarInsn(ALOAD, 1);

        // 调用静态方法
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("method ing");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        // 创建局部变量
        LocalVariablesSorter lvs = new LocalVariablesSorter(ACC_PUBLIC, "(Ljava/lang/String;)I", mv);
//创建ArrayList对象
//new ArrayList,分配内存但不做初始化操作
        mv.visitTypeInsn(NEW, "java/util/ArrayList");
//复制栈里元素(对象地址)，再次压入
        mv.visitInsn(DUP);
//弹出一个对象所在地址，进行初始化操作，构造函数默认为空，此时栈大小为1
        mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V", false);
        int time = lvs.newLocal(Type.getType("java/util/List"));
        mv.visitVarInsn(ASTORE, time);
        mv.visitVarInsn(ALOAD, time);
//创建StringBuilder对象
        mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        time = lvs.newLocal(Type.getType("java/lang/StringBuilder"));
        mv.visitVarInsn(ASTORE, time);
        mv.visitVarInsn(ALOAD, time);

        //调用StringBuilder的append方法
        mv.visitLdcInsn("Hello Java Asm StringBuilder!");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

        //调用StringBuilder的append方法
        mv.visitLdcInsn("Hello Java Asm StringBuilder!");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);

        //调用静态方法
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "nanoTime", "()J", false);
        time = lvs.newLocal(Type.LONG_TYPE);
        mv.visitVarInsn(LSTORE, time);
        mv.visitLdcInsn(10);
        mv.visitLdcInsn(11);
//加运算10+11
        mv.visitInsn(IADD);
        mv.visitInsn(IRETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        //生成静态方法
        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "staticMethod", "(Ljava/lang/String;)V", null, null);
//生成静态方法中的字节码指令
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitLdcInsn("Hello Java Asm!");
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        // 获取生成的class文件对应的二进制
        byte[] code = cw.toByteArray();

        //将二进制写到本地磁盘上
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("HelloAsm.class");
            fos.write(code);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //直接将二进制流加载到内存中 name可以传入null
        /*注意：defineClass是ClassLoader的protected方法<本类、子类、同包类中才可以调用>，所以自己想办法吧*/
//
//        this.getClass().getClassLoader()
//        Class<?> clazz=("com.zjw.HelloAsm", code, 0, code.length);
    }

}

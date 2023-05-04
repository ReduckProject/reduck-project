package net.reduck.jpa.test.doc;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Reduck
 * @since 2022/12/9 11:24
 */
public class ApiDocTest {

    @Test
    public void test() throws IOException {
        // 创建 java 项目 builder 对象
        JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder();
        // 添加 java 源文件
        javaProjectBuilder.addSourceTree(new File(System.getProperty("user.dir") + "/src/main/java/net/reduck/jpa/test/controller"));

        Collection<JavaClass> classes = javaProjectBuilder.getClasses();

        for (JavaClass javaClass : classes) {

            for (JavaMethod javaMethod : javaClass.getMethods()) {
                System.out.println(javaMethod.getCallSignature());
            }
        }
    }
}

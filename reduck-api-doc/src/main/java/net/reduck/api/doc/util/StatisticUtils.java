package net.reduck.api.doc.util;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Reduck
 * @since 2022/12/12 14:33
 */
public class StatisticUtils {

    public static List<String> getAllUrl(String path) throws IOException {
        File file = new File(path);

        JavaProjectBuilder builder = new JavaProjectBuilder();
        if (file.isDirectory()) {
            builder.addSourceTree(file);
        } else {
            builder.addSource(file);
        }

        List<String> urls = new ArrayList<>();
        for (JavaClass javaClass : builder.getClasses()) {
            if (!ClassHelper.containController(javaClass)) {
                continue;
            }

            String prefix = ClassHelper.getUrl(javaClass.getAnnotations());
            for (JavaMethod javaMethod : javaClass.getMethods()) {
                if (!ClassHelper.containRequestMapping(javaMethod)) {
                    continue;
                }
                String suffix = ClassHelper.getUrl(javaMethod.getAnnotations());
                urls.add(prefix + suffix);
            }
        }

        return urls;
    }
}

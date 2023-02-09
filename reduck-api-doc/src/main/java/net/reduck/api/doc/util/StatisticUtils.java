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
            if (!MappingHelper.containController(javaClass)) {
                continue;
            }

            String prefix = MappingHelper.getUrl(javaClass.getAnnotations());
            for (JavaMethod javaMethod : javaClass.getMethods()) {
                if (!MappingHelper.containRequestMapping(javaMethod)) {
                    continue;
                }
                String suffix = MappingHelper.getUrl(javaMethod.getAnnotations());
                urls.add(prefix + suffix);
            }
        }

        return urls;
    }

    public static void main(String[] args) throws IOException {
        List<String> urls = getAllUrl(System.getProperty("user.home") + "/Documents/workspace/dsm/backend/src/main/java/com/secsmart/backend");

        urls.forEach(System.out::println);
        System.out.println(urls.size());
    }
}

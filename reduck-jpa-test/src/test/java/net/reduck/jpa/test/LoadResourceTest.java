package net.reduck.jpa.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileCopyUtils;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

/**
 * @author Gin
 * @since 2023/5/24 16:11
 */
public class LoadResourceTest {

    @Test
    public void test() {
        loadFile(FACTORIES_RESOURCE_LOCATION);
    }

    public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";

    @SneakyThrows
    public static void loadFile(String location) {

        ClassLoader classLoader = new URLClassLoader(new URL[]{Autowired.class.getProtectionDomain().getCodeSource().getLocation()}, null);

//        classLoader = PrivateKeyFinder.class.getClassLoader();
        Enumeration<URL> enumeration = classLoader.getResources(location);

        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();
            UrlResource resource = new UrlResource(url);
            System.out.println(new String(FileCopyUtils.copyToByteArray(resource.getInputStream())));;
        }
    }
}

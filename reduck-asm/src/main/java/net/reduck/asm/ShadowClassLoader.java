package net.reduck.asm;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Reduck
 * @since 2023/1/31 15:38
 */
public class ShadowClassLoader extends ClassLoader {
    private final List<String> parentExclusion = new ArrayList<String>();
    private final List<String> highlanders = new ArrayList<String>();

    public static final String[] DEFAULT_EXCLUDED_PACKAGES =
            new String[]{"java.", "javax.", "jdk.", "sun.", "oracle.", "com.sun.", "com.ibm.", "COM.ibm.",
                    "org.w3c.", "org.xml.", "org.dom4j.", "org.eclipse", "org.aspectj.", "net.sf.cglib",
                    "org.springframework.cglib", "org.apache.xerces.", "org.apache.commons.logging."};


    private static final ConcurrentMap<String, Class<?>> highlanderMap = new ConcurrentHashMap<String, Class<?>>();

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        for (String packageName : DEFAULT_EXCLUDED_PACKAGES) {
            if (name.startsWith(packageName)) {
                return super.loadClass(name);
            }
        }
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass != null) return loadedClass;
        return findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        System.out.println("Prepare find class");
        URL url;
        try {
            url = new File(System.getProperty("user.dir") + "/reduck-validator/target/classes/net/reduck/validator/PatternConstant.class")
                    .toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return urlToDefineClass(name, url, false);
    }

    private Class<?> urlToDefineClass(String name, URL res, boolean resolve) throws ClassNotFoundException {
        byte[] b;
        int p = 0;
        try {
            InputStream in = res.openStream();

            try {
                b = new byte[65536];
                while (true) {
                    int r = in.read(b, p, b.length - p);
                    if (r == -1) break;
                    p += r;
                    if (p == b.length) {
                        byte[] nb = new byte[b.length * 2];
                        System.arraycopy(b, 0, nb, 0, p);
                        b = nb;
                    }
                }
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new ClassNotFoundException("I/O exception reading class " + name, e);
        }

        Class<?> c;
        try {
            System.out.println(p);
            c = defineClass(name, b, 0, p);
        } catch (LinkageError e) {
            if (highlanders.contains(name)) {
                Class<?> alreadyDefined = highlanderMap.get(name);
                if (alreadyDefined != null) return alreadyDefined;
            }
            try {
                c = this.findLoadedClass(name);
            } catch (LinkageError e2) {
                throw e;
            }
            if (c == null) throw e;
        }

        if (highlanders.contains(name)) {
            Class<?> alreadyDefined = highlanderMap.putIfAbsent(name, c);
            if (alreadyDefined != null) c = alreadyDefined;
        }

        if (resolve) resolveClass(c);
        return c;
    }

    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
        Class<?> target = new ShadowClassLoader().loadClass("net.reduck.validator.PatternConstant");
//        Class<?> target1 = new ShadowClassLoader().loadClass("net.reduck.validator.PatternConstant");
//        Class<?> target2 = new ShadowClassLoader().loadClass("net.reduck.validator.PatternConstant");
//        Class<?> target3 = new ShadowClassLoader().loadClass("net.reduck.validator.PatternConstant");
//        Class<?> target4 = new ShadowClassLoader().loadClass("net.reduck.validator.PatternConstant");
        ShadowClassLoader shadowClassLoader = new ShadowClassLoader();
        Class<?> target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");
        target5 = shadowClassLoader.loadClass("net.reduck.validator.PatternConstant");

        System.out.println(target.getClassLoader());
    }
}

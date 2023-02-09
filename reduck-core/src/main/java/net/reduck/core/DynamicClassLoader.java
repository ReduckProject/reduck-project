package net.reduck.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Reduck
 * @since 2023/1/31 16:26
 */
public class DynamicClassLoader extends ClassLoader {

    public DynamicClassLoader(ClassLoader parent) {
        super(parent);
    }

    public DynamicClassLoader() {
    }

    public Class<?> loadClass(String name, byte[] classData) {
        try {
            return super.loadClass(name);
        } catch (ClassNotFoundException e) {
            return defineClass(name, classData, 0, classData.length);
        }
    }

    public Class<?> loadClass(String name, String path) throws MalformedURLException, ClassNotFoundException {
        try {
            return super.loadClass(name);
        } catch (ClassNotFoundException e) {
            byte[] classData = readResource(name, path);
            return defineClass(name, classData, 0, classData.length);
        }
    }

    public byte[] readResource(String name, String path) throws MalformedURLException, ClassNotFoundException {
        URL res = new File(path).toURI().toURL();
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
        byte[] data = new byte[p];

        System.arraycopy(b, 0, data, 0, p);
        return data;
    }


    public static void main(String[] args) throws ClassNotFoundException, MalformedURLException {
        DynamicClassLoader loader = new DynamicClassLoader();
        Class<?> target5 = loader.loadClass("net.reduck.validator.PatternConstant", System.getProperty("user.dir") + "/reduck-validator/target/classes/net/reduck/validator/PatternConstant.class");

        System.out.println(target5.getClassLoader());

    }
}
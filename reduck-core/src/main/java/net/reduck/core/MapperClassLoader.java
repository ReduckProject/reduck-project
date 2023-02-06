package net.reduck.core;

/**
 * @author Gin
 * @since 2023/2/6 18:11
 */
public class MapperClassLoader extends ClassLoader {
    private final String prefix = "net.reduck.core.proxy.";
    public MapperClassLoader(ClassLoader parent) {
        super(parent);
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        if(name.startsWith(prefix)) {
//            if()
        }
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        return defineClass(n)
    }
}

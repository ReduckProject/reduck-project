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
        if (name.startsWith(prefix)) {
            Class<?> defined = findLoadedClass(name);
            if (defined != null) {
                return defined;
            }
        }
        return super.loadClass(name);
    }

    public Class<?> loadClass(Class<?> sourceClass, Class<?> targetClass) throws ClassNotFoundException {
        String name = prefix + sourceClass.getSimpleName() + "2" + targetClass.getSimpleName();

        Class<?> defined = findLoadedClass(name);
        if (defined != null) {
            return defined;
        }

        byte[] classFileData = new TransformerClassBuilder(name, sourceClass, targetClass).construct().transfer().build();
        return defineClass(name, classFileData, 0, classFileData.length);
    }
}

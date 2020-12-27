package fr.unice.miage.classloader;

public abstract class ClassLoaderCustom extends ClassLoader {
    public Class<?> loadClassNoDeep(String name) throws ClassNotFoundException {
        Class<?> cl = super.findLoadedClass(name);
        if(cl != null)
            return cl;
        return findClassNoDeep(name);
    }

    public abstract Class<?> findClassNoDeep(String name) throws ClassNotFoundException;
}

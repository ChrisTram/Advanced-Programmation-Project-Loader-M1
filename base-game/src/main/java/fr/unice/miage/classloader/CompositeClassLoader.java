package fr.unice.miage.classloader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompositeClassLoader extends ClassLoaderCustom{
    protected final Collection<ClassLoaderCustom> classLoaders;
    public CompositeClassLoader(ClassLoaderCustom... classLoaders) {
        this.classLoaders = List.of(classLoaders);
    }

    CompositeClassLoader(Collection<ClassLoaderCustom> classLoaders) {
        this.classLoaders = classLoaders;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        for (ClassLoaderCustom cl : classLoaders) {
            try {
                return cl.loadClassNoDeep(name);
            } catch (ClassNotFoundException ignored) {}
        }
        return super.findClass(name);
    }

    @Override
    public Enumeration<URL> findResources(String name) throws IOException {
        return new EnumerationList(
                classLoaders.stream()
                        .map(s -> {
                            try {
                                return s.getResources(name);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );
    }

    @Override
    protected URL findResource(String name) {
        for (ClassLoader cl : classLoaders) {
                URL url = cl.getResource(name);
                if(url != null)
                    return url;
        }
        return super.findResource(name);
    }

    @Override
    public Class<?> findClassNoDeep(String name) throws ClassNotFoundException {
        return findClass(name);
    }
}

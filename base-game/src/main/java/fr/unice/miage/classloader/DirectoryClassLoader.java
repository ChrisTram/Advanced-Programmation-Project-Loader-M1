package fr.unice.miage.classloader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

class DirectoryClassLoader extends ClassLoaderCustom {
    private final File path;
    private final Collection<ClassLoaderCustom> classPath;

    public DirectoryClassLoader(File path, Collection<ClassLoaderCustom> classPath) {
        this.path = path;
        this.classPath = classPath;
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String filePath = name.replace('.', '/');
        File file = new File(path.getPath() + '/' + filePath+".class");
        if (file.exists()) {
            try {
                byte[] bytecode = Files.readAllBytes(file.toPath());
                return defineClass(name,bytecode,0,bytecode.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (ClassLoaderCustom classLoader : classPath) {
            if(classLoader == this)
                continue;
            try {
                Class<?> tclass = classLoader.loadClassNoDeep(name);
                if(tclass !=null)
                    return tclass;
            } catch (ClassNotFoundException ignore) {}
        }
        return super.findClass(name);
    }

    @Override
    protected URL findResource(String name) {
        File file = new File(path.getPath() + '/' + name);
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException ignore) {
        }
        return null;
    }

    @Override
    public Enumeration<URL> findResources(String name) throws IOException {
        URL url = findResource(name);
        if(url == null)
            return super.findResources(name);
        return Collections.enumeration(List.of(url));
    }

    @Override
    public Class<?> findClassNoDeep(String name) throws ClassNotFoundException {
        String filePath = name.replace('.', '/');
        File file = new File(path.getPath() + '/' + filePath+".class");
        if (file.exists()) {
            try {
                byte[] bytecode = Files.readAllBytes(file.toPath());
                return defineClass(name,bytecode,0,bytecode.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.findClass(name);
    }
}

package fr.unice.miage.classloader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

class DeflateArchiveClassLoader extends ClassLoaderCustom {
    private File archive;
    private final Collection<ClassLoaderCustom> classPath;

    public DeflateArchiveClassLoader(File archive, Collection<ClassLoaderCustom> classPath) {
        this.archive = archive;
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            String objectFile = name.replace('.', '/') + ".class";
            JarFile file = new JarFile(archive);
            ZipEntry entry = file.getEntry(objectFile);
            if(entry != null) {
                byte[] bytecode = file.getInputStream(entry).readAllBytes();
                file.close();
                return defineClass(name, bytecode, 0, bytecode.length);
            }
            file.close();
        } catch (IOException ignore) {
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
        try {
            JarFile file = new JarFile(archive);
            ZipEntry entry = file.getEntry(name);
            file.close();
            if(entry != null)
                return new URL("jar:file:/"+archive+"!/"+name);
        } catch (IOException ignored) {
        }
        return super.findResource(name);
    }

    @Override
    protected Enumeration<URL> findResources(String name) throws IOException {
        try {
            JarFile file = new JarFile(archive);
            ZipEntry entry = file.getEntry(name);
            file.close();
            if(entry != null)
                return Collections.enumeration(
                        List.of(new URL("jar:file:/"+archive+"!/"+name))
                );
        } catch (IOException ignored) {
        }
        return super.findResources(name);
    }

    @Override
    public Class<?> findClassNoDeep(String name) throws ClassNotFoundException {
        try {
            String objectFile = name.replace('.', '/') + ".class";
            JarFile file = new JarFile(archive);
            ZipEntry entry = file.getEntry(objectFile);
            if(entry != null) {
                byte[] bytecode = file.getInputStream(entry).readAllBytes();
                file.close();
                return defineClass(name, bytecode, 0, bytecode.length);
            }
            file.close();
        } catch (IOException ignore) {
        }
        return null;
    }
}

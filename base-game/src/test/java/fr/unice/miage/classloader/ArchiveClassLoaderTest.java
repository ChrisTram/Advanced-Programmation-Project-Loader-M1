package fr.unice.miage.classloader;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ArchiveClassLoaderTest {
    @Test
    void test_import_zip() throws URISyntaxException, ClassNotFoundException {
        URL plugin = ArchiveClassLoaderTest.class.getResource("plugin.zip");
        assertNotNull(plugin);
        File file = new File(plugin.toURI().getPath());
        assertTrue(file.exists());
        assertTrue(file.isFile());
        ClassLoader cl = new DeflateArchiveClassLoader(file, new ArrayList<>());
        Class<?> tclass = cl.loadClass("fr.example.ExamplePlugin");
        assertNotNull(tclass);
        assertEquals(tclass.getName(), "fr.example.ExamplePlugin");
    }

    @Test
    void test_import_jar() throws URISyntaxException, ClassNotFoundException {
        URL plugin = ArchiveClassLoaderTest.class.getResource("plugin.jar");
        assertNotNull(plugin);
        File file = new File(plugin.toURI().getPath());
        assertTrue(file.exists());
        assertTrue(file.isFile());
        ClassLoader cl = new DeflateArchiveClassLoader(file, new ArrayList<>());
        Class<?> tclass = cl.loadClass("fr.example.ExamplePlugin");
        assertNotNull(tclass);
        assertEquals(tclass.getName(), "fr.example.ExamplePlugin");
    }

    @Test
    void test_import_dir() throws URISyntaxException, ClassNotFoundException {
        URL plugin = ArchiveClassLoaderTest.class.getResource("plugin");
        assertNotNull(plugin);
        File file = new File(plugin.toURI().getPath());
        assertTrue(file.exists());
        assertTrue(file.isDirectory());
        ClassLoader cl = new DirectoryClassLoader(file, new ArrayList<>());
        Class<?> tclass = cl.loadClass("fr.example.ExamplePlugin");
        assertNotNull(tclass);
        assertEquals(tclass.getName(), "fr.example.ExamplePlugin");
    }
}
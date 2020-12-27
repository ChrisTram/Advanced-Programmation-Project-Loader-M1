package fr.unice.miage.classloader;

import java.net.URL;
import java.util.Enumeration;
import java.util.List;

public class EnumerationList implements Enumeration<URL> {
    private final List<Enumeration<URL>> enumerationList;

    public EnumerationList(List<Enumeration<URL>> enumerationList) {
        this.enumerationList = enumerationList;
        while (!enumerationList.get(0).hasMoreElements())
            enumerationList.remove(0);
    }

    @Override
    public boolean hasMoreElements() {
        return !enumerationList.isEmpty();
    }

    @Override
    public URL nextElement() {
        URL e = enumerationList.get(0).nextElement();
        while (!enumerationList.isEmpty() && !enumerationList.get(0).hasMoreElements())
            enumerationList.remove(0);
        return e;
    }
}

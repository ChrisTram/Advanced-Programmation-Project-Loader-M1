package fr.unice.miage.classloader;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class OneManyClassLoader extends CompositeClassLoader {
    public OneManyClassLoader(File[] paths) {
        super(new ArrayList<>());
        this.classLoaders.addAll(Arrays.stream(paths)
                .map(f -> {
                            String file = f.getPath();
                            return file.endsWith(".zip") || file.endsWith(".jar") ?
                                    new DeflateArchiveClassLoader(f, this.classLoaders) :
                                    f.isDirectory() ?
                                            new DirectoryClassLoader(f, this.classLoaders) :
                                            null;
                        }
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toCollection(ArrayList::new)));
    }
}

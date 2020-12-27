package fr.unice.miage.engine;

import fr.unice.miage.engine.gui.GameGUI;
import fr.unice.miage.game.Plugin;
import fr.unice.miage.game.gamemode.Gamemode;
import fr.unice.miage.game.gamemode.ModBuilder;
import fr.unice.miage.game.reflection.RequireGamemode;
import fr.unice.miage.game.reflection.RequireModBuilder;
import fr.unice.miage.graph.DepthIterator;
import fr.unice.miage.graph.Node;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sort the mods into a working order.
 */
public class Loader {
    static List<Plugin> localPlugins() {
        List<Plugin> plugins = new LinkedList<>();
        Iterator<Plugin> p = ServiceLoader.load(Plugin.class).iterator();
        if(p.hasNext())
            p.forEachRemaining(plugins::add);
        return plugins;
    }

    static List<Plugin> plugins(ClassLoader classLoader) {
        return ServiceLoader.load(Plugin.class, classLoader).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

    private List<Plugin> loadPlugin(List<String> stringPaths) {
        URL[] paths = stringPaths.stream().map(s -> new File(s).toURI()).map(uri -> {
            try {
                return uri.toURL();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }).toArray(URL[]::new);
        return plugins(new URLClassLoader(paths));
    }

    public static List<Gamemode> load(List<ModBuilder> mods) {
        Stream<Gamemode> acc = Stream.empty();
        for (ModBuilder mod : mods) {
            Gamemode[] generates = mod.generates();
            acc = Stream.concat(acc, Arrays.stream(generates));
        }
        Gamemode[] gamemodes = acc.toArray(Gamemode[]::new);
        List<Node<Gamemode>> dependencies = new ArrayList<>(gamemodes.length);
        HashMap<Class<? extends Gamemode>, Set<Node<Gamemode>>> classToNode = new HashMap<>();

        // generate each node to link
        for (Gamemode gamemode : gamemodes) {
            Node<Gamemode> n = new Node<>(gamemode, new LinkedList<>());
            classToNode.computeIfAbsent(gamemode.getClass(), k -> new HashSet<>()).add(n);
            dependencies.add(n);
        }

        // link child to parents
        for (Gamemode gamemode : gamemodes) {
            Set<Node<Gamemode>> currentNodes = classToNode.get(gamemode.getClass());
            for (RequireGamemode annotation : gamemode.getClass().getAnnotationsByType(RequireGamemode.class))
                for (Class<? extends Gamemode> requireGamemodeClass : annotation.gamemodes())
                    for (Node<Gamemode> parent : classToNode.get(requireGamemodeClass))
                        for (Node<Gamemode> child : currentNodes)
                            child.add(parent);
        }

        // generate ordered loading gamemode
        List<Gamemode> gamemodes1 = new ArrayList<>(gamemodes.length);
        new DepthIterator<>(dependencies.iterator()).forEachRemaining(gamemodes1::add);
        return gamemodes1;
    }

    public static List<ModBuilder> sort(List<ModBuilder> mods) {
        List<Node<ModBuilder>> dependencies = new ArrayList<>(mods.size());
        HashMap<Class<? extends ModBuilder>, Set<Node<ModBuilder>>> classToNode = new HashMap<>();

        // generate each node to link
        for (ModBuilder mod : mods) {
            Node<ModBuilder> n = new Node<>(mod, new LinkedList<>());
            classToNode.computeIfAbsent(mod.getClass(), k -> new HashSet<>()).add(n);
            dependencies.add(n);
        }

        // link child to parents
        for (ModBuilder mod : mods) {
            Set<Node<ModBuilder>> currentNodes = classToNode.get(mod.getClass());
            for (RequireModBuilder annotation : mod.getClass().getAnnotationsByType(RequireModBuilder.class))
                for (Class<? extends ModBuilder> requireModsClass : annotation.mods())
                    for (Node<ModBuilder> parent : classToNode.get(requireModsClass))
                        for (Node<ModBuilder> child : currentNodes)
                            child.add(parent);
        }

        // generate ordered loading modBuilder
        List<ModBuilder> modsSorted = new ArrayList<>(mods.size());
        new DepthIterator<>(dependencies.iterator()).forEachRemaining(modsSorted::add);
        return modsSorted;
    }
}

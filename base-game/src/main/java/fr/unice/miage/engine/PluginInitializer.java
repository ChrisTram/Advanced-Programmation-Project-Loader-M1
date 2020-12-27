package fr.unice.miage.engine;

import fr.unice.miage.classloader.CompositeClassLoader;
import fr.unice.miage.classloader.OneManyClassLoader;
import fr.unice.miage.game.Plugin;
import fr.unice.miage.game.gamemode.ModBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PluginInitializer implements Initializable {
    private List<Plugin> plugins;
    private List<Plugin> activate;
    private List<String> defaultJarPaths;
    private List<String> defaultPaths;
    private Stage primaryStage;

    @FXML
    private VBox pluginsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPlugin();

        this.plugins = Loader.localPlugins();
        this.activate = plugins;
        defaultJarPaths = new ArrayList<>();
    }

    private static class ActivatePlugin {
        public final CheckBox activate;
        public final Plugin plugin;

        public ActivatePlugin(String text, Plugin plugin) {
            this.activate = new CheckBox(text);
            this.plugin = plugin;
        }
    }

    private List<ActivatePlugin> activatePluginList;

    public PluginInitializer() {
        this.plugins = Loader.localPlugins();
        this.activate = plugins;
        defaultJarPaths = new ArrayList<>();
        defaultPaths = new ArrayList<>();
    }

    public PluginInitializer(List<Plugin> plugins) {
        this.plugins = plugins;
        this.activate = plugins;
        defaultJarPaths = new ArrayList<>();
        defaultPaths = new ArrayList<>();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
        defaultJarPaths = new ArrayList<>();
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }


    public void addJar(String jar) {
        defaultJarPaths.add(jar);
    }

    public void addPath(String path) {
        defaultPaths.add(path);
    }

    @FXML
    public void close() {
        primaryStage.close();
    }

    @FXML
    public void reload() {
        File[] jars =
                Stream.concat(
                        defaultPaths.stream()
                                .map(File::new)
                                .filter(File::isDirectory),
                        defaultJarPaths.stream()
                                .map(File::new)
                                .filter(File::isFile)
                )
                        .filter(File::exists)
                        .distinct()
                        .toArray(File[]::new);

        plugins = Loader.plugins(
                new OneManyClassLoader(jars)
        );

        showPlugin();
    }

    private void showPlugin() {
        pluginsList.getChildren().clear();
        activatePluginList = plugins.stream()
                .map(p -> new ActivatePlugin("activate", p))
                .collect(Collectors.toList());

        for (ActivatePlugin activatePlugin : activatePluginList) {
            pluginsList.getChildren().add(
                    new HBox(
                            new Label(
                                    activatePlugin.plugin.getName() + '\n' +
                                            activatePlugin.plugin.getDescription()
                            ),
                            activatePlugin.activate
                    )
            );
        }
    }

    @FXML
    void handleAddPath(ActionEvent ae) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Add Path");
        File path = chooser.showDialog(primaryStage.getScene().getWindow());
        if(path == null)
            return;
        addPath(path.getPath());
        reload();
    }

    @FXML
    void handleAddJar(ActionEvent ae) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Add Jar");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Jar File", "*.jar"));
        File p = chooser.showOpenDialog(primaryStage.getScene().getWindow());
        if(p == null)
            return;
        addJar(p.getPath());
        reload();
    }

    public List<ModBuilder> generates() {
        return activatePluginList.stream()
                .filter(s -> s.activate.isSelected())
                .map(s -> s.plugin)
                .map(Plugin::getMods)
                .map(Arrays::stream)
                .reduce(Stream.empty(), Stream::concat)
                .collect(Collectors.toList());
    }


}

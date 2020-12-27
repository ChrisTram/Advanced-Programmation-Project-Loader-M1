package fr.unice.miage.engine;

import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.gamemode.Gamemode;
import fr.unice.miage.game.gamemode.ModBuilder;
import fr.unice.miage.game.reflection.DecoratorCasteable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * The GameLoader takes a modList and instance them into the game
 */
public class GameLoader implements DecoratorCasteable {
    private final List<ModBuilder> mods;
    private final GameEngineBuild gameEngineBuild;

    public GameLoader(List<ModBuilder> mods, GameEngineBuild gameEngineBuild) {
        this.mods = Loader.sort(mods);
        this.gameEngineBuild = gameEngineBuild;
    }

    public GameEngine newGameEngine() {
        List<Gamemode> gamemodes = Loader.load(mods);
        gameEngineBuild.setGamemodes(gamemodes);

        GameEngine gameEngine = gameEngineBuild.build();
        Iterator<Gamemode> iterator = gamemodes.iterator();
        while (iterator.hasNext())
            gameEngine = iterator.next().register(gameEngine);
        return gameEngine;

    }

    public void showAndWait() {
        Stage stage = new Stage();

        VBox modsMainPane = new VBox();
        ScrollPane pane = new ScrollPane(modsMainPane);
        mods.stream().map(ModBuilder::getConfigurePanel).filter(Objects::nonNull).forEach(modsMainPane.getChildren()::add);
        Button apply = new Button("apply");
        Parent root = new VBox(
                pane,
                new HBox(
                        apply
                )
        );
        root.setVisible(true);
        stage.setScene(new Scene(root,  480, 320));
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mods.forEach(ModBuilder::validConfiguration);
                stage.close();
            }
        });
        stage.showAndWait();
    }
}

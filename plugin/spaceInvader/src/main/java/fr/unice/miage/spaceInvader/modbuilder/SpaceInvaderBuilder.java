package fr.unice.miage.spaceInvader.modbuilder;

import fr.unice.miage.game.gamemode.*;
import fr.unice.miage.spaceInvader.gamemode.SpaceInvaderEngine;
import javafx.beans.property.ReadOnlyProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class SpaceInvaderBuilder implements ModBuilder {
    private List<SpaceInvaderBuilder.PlayerInfo> validate;

    private List<SpaceInvaderBuilder.PlayerInfo> configurePlayers;
    private int instancePlayerCount = 0;

    public SpaceInvaderBuilder() {
        configurePlayers = Collections.emptyList();
        validate = Collections.emptyList();
    }

    private <T extends Parent> T addId(String id, T element) {
        element.setId(id);
        return element;
    }

    private Button captureKeyBoard(KeyCode defaultValue, Consumer<KeyCode> setter) {
        Button button = new Button(defaultValue.getName());
        final EventHandler<ActionEvent> defaultCaptureKeyboard = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                final Button button = (Button) actionEvent.getSource();
                final EventHandler<? super KeyEvent> oldKeyListener = button.getOnKeyPressed();
                final EventHandler<ActionEvent> newOnAction = button.getOnAction();
                button.setOnAction((o) -> {});
                button.setOnKeyPressed(new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent keyEvent) {
                        button.setOnAction(newOnAction);
                        button.setOnKeyPressed(oldKeyListener);
                        setter.accept(keyEvent.getCode());
                        button.setText(keyEvent.getCode().getName());
                    }
                });
            }
        };
        button.setOnAction(defaultCaptureKeyboard);
        return button;
    }

    @Override
    public Parent getConfigurePanel() {
        configurePlayers = new ArrayList<>();
        VBox playerList = new VBox();
        Button newPlayer = new Button("New Player");
        newPlayer.setId("buttonAddPlayer");

        newPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                SpaceInvaderBuilder.PlayerInfo playerInfo =  new SpaceInvaderBuilder.PlayerInfo();
                ++instancePlayerCount;
                playerInfo.fire = KeyCode.SPACE;
                playerInfo.leftKey = KeyCode.LEFT;
                playerInfo.rightKey = KeyCode.RIGHT;
                Button fireKey = addId("defineFireKey_"+instancePlayerCount, captureKeyBoard(playerInfo.fire, i -> playerInfo.fire = i));
                Button leftKey = addId("defineLeftKey_"+instancePlayerCount, captureKeyBoard(playerInfo.leftKey, i -> playerInfo.leftKey = i));
                Button rightKey = addId("defineRightKey_"+instancePlayerCount, captureKeyBoard(playerInfo.rightKey, i -> playerInfo.rightKey = i));
                Spinner<Integer> speed = addId("speedSpinner_"+instancePlayerCount,new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 10)));
                playerInfo.speed = speed.valueProperty();

                configurePlayers.add(playerInfo);
                Button removeButton = addId("deleteButton_"+instancePlayerCount, new Button("Delete"));
                removeButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        configurePlayers.remove(playerInfo);
                        playerList.getChildren().remove(playerInfo.root);
                    }
                });
                playerInfo.root = new VBox(
                        new HBox(
                                new Label("Fire"),
                                fireKey,
                                new Label("Move Left"),
                                leftKey,
                                new Label("Move Right"),
                                rightKey
                        ),
                        new HBox(
                                new Label("speed"),
                                speed,
                                removeButton
                        )
                );
                playerList.getChildren().add(
                        playerInfo.root
                );
            }
        });

        Label m = new Label(" ");
        return new VBox(
                playerList,
                new HBox(
                        newPlayer
                )
        );
    }

    @Override
    public void validConfiguration() {
        validate = configurePlayers;
    }

    @Override
    public Gamemode[] generates() {
        return new Gamemode[] {
                new SpaceInvaderEngine(validate)
        };
    }

    public static class PlayerInfo {
        public ReadOnlyProperty<Integer> speed;
        public KeyCode fire;
        public KeyCode leftKey;
        public KeyCode rightKey;
        public Parent root;
    }
}

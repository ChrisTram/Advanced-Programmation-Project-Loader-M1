package fr.unice.miage.game.gamemode;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
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

public class BaseGameBuilder implements ModBuilder {
    private List<PlayerInfo> validate;

    private Spinner<Integer> obstacleCount;
    private List<PlayerInfo> configurePlayers;
    private int instancePlayerCount = 0;
    private final TextArea obstaclePosition;
    private Button upKey;
    private Button downKey;
    private Button leftKey;
    private Button rightKey;
    private Spinner<Integer> posX;
    private Spinner<Integer> posY;
    private Spinner<Integer> speed;
    public static String backgroundPath = null;

    public BaseGameBuilder() {
        configurePlayers = Collections.emptyList();
        validate = Collections.emptyList();
        obstaclePosition = addId("textObstacle", new TextArea());
        obstacleCount = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 5));
    }

    private <T extends Parent> T addId(String id, T element) {
        element.setId(id);
        return element;
    }

    public static String getBackgroundPath() {
        return backgroundPath;
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
                PlayerInfo playerInfo =  new PlayerInfo();
                ++instancePlayerCount;
                playerInfo.upKey = KeyCode.UP;
                playerInfo.downKey = KeyCode.DOWN;
                playerInfo.leftKey = KeyCode.LEFT;
                playerInfo.rightKey = KeyCode.RIGHT;
                upKey = addId("defineUpKey_"+instancePlayerCount, captureKeyBoard(KeyCode.UP, i -> playerInfo.upKey = i));
                downKey = addId("defineDownKey_"+instancePlayerCount, captureKeyBoard(KeyCode.DOWN, i -> playerInfo.downKey = i));
                leftKey = addId("defineLeftKey_"+instancePlayerCount, captureKeyBoard(KeyCode.LEFT, i -> playerInfo.leftKey = i));
                rightKey = addId("defineRightKey_"+instancePlayerCount, captureKeyBoard(KeyCode.RIGHT, i -> playerInfo.rightKey = i));
                posX = addId("posXSpinner_"+instancePlayerCount,new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)));
                posY = addId("posYSpinner_"+instancePlayerCount,new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)));
                speed = addId("speedSpinner_"+instancePlayerCount,new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 10)));
                playerInfo.posX = posX.valueProperty();
                playerInfo.posY = posY.valueProperty();
                playerInfo.speed = speed.valueProperty();

                CheckBox cbRandom = addId("cbRandom_"+instancePlayerCount,new CheckBox(""));
                CheckBox cbEscape = addId("cbEscape_"+instancePlayerCount,new CheckBox(""));
                CheckBox cbFollow = addId("cbFollow_"+instancePlayerCount,new CheckBox(""));

                cbRandom.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        //TODO Auto-generated method stub
                        if(newValue){
                            playerInfo.cbRandom = true;
                            playerInfo.cbEscape = false;
                            playerInfo.cbFollow = false;
                            cbEscape.setSelected(false);
                            cbFollow.setSelected(false);
                            buttonsState(true);
                        }else{
                            playerInfo.cbRandom = false;
                            buttonsState(false);
                        }
                    }
                });

                cbEscape.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        //TODO Auto-generated method stub
                        if(newValue){
                            playerInfo.cbEscape = true;
                            playerInfo.cbRandom = false;
                            playerInfo.cbFollow = false;
                            cbRandom.setSelected(false);
                            cbFollow.setSelected(false);
                            buttonsState(true);
                        }else{
                            playerInfo.cbEscape = false;
                            buttonsState(false);
                        }
                    }
                });

                cbFollow.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        //TODO Auto-generated method stub
                        if(newValue){
                            playerInfo.cbFollow = true;
                            playerInfo.cbEscape = false;
                            playerInfo.cbRandom = false;
                            cbEscape.setSelected(false);
                            cbRandom.setSelected(false);
                            buttonsState(true);
                        }else{
                            playerInfo.cbRandom = false;
                            buttonsState(false);
                        }
                    }
                });


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
                                new Label("Move Up"),
                                upKey,
                                new Label("Move Down"),
                                downKey,
                                new Label("Move Left"),
                                leftKey,
                                new Label("Move Right"),
                                rightKey
                        ),
                        new HBox(
                                new Label("Xpos"),
                                posX,
                                new Label("Ypos"),
                                posY,
                                new Label("speed"),
                                speed,
                                removeButton
                        ),
                        new HBox(
                                new Label("Aléatoire"),
                                cbRandom,
                                new Label("Fuite"),
                                cbEscape,
                                new Label("Suivi"),
                                cbFollow
                        )
                );
                playerList.getChildren().add(
                        playerInfo.root
                );
            }
        });
        Button loadObstacleFromFile = addId("loadFileButton", new Button("Load from file"));

        loadObstacleFromFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage here = new Stage();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(here);
                if(file != null) {
                    try {
                        String text = new String(Files.readAllBytes(file.toPath()));
                        if(text.matches("(\\d+(\\.\\d*)?(,|\\s)\\d+(\\.\\d*)?\\s*)+"))
                            obstaclePosition.setText(text);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Button loadBackgroundFromFile = addId("loadBackgroundButton", new Button("Load background"));

        loadBackgroundFromFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage here = new Stage();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                File file = fileChooser.showOpenDialog(here);
                if(file != null) {
                    System.out.println(file.getPath());
                    backgroundPath = file.getPath();
                }
            }
        });

        Label m = new Label(" ");
        return new VBox(
                playerList,
                new HBox(
                        newPlayer
                ),
                new Label("Obstacle"),
                obstaclePosition,
                loadObstacleFromFile,
                new HBox(
                        new Label("Random Obstacle Count"),
                        obstacleCount
                ),
                loadBackgroundFromFile
        );
    }



    @Override
    public void validConfiguration() {
        validate = configurePlayers;
    }

    @Override
    public Gamemode[] generates() {
        List<Point2D> points = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new StringReader(obstaclePosition.getText()));
        try {
            String line = reader.readLine();
            while (line != null) {
                String[] coordinate = line.split(",|\\s");
                points.add(new Point2D(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1])));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Gamemode[] {
                new PlayerLegacyGamemode(validate),
                new ObstacleGamemode(points),
                new PlaceRandomObstacle(new Random(),obstacleCount.getValue())
        };
    }



    public void buttonsState(boolean state){
        upKey.setDisable(state);
        downKey.setDisable(state);
        leftKey.setDisable(state);
        rightKey.setDisable(state);
    }

    public static class PlayerInfo {
        public ReadOnlyProperty<Integer> posX;
        public ReadOnlyProperty<Integer> posY;
        public ReadOnlyProperty<Integer> speed;
        public KeyCode upKey;
        public KeyCode downKey;
        public KeyCode leftKey;
        public KeyCode rightKey;
        public boolean cbRandom;
        public boolean cbEscape;
        public boolean cbFollow;
        public Parent root;
    }

    public List<PlayerInfo> getConfigurePlayers() {
        return configurePlayers;
    }
}

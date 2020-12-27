package fr.unice.miage.player.modbuilder;

import fr.unice.miage.game.gamemode.Gamemode;
import fr.unice.miage.game.gamemode.ModBuilder;
import fr.unice.miage.player.gamemode.PlayerGamemode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class PlayerBuilder implements ModBuilder {
    private Parent root;
    @FXML
    private VBox playerConfigureList;
    private List<PlayerInfo> playerInfos;
    private List<PlayerInfo> currentPlayerInfos;
    private int instancePlayerCount = 0;

    public PlayerBuilder() {
        currentPlayerInfos = Collections.emptyList();
        playerInfos = new LinkedList<>();
    }

    private Button captureKeyBoard(Button button, Consumer<KeyCode> setter) {
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

    @FXML
    protected void addPlayer(ActionEvent actionEvent) {
        ++instancePlayerCount;
        final PlayerInfo playerInfo = new PlayerInfo(KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT, 20, new Point2D(0,0));

        Button upKey = new Button(playerInfo.getUp().getName()); // = addId("defineUpKey_"+instancePlayerCount, captureKeyBoard(KeyCode.UP, i -> playerInfo.upKey = i));
        Button downKey = new Button(playerInfo.getUp().getName()); // addId("defineDownKey_"+instancePlayerCount, captureKeyBoard(KeyCode.DOWN, i -> playerInfo.downKey = i));
        Button leftKey = new Button(playerInfo.getUp().getName()); // addId("defineLeftKey_"+instancePlayerCount, captureKeyBoard(KeyCode.LEFT, i -> playerInfo.leftKey = i));
        Button rightKey = new Button(playerInfo.getUp().getName()); // addId("defineRightKey_"+instancePlayerCount, captureKeyBoard(KeyCode.RIGHT, i -> playerInfo.rightKey = i));
        captureKeyBoard(upKey, playerInfo::setUp);
        upKey.setId("defineUpKey_"+instancePlayerCount);
        captureKeyBoard(downKey, playerInfo::setDown);
        downKey.setId("defineDownKey_"+instancePlayerCount);
        captureKeyBoard(rightKey, playerInfo::setRight);
        rightKey.setId("defineRightKey_"+instancePlayerCount);
        captureKeyBoard(leftKey, playerInfo::setLeft);
        leftKey.setId("defineLeftKey_"+instancePlayerCount);
        Spinner<Integer> posX = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory((int) playerInfo.getPosition().getX(), 100, 0)); //addId(,new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)));
        Spinner<Integer> posY = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory((int) playerInfo.getPosition().getY(), 100, 0)); // = addId("posYSpinner_"+instancePlayerCount,new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0)));
        Spinner<Integer> speed = new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 0)); // = addId("speedSpinner_"+instancePlayerCount,new Spinner<>(new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 100, 10)));
        posX.setId("posXSpinner_"+instancePlayerCount);
        posY.setId("posYSpinner_"+instancePlayerCount);
        speed.setId("speedSpinner_"+instancePlayerCount);
        posX.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer oldValue, Integer newValue) {
                playerInfo.setPosition(new Point2D(newValue, playerInfo.getPosition().getY()));
            }
        });
        posY.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer oldValue, Integer newValue) {
                playerInfo.setPosition(new Point2D(playerInfo.getPosition().getX(), newValue));
            }
        });
        speed.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer oldValue, Integer newValue) {
                playerInfo.setSpeed(newValue);
            }
        });
        playerInfos.add(playerInfo);
        Button removeButton = new Button("Remove");
        removeButton.setId("deleteButton_"+instancePlayerCount);
        final VBox root = new VBox(
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
                )
        );
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                playerInfos.remove(playerInfo);
                playerConfigureList.getChildren().remove(root);
            }
        });
        playerConfigureList.getChildren().add(
                root
        );
    }

    @Override
    public Parent getConfigurePanel() {
        return root;
    }

    @Override
    public void validConfiguration() {
        currentPlayerInfos = playerInfos;
        playerInfos = new LinkedList<>();
    }

    @Override
    public Gamemode[] generates() {
        return new Gamemode[]{
                new PlayerGamemode(currentPlayerInfos)
        };
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
}

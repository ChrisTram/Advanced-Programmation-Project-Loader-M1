package fr.unice.miage.spaceInvader.game.state;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.sprite.LineText;
import fr.unice.miage.game.utils.FXRender;
import fr.unice.miage.spaceInvader.gamemode.SpaceInvaderEngine;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.EventListener;

public class GameOverState implements GameState, EventHandler<KeyEvent> {
    private final LineText text;
    private GameState next;
    private GameBoard board;
    private GameStateDone done = GameStateDone.empty;
    private final SpaceInvaderEngine engine;

    public GameOverState(GameState next, SpaceInvaderEngine engine) {
        this.next = next;
        Text msg = new Text("Game Over");
        msg.setX(0);
        msg.setY(0);
        msg.setFont(new Font("Lucida Console Bold", 50));
        msg.setFill(Color.LIGHTGRAY);
        text = new LineText(0,0, 0, 0, msg);
        this.engine = engine;
    }
    @Override
    public void initialize() {
        board = engine.getCurrent();
        board.setBackground(Color.BLACK);
        Bounds sp = text.getBoundingShape().getBoundsInParent();
        double posX = (board.getWidth() - sp.getWidth()) / 2.;
        double posY = (board.getHeight() - sp.getHeight()) / 2.;
        text.setX(posX);
        text.setY(posY);
        board.addSprite(text);
        engine.getScene().addEventFilter(KeyEvent.KEY_PRESSED,  this);
    }

    @Override
    public void setStateDone(GameStateDone stateDone) {
        done = stateDone;
    }

    @Override
    public void uninitialized() {
        board.removeSprite(text);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        engine.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, this);
        done.done(this);
        uninitialized();
        next.initialize();
    }
}

package fr.unice.miage.spaceInvader.game.state;

import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.layout.BouncingMapElement;
import fr.unice.miage.spaceInvader.gamemode.SpaceInvaderEngine;
import fr.unice.miage.spaceInvader.sprite.DefaultBackGround;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class TitleGame implements GameState, EventHandler<KeyEvent> {
    private MapElement backGround;
    private SpaceInvaderEngine engine;
    private GameStateDone stateDone = GameStateDone.empty;
    private GameState nextState;

    public TitleGame(SpaceInvaderEngine engine) {
        this.engine = engine;
        backGround = new BouncingMapElement(new DefaultBackGround(0,0,25, 30, 8));
    }

    public void setNextState(GameState nextState) {
        this.nextState = nextState;
    }

    @Override
    public void initialize() {
        engine.getCurrent().addSprite(backGround);
        engine.getScene().addEventFilter(KeyEvent.KEY_PRESSED, this);
    }

    @Override
    public void setStateDone(GameStateDone stateDone) {
        this.stateDone = stateDone;
    }

    @Override
    public void uninitialized() {
        engine.getCurrent().removeSprite(backGround);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        engine.getScene().removeEventFilter(KeyEvent.KEY_PRESSED, this);
        stateDone.done(this);
        uninitialized();
        nextState.initialize();
    }
}

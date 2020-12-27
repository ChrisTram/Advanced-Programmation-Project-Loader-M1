package fr.unice.miage.spaceInvader.sprite.action;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.spaceInvader.game.state.GameState;
import fr.unice.miage.spaceInvader.gamemode.SpaceInvaderEngine;
import fr.unice.miage.spaceInvader.sprite.SpawnSpriteDelay;

public class StateSetterElement extends SpawnSpriteDelay {
    private GameState nextState;
    private SpaceInvaderEngine gamemode;

    public StateSetterElement(double delay, GameBoard board, MapElement next, GameBoard board1, GameState nextState, SpaceInvaderEngine gamemode) {
        super(delay, board, next, board1);
        this.nextState = nextState;
        this.gamemode = gamemode;
    }

    @Override
    protected void proceed() {
        super.proceed();
        nextState.initialize();
    }
}

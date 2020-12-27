package fr.unice.miage.spaceInvader.game.state;

import fr.unice.miage.spaceInvader.gamemode.SpaceInvaderEngine;

public interface GameState {
    void initialize();
    void setStateDone(GameStateDone stateDone);
    void uninitialized();
}

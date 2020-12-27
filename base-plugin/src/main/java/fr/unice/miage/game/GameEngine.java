package fr.unice.miage.game;

import fr.unice.miage.game.reflection.DecoratorCasteable;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;

public abstract class GameEngine extends AnimationTimer implements DecoratorCasteable {
    public abstract GameBoard register(GameBoard gameBoard);
    public abstract MapElement register(MapElement sprite);
    public abstract Scene getScene();
    public abstract Input getInput();
}

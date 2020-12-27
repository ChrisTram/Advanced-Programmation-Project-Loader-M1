package fr.unice.miage.game.gamemode;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.MapElement;

/**
 * Decorate the element we want to use
 * Each gamemode can decorate an element or configurate it
 */
public interface Gamemode {
    default GameEngine register(GameEngine gameEngine) {
        return gameEngine;
    }
    default MapElement register(GameEngine gameEngine, MapElement sprite) {
        return sprite;
    }
    default GameBoard register(GameEngine gameEngine, GameBoard board) {
        return board;
    }
}

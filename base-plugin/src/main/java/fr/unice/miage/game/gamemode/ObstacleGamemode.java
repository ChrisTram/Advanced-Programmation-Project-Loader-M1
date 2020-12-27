package fr.unice.miage.game.gamemode;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.AddPhysic;
import fr.unice.miage.game.sprite.Player;
import fr.unice.miage.game.sprite.RectangleSprite;
import javafx.geometry.Point2D;

import java.util.List;

/**
 * Mod that wrap all elements in Sprites in order to get collisions behaviors.
 */
public class ObstacleGamemode implements Gamemode {
    protected List<Point2D> obstacle;

    public ObstacleGamemode(List<Point2D> obstacle) {
        this.obstacle = obstacle;
    }

    @Override
    public MapElement register(GameEngine gameEngine, MapElement sprite) {
        if(sprite.getInstance(Player.class) != null)
            return new AddPhysic(sprite);
        return sprite;
    }

    @Override
    public GameBoard register(GameEngine gameEngine, GameBoard board) {
        for (Point2D p : obstacle)
            board.addSprite(gameEngine.register(new AddPhysic(new RectangleSprite(p.getX(), p.getY(), 0, 0))));
        return board;
    }
}

package fr.unice.miage.game.gamemode;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.reflection.RequireGamemode;
import fr.unice.miage.game.sprite.AddPhysic;
import fr.unice.miage.game.sprite.RectangleSprite;

import java.util.Random;

/**
 * Mod that generate random obstacle that can colide
 */
@RequireGamemode(gamemodes = {ObstacleGamemode.class})
public class PlaceRandomObstacle implements Gamemode{
    private Random rand;
    private int obstacleCount;

    public PlaceRandomObstacle(Random rand, int obstacleCount) {
        this.rand = rand;
        this.obstacleCount = obstacleCount;
    }

    @Override
    public GameBoard register(GameEngine gameEngine, GameBoard board) {
        for (int i = 0; i < obstacleCount; i++) {
            double pos = (double) (board.getHeight()-40) * (board.getWidth()-40) * rand.nextDouble();
            board.addSprite(gameEngine.register(new AddPhysic(new RectangleSprite(pos % (board.getWidth()-40)+20, pos / (board.getWidth()-40)+20, 0, 0))));
        }
        return board;
    }
}

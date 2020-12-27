package fr.unice.miage.player.gamemode;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.PlayerController;
import fr.unice.miage.game.PlayerInput;
import fr.unice.miage.game.gamemode.Gamemode;
import fr.unice.miage.game.sprite.Player;
import fr.unice.miage.game.sprite.RoundSprite;
import fr.unice.miage.player.modbuilder.PlayerInfo;
import javafx.geometry.Point2D;

import java.util.List;

public class PlayerGamemode implements Gamemode {
    private final List<PlayerInfo> currentPlayerInfos;
    public PlayerGamemode(List<PlayerInfo> currentPlayerInfos) {
        this.currentPlayerInfos = currentPlayerInfos;
    }

    @Override
    public GameBoard register(GameEngine gameEngine, GameBoard board) {
        for (PlayerInfo playerInfo : currentPlayerInfos) {
            Point2D position = playerInfo.getPosition();
            board.addSprite(gameEngine.register(new PlayerController(new RoundSprite(position.getX(), position.getY(),0,0,10), new PlayerInput(playerInfo.getUp(), playerInfo.getDown(), playerInfo.getLeft(), playerInfo.getRight(), gameEngine.getInput()), playerInfo.getSpeed())));
        }
        return board;
    }
}

package fr.unice.miage.game.gamemode;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.Input;
import fr.unice.miage.game.PlayerInput;
import fr.unice.miage.game.sprite.Player;
import javafx.scene.Scene;

import java.util.List;

/**
 * Mod to add player
 */
public class PlayerLegacyGamemode implements Gamemode {
    private final List<BaseGameBuilder.PlayerInfo> infos;
    public PlayerLegacyGamemode(List<BaseGameBuilder.PlayerInfo> infos) {
        this.infos = infos;
    }

    @Override
    public GameBoard register(GameEngine gameEngine, GameBoard board) {
        registerPlayerOnMap(gameEngine, board);
        return board;
    }

    private void registerPlayerOnMap(GameEngine gameEngine, GameBoard board) {
        for(BaseGameBuilder.PlayerInfo p : infos) {
            PlayerInput input =  new PlayerInput(p.upKey, p.downKey, p.leftKey, p.rightKey, gameEngine.getInput());
            board.addSprite(gameEngine.register(new Player(p.posX.getValue(), p.posY.getValue(), 0, 0, 20, input, p.speed.getValue(),p.cbRandom,p.cbEscape,p.cbFollow)));
        }
    }
}

package fr.unice.miage.spaceInvader.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.spaceInvader.sprite.action.ActionDelay;

public class SpawnSpriteDelay extends ActionDelay {
    private final MapElement next;
    private final GameBoard board;

    public SpawnSpriteDelay(double delay, GameBoard board, MapElement next, GameBoard board1) {
        super(delay, board);
        this.next = next;
        this.board = board1;
    }

    @Override
    protected void proceed() {
        board.removeSprite(this);
        if(next != null)
            board.addSprite(next);
    }

}

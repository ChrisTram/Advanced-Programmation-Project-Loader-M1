package fr.unice.miage.spaceInvader.sprite.action;

import fr.unice.miage.game.GameBoard;

public class DoActionDelay extends ActionDelay {
    private final Runnable runnable;

    public DoActionDelay(double delay, GameBoard board, Runnable runnable) {
        super(delay, board);
        this.runnable = runnable;
    }

    @Override
    protected void proceed() {
        runnable.run();
    }
}

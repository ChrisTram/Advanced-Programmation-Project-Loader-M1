package fr.unice.miage.spaceInvader.game.state;

public interface GameStateDone {
    GameStateDone empty = new GameStateDone() {
        @Override
        public void done(GameState state) {}
    };
    void done(GameState state);
}

package fr.unice.miage.spaceInvader.gamemode;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.Input;
import fr.unice.miage.game.gamemode.Gamemode;
import fr.unice.miage.spaceInvader.PlayerInput;
import fr.unice.miage.spaceInvader.game.state.*;
import fr.unice.miage.spaceInvader.modbuilder.SpaceInvaderBuilder;
import fr.unice.miage.spaceInvader.sprite.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

import java.util.List;

public class SpaceInvaderEngine implements Gamemode {
    public int phaseCounter = 0;
    private GameBoard current;
    private TerminalElement terminalElement = null;
    private Input input = null;
    private Scene scene = null;

    private TitleGame titleGame;
    private FightPhase fightPhase;
    private StoryTellingPhase storyTellingPhase;
    private GameOverState gameOverState;
    private final List<SpaceInvaderBuilder.PlayerInfo> playerInfos;

    public SpaceInvaderEngine(List<SpaceInvaderBuilder.PlayerInfo> validate) {
        playerInfos = validate;
    }

    public TitleGame getTitleGame() {
        return titleGame;
    }

    public FightPhase getFightPhase() {
        return fightPhase;
    }

    public StoryTellingPhase getStoryTellingPhase() {
        return storyTellingPhase;
    }

    public GameOverState getGameOverState() {
        return gameOverState;
    }

    public TerminalElement getTerminalElement() {
        return terminalElement;
    }

    public GameBoard getCurrent() {
        return current;
    }

    public int getPhaseCounter() {
        return phaseCounter;
    }

    public void setPhaseCounter(int phaseCounter) {
        this.phaseCounter = phaseCounter;
    }

    public Input getInput() {
        return input;
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public GameBoard register(GameEngine gameEngine, GameBoard board) {
        input = gameEngine.getInput();
        scene = gameEngine.getScene();
        titleGame = new TitleGame(this);
        fightPhase = new FightPhase(4, 10, 3, null, null, this);
        storyTellingPhase = new StoryTellingPhase(this, fightPhase, titleGame);
        titleGame.setNextState(storyTellingPhase);
        gameOverState = new GameOverState(storyTellingPhase, this);
        fightPhase.setGameOver(gameOverState);
        fightPhase.setVictory(storyTellingPhase);

        for(SpaceInvaderBuilder.PlayerInfo playerInfo : playerInfos) {
            fightPhase.addPlayer(new PlayerShip(0,0,0,0, Color.RED, 1, new PlayerInput(gameEngine.getInput(), playerInfo.rightKey, playerInfo.leftKey, playerInfo.fire), playerInfo.speed.getValue(), null));
        }

        terminalElement = new TerminalElement(0, 0 ,0,0,Color.LIGHTGRAY, Color.BLACK, board.getSurface());
        current = board;
        fightPhase.initialize();
        return board;
    }

}

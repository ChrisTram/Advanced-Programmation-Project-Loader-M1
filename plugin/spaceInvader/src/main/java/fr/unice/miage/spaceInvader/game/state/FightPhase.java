package fr.unice.miage.spaceInvader.game.state;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.RectangleSprite;
import fr.unice.miage.game.sprite.layout.SpriteStayInLimit;
import fr.unice.miage.game.sprite.layout.StayInLimitListener;
import fr.unice.miage.game.sprite.layout.SpriteLayoutDynamic;
import fr.unice.miage.spaceInvader.gamemode.SpaceInvaderEngine;
import fr.unice.miage.spaceInvader.sprite.Direction;
import fr.unice.miage.spaceInvader.sprite.Invader;
import fr.unice.miage.spaceInvader.sprite.PlayerShip;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FightPhase implements GameState, Consumer<Invader> {
    private GameState gameOver;
    private GameState victory;

    private static class InvaderHandler implements StayInLimitListener {
        private final double speed;
        private final double invaderHeight;
        private final SpriteStayInLimit limit;
        private final MapElement swarm;
        private Direction direction;

        public InvaderHandler(double speed, double invaderHeight, SpriteStayInLimit limit, MapElement swarm, Direction direction) {
            this.speed = speed;
            this.invaderHeight = invaderHeight;
            this.limit = limit;
            this.swarm = swarm;
            this.direction = direction;
            swarm.setSpeedX(speed);
        }

        @Override
        public void touchTop(double overflow) {
        }

        @Override
        public void touchBottom(double overflow) {
            switch (direction) {
                case LEFT:
                    swarm.setSpeedX(speed);
                    swarm.setSpeedY(0);
                    break;
                case RIGHT:
                    swarm.setSpeedX(-speed);
                    swarm.setSpeedY(0);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void touchLeft(double overflow) {
            direction = Direction.LEFT;
            swarm.setSpeedX(0);
            swarm.setSpeedY(speed);
            Rectangle surface = limit.getBoundingShape();
            surface.setHeight(surface.getHeight() + invaderHeight);
        }

        @Override
        public void touchRight(double overflow) {
            direction = Direction.RIGHT;
            swarm.setSpeedX(0);
            swarm.setSpeedY(speed);
            Rectangle surface = limit.getBoundingShape();
            surface.setHeight(surface.getHeight() + invaderHeight);
        }
    }

    private GameStateDone stateDone = GameStateDone.empty;
    private Bounds invadersBound;
    private SpriteStayInLimit sprites;
    private List<Invader> invaders;
    private List<Invader> pendingInvader = null;
    private SpriteStayInLimit playerLimit;
    private List<PlayerShip> playerShip;
    private final SpaceInvaderEngine engine;
    private final SpriteLayoutDynamic layoutDynamic;
    private InvaderHandler handler;

    public FightPhase(int lineCount, int columnCount, double spacing, GameState gameOver, GameState victory, SpaceInvaderEngine engine) {
        this.gameOver = gameOver;
        this.victory = victory;
        this.engine = engine;
        playerShip = new ArrayList<>();
        invaders = new ArrayList<>(lineCount*columnCount);
        invadersBound = new Invader(new RectangleSprite(0,0,0, 0), Color.YELLOW, 35, false, 2, null, null, null).getBoundingShape().getBoundsInParent();

        layoutDynamic = new SpriteLayoutDynamic(0,0,0,0, true);
        for (int j = 0; j < columnCount; j++) {
            double hPos = (spacing+invadersBound.getWidth())*j;
            Invader latest = null;
            for (int i = lineCount; i > 0;) {
                i--;
                double vPos = (spacing+invadersBound.getHeight()) * i;
                invaders.add(latest = new Invader(new RectangleSprite(hPos,vPos,0, 0), Color.YELLOW, 35, latest == null, 2, layoutDynamic, latest, this));
            }
        }
    }

    public void setGameOver(GameState gameOver) {
        this.gameOver = gameOver;
    }

    public void setVictory(GameState victory) {
        this.victory = victory;
    }

    public void addPlayer(PlayerShip element) {
        playerShip.add(element);
    }

    public void removePlayer(MapElement element) {
        playerShip.remove(element);
    }

    @Override
    public void initialize() {
        GameBoard board = engine.getCurrent();
        synchronized (board) {
            board.setBackground(Color.BLACK);
            layoutDynamic.getChildren().clear();
            layoutDynamic.getChildren().addAll(invaders);
            double playerHeight = playerShip.stream().mapToDouble(x -> x.getBoundingShape().getBoundsInParent().getHeight()).max().orElse(10);
            playerLimit = new SpriteStayInLimit(0, board.getHeight() * 9. / 10. - playerHeight - 5, 0, 0, new Rectangle(board.getWidth(), playerHeight), true);
            playerLimit.getChildren().addAll(playerShip);
            playerShip.forEach(p -> p.setParent(playerLimit));
            sprites = new SpriteStayInLimit(0, 0, 0, 0, new Rectangle(board.getWidth(), layoutDynamic.getBoundingShape().getBoundsInParent().getHeight()), false);
            sprites.getChildren().add(layoutDynamic);
            handler = new InvaderHandler(20, invadersBound.getHeight(), sprites, layoutDynamic, Direction.LEFT);
            sprites.setLimitListener(handler);
            board.addSprite(playerLimit);
            board.addSprite(sprites);
            pendingInvader = new ArrayList<>(invaders);
        }
    }

    @Override
    public void setStateDone(GameStateDone stateDone) {
        this.stateDone = stateDone;
    }

    @Override
    public void uninitialized() {
        GameBoard board = engine.getCurrent();
        synchronized (board) {
            invaders.forEach(board::removeSprite);
            engine.getCurrent().removeSprite(sprites);
            sprites.setLimitListener(null);
        }
    }

    @Override
    public void accept(Invader invader) {
        pendingInvader.remove(invader);
        if(pendingInvader.isEmpty()) {
            uninitialized();
            victory.initialize();
        }
    }
}

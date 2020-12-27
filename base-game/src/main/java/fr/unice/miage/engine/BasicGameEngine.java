package fr.unice.miage.engine;

import fr.unice.miage.game.*;
import fr.unice.miage.game.collision.*;
import fr.unice.miage.game.gamemode.BaseGameBuilder;
import fr.unice.miage.game.gamemode.Gamemode;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static fr.unice.miage.game.gamemode.BaseGameBuilder.backgroundPath;

public class BasicGameEngine extends GameEngine {
    private final Canvas canvas;
    private final List<Gamemode> gamemodes;
    private final MapGenerator mapGenerator;
    private GameBoard board;
    private long lastUpdateNanoTime;
    private Input input;
    private final GraphicsContext gc;
    private Scene scene;

    public BasicGameEngine(Scene scene, Canvas canvas, List<Gamemode> gamemodes, MapGenerator mapGenerator) {
        this.canvas = canvas;
        this.gamemodes = gamemodes;
        this.scene = scene;
        input = new Input(scene);
        gc = canvas.getGraphicsContext2D();
        this.mapGenerator = mapGenerator;
        input.addListeners();
        //taken from https://gamedevelopment.tutsplus.com/tutorials/introduction-to-javafx-for-game-development--cms-23835
        lastUpdateNanoTime = System.nanoTime();
    }

    public List<Gamemode> getGamemodes() {
        return gamemodes;
    }

    public MapElement register(MapElement sprite) {
        Iterator<Gamemode> gamemodeIterator = gamemodes.iterator();
        while (gamemodeIterator.hasNext())
            sprite = gamemodeIterator.next().register(this, sprite);
        return sprite;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public GameBoard register(GameBoard gameBoard) {
        Iterator<Gamemode> gamemodeIterator = gamemodes.iterator();
        while (gamemodeIterator.hasNext())
            gameBoard = gamemodeIterator.next().register(this, gameBoard);
        return gameBoard;
    }

    public long getLastUpdateNanoTime() {
        return lastUpdateNanoTime;
    }

    @Override
    public void start() {
        initialize();
        super.start();
    }

    public void initialize() {
        /* We get MapGenerator to register a map THEN we notify the gamemodes that we are
         * using this map */
        this.board = register(
                mapGenerator.generate((int)canvas.getWidth(), (int)canvas.getHeight())
        );
    }

    public void handle(long currentNanoTime) {
        double t = (currentNanoTime - lastUpdateNanoTime) / 1000000000.0;

//        t = Double.min(t, 0.2);


        if(backgroundPath==null){
            gc.setFill(board.getBackground());
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            gc.setFill(Color.BLACK);
        }
        else{
            File file = new File(backgroundPath);
            String localUrl = null;
            try {
                localUrl = file.toURI().toURL().toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Image image = new Image(localUrl);
            gc.drawImage(image,0,0,512,512);
        }

        List<MapElement> toUpdate = new ArrayList<>();
        board.spriteIterator().forEachRemaining(toUpdate::add);
        BoardIterator iterator = new BoardIterator(toUpdate);
        iterator.explore(
                new CompositeVisitor(
                        new UpdateVisitor(t, board),
                        new CollisionCompute(board)
                )
        );
        toUpdate.clear();
        board.spriteIterator().forEachRemaining(toUpdate::add);
        new BoardIterator(toUpdate).explore(new RenderVisitor(gc));
        lastUpdateNanoTime = currentNanoTime;
    }
}

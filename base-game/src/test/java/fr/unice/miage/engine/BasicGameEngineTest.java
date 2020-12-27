package fr.unice.miage.engine;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.gamemode.Gamemode;
import fr.unice.miage.game.sprite.Player;
import fr.unice.miage.game.sprite.RectangleSprite;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BasicGameEngineTest extends ApplicationTest {
    private BorderPane root;
    private Scene theScene;
    private Canvas canvas;
    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        Scene scene = new Scene(root, 512, 512);
        theScene = scene;
        canvas = new Canvas(512, 512);
        root.setCenter(canvas);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void test_register() {
        Gamemode gamemode = mock(Gamemode.class);
        GameBoard board = new GameBoard((int)canvas.getWidth(), (int)canvas.getHeight());
        when(gamemode.register(any())).thenCallRealMethod();
        when(gamemode.register(any(), any(MapElement.class))).thenCallRealMethod();
        when(gamemode.register(any(), any(GameBoard.class))).thenCallRealMethod();
        List<Gamemode> gamemodeList = new ArrayList<>();
        gamemodeList.add(gamemode);
        BasicGameEngine gameEngine = (BasicGameEngine) new GameEngineBuild().setScene(theScene).setCanvas(canvas).setGamemodes(gamemodeList).setMapGenerator((h, w) -> board).build();
        gameEngine.initialize();
        assertSame(board, gameEngine.register(board));
        verify(gamemode, atLeastOnce()).register(any(), any(GameBoard.class));
        Player player = new Player(10, 10,0, 0, 0, null, 2,false,false,false);
        assertSame(player, gameEngine.register(player));
        verify(gamemode, atLeastOnce()).register(any(), any(MapElement.class));
        RectangleSprite sprite = new RectangleSprite(20,20,10,15);
        board.addSprite(sprite);
        long t0 = gameEngine.getLastUpdateNanoTime();
        gameEngine.handle(t0 + 1000000000L);
        assertEquals(30, sprite.getX());
        assertEquals(35, sprite.getY());
    }
}
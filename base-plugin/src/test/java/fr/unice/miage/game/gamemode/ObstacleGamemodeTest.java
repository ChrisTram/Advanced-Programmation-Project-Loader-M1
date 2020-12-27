package fr.unice.miage.game.gamemode;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.AddPhysic;
import fr.unice.miage.game.sprite.Player;
import fr.unice.miage.game.sprite.RectangleSprite;
import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ObstacleGamemodeTest {
    @Mock
    private GameBoard board;
    @Mock
    private GameEngine engine;

    @BeforeEach
    void init(){
        MockitoAnnotations.initMocks(this);

    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    void place_n_obstacle(int n) {
        assertDoesNotThrow( () -> {
            when(engine.register(any(AddPhysic.class))).thenAnswer(c -> c.getArgument(0));
            doAnswer(c -> 100).when(board).getWidth();
            doAnswer(c -> 100).when(board).getHeight();
            doNothing().when(board).addSprite(any());
            List<Point2D> gamemodes = new ArrayList<>();
            for (int i = 0; i < n; i++)
                gamemodes.add(new Point2D(i,i));

            Gamemode mode = new ObstacleGamemode(Collections.unmodifiableList(gamemodes));
            mode.register(engine, board);
            verify(board, times(n)).addSprite(any());
        });
    }

    @Test
    void register_MapElement() {
        when(engine.register(any(MapElement.class))).thenAnswer(c -> c.getArgument(0));
        doAnswer(c -> 100).when(board).getWidth();
        doAnswer(c -> 100).when(board).getHeight();

        Gamemode mode = new ObstacleGamemode(Collections.emptyList());
        MapElement e = mode.register(engine, new Player(0,0,0,0,5, null, 2,false,false,false));
        MapElement f = mode.register(engine, new RectangleSprite(0,0,0,0));
        assertNotSame(null, e.getInstance(AddPhysic.class));
        assertSame(null, f.getInstance(AddPhysic.class));
    }
}
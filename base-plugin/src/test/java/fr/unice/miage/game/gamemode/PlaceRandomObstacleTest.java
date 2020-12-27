package fr.unice.miage.game.gamemode;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.sprite.AddPhysic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PlaceRandomObstacleTest {
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
            doNothing().when(board).addSprite(argThat(c -> 0 < c.getX() && c.getX() < 100 && 0 < c.getY() && c.getY() < 100));
            Gamemode mode = new PlaceRandomObstacle(new Random(), n);
            mode.register(engine, board);
            verify(board, times(n)).addSprite(any());
        });
    }
}
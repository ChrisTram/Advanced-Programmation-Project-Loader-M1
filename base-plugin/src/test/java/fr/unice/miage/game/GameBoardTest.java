package fr.unice.miage.game;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    @ParameterizedTest
    @ValueSource(ints = {0, 10, 500 , Integer.MAX_VALUE})
    void test_with(int width) {
        GameBoard board = new GameBoard(width, -1);
        assertEquals(width, board.getWidth());
        assertEquals(-1, board.getHeight());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 10, 500 , Integer.MAX_VALUE})
    void test_height(int height) {
        GameBoard board = new GameBoard(-1, height);
        assertEquals(height, board.getHeight());
        assertEquals(-1, board.getWidth());
    }

    @Test
    void test_getWith() {
        GameBoard board = new GameBoard(-1, -1);
        assertEquals(-1, board.getHeight());
        assertEquals(-1, board.getWidth());
        for (int i = 0; i < 10; i++) {
            board.setWidth(i);
            assertEquals(-1, board.getHeight());
            assertEquals(i, board.getWidth());
        }
    }

    @Test
    void test_getHeight() {
        GameBoard board = new GameBoard(-1, -1);
        assertEquals(-1, board.getHeight());
        assertEquals(-1, board.getWidth());
        for (int i = 0; i < 10; i++) {
            board.setHeight(i);
            assertEquals(i, board.getHeight());
            assertEquals(-1, board.getWidth());
        }
    }

    @Test
    void test_use_sprite() {
        GameBoard board = new GameBoard(20, 20);
        List<MapElement> elements = new ArrayList<>();
        Collections.addAll(elements,
                new Player(0,0,0,0,5, null, 5,false,false,false),
                new Player(0,0,0,0,5, null, 5,false,false,false),
                new Player(0,0,0,0,5, null, 5,false,false,false)
                );
        elements.forEach(board::addSprite);
        Iterator<MapElement> iterator = board.spriteIterator();
        assertTrue(iterator.hasNext());
        assertSame(elements.get(0), iterator.next());
        assertTrue(iterator.hasNext());
        assertSame(elements.get(1), iterator.next());
        assertTrue(iterator.hasNext());
        assertSame(elements.get(2), iterator.next());
        assertFalse(iterator.hasNext());
    }
}
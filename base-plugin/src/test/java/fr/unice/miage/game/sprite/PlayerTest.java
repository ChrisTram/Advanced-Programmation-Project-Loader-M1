package fr.unice.miage.game.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.Input;
import fr.unice.miage.game.PlayerInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

class PlayerTest {
    @Mock
    PlayerInput input;
    @Mock
    GameBoard board;
    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void test_player_move_left() {
        doReturn(100).when(board).getWidth();
        doReturn(100).when(board).getHeight();
        doReturn(true).doReturn(false).when(input).isMoveLeft();
        doReturn(false).when(input).isMoveRight();
        doReturn(false).when(input).isMoveDown();
        doReturn(false).when(input).isMoveUp();
        Player player = new Player(0,0,0,0,5,input, 10,false,false,false);
        // TODO: corigé le code wtf de Input
        player.update(1, board);
        assertEquals(-10, player.getSpeedX());
        assertEquals(0, player.getSpeedY());
        assertEquals(-10, player.getX());
        assertEquals(0, player.getY());
        player.update(1, board); // proceed to key board left
        assertEquals(0, player.getSpeedX());
        assertEquals(0, player.getSpeedY());
        assertEquals(-10, player.getX());
        assertEquals(0, player.getY());
        player.update(1, board);
        assertEquals(-10, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void test_player_move_right() {
        doReturn(100).when(board).getWidth();
        doReturn(100).when(board).getHeight();
        doReturn(false).when(input).isMoveLeft();
        doReturn(true).doReturn(false).when(input).isMoveRight();
        doReturn(false).when(input).isMoveDown();
        doReturn(false).when(input).isMoveUp();
        Player player = new Player(0,0,0,0,5,input, 10,false,false,false);
        // TODO: corigé le code wtf de Input
        player.update(1, board);
        assertEquals(10, player.getSpeedX());
        assertEquals(0, player.getSpeedY());
        assertEquals(10, player.getX());
        assertEquals(0, player.getY());
        player.update(1, board); // proceed to key board left
        assertEquals(0, player.getSpeedX());
        assertEquals(0, player.getSpeedY());
        assertEquals(10, player.getX());
        assertEquals(0, player.getY());
        player.update(1, board);
        assertEquals(10, player.getX());
        assertEquals(0, player.getY());
    }

    @Test
    void test_player_move_up() {
        doReturn(100).when(board).getWidth();
        doReturn(100).when(board).getHeight();
        doReturn(false).when(input).isMoveLeft();
        doReturn(false).when(input).isMoveRight();
        doReturn(false).when(input).isMoveDown();
        doReturn(true).doReturn(false).when(input).isMoveUp();
        Player player = new Player(0,0,0,0,5,input, 10,false,false,false);
        // TODO: corigé le code wtf de Input
        player.update(1, board);
        assertEquals(0, player.getSpeedX());
        assertEquals(-10, player.getSpeedY());
        assertEquals(0, player.getX());
        assertEquals(-10, player.getY());
        player.update(1, board); // proceed to key board left
        assertEquals(0, player.getSpeedX());
        assertEquals(0, player.getSpeedY());
        assertEquals(0, player.getX());
        assertEquals(-10, player.getY());
        player.update(1, board);
        assertEquals(0, player.getX());
        assertEquals(-10, player.getY());
    }

    @Test
    void test_player_move_down() {
        doReturn(100).when(board).getWidth();
        doReturn(100).when(board).getHeight();
        doReturn(false).when(input).isMoveLeft();
        doReturn(false).when(input).isMoveRight();
        doReturn(true).doReturn(false).when(input).isMoveDown();
        doReturn(false).when(input).isMoveUp();
        Player player = new Player(50,50,0,0,5,input, 10,false,false,false);
        // TODO: corigé le code wtf de Input
        player.update(1, board);
        assertEquals(0, player.getSpeedX());
        assertEquals(10, player.getSpeedY());
        assertEquals(50, player.getX());
        assertEquals(60, player.getY());
        player.update(1, board); // proceed to key board left
        assertEquals(0, player.getSpeedX());
        assertEquals(0, player.getSpeedY());
        assertEquals(50, player.getX());
        assertEquals(60, player.getY());
        player.update(1, board);
        assertEquals(50, player.getX());
        assertEquals(60, player.getY());
    }
}
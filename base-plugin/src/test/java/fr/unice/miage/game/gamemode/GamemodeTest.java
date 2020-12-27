package fr.unice.miage.game.gamemode;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertSame;

class GamemodeTest {
    @Mock
    GameEngine engine;
    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void register_MapElement() {
        Gamemode gamemode = new Gamemode() {};
        Player p = new Player(0,0,0,0,0,null,0,false,false,false);
        assertSame(null, gamemode.register(engine, (MapElement) null));
        assertSame(p, gamemode.register(engine, p));
    }

    @Test
    void register_GameBoard() {
        Gamemode gamemode = new Gamemode() {};
        GameBoard p = new GameBoard(12,12);
        assertSame(null, gamemode.register(engine, (GameBoard) null));
        assertSame(p, gamemode.register(engine, p));
    }

    @Test
    void register_GameEngine() {
        Gamemode gamemode = new Gamemode() {};
        assertSame(null, gamemode.register(null));
        assertSame(engine, gamemode.register(engine));
    }
}
package fr.unice.miage.game.gamemode;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.MapElement;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class PlayerLegacyGamemodeTest {
    @Mock
    GameEngine engine;
    @Mock
    GameBoard board;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,4,8,10})
    void generate_player(int playerCount) {
        when(engine.register(any(MapElement.class))).thenAnswer(c -> c.getArgument(0));
        doReturn(100).when(board).getHeight();
        doReturn(100).when(board).getWidth();
        List<BaseGameBuilder.PlayerInfo> infos = new ArrayList<>(playerCount);
        for (int i = 0; i < playerCount; i++) {
            BaseGameBuilder.PlayerInfo playerInfo = new BaseGameBuilder.PlayerInfo();
            playerInfo.posY = new SimpleObjectProperty<>(1);
            playerInfo.posX = new SimpleObjectProperty<>(2);
            playerInfo.speed = new SimpleObjectProperty<>(3);
            playerInfo.root = null;
            playerInfo.rightKey = KeyCode.A;
            playerInfo.leftKey = KeyCode.B;
            playerInfo.upKey = KeyCode.C;
            playerInfo.downKey = KeyCode.D;
            infos.add(playerInfo);
        }
        PlayerLegacyGamemode gamemode = new PlayerLegacyGamemode(infos);
        gamemode.register(engine, board);
        verify(board, times(playerCount)).addSprite(any());
    }
}
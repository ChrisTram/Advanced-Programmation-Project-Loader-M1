package fr.unice.miage.game.sprite;

import fr.unice.miage.game.MapElement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class ObstacableTest {
    @Test
    void test_getInstance() {
        Player player;
        AddPhysic obstacable = new AddPhysic(
                player = new Player(0,0,0,0,0,null,0,false,false,false)
        );
        MapElement e = obstacable;
        assertSame(obstacable, e.getInstance(AddPhysic.class));
        assertSame(player, e.getInstance(Player.class));
    }
    @Test
    void test_getInstanceAlwaysEnd() {
        Player player;
        AddPhysic obstacable = new AddPhysic(
                player = new Player(0,0,0,0,0,null,0,false,false,false)
        );
        MapElement e = obstacable;
        assertSame(obstacable, e.getInstance(AddPhysic.class));
        assertSame(player, e.getInstance(Player.class));
    }
}
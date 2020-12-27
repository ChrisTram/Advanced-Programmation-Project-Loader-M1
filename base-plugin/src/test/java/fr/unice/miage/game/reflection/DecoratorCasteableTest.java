package fr.unice.miage.game.reflection;

import fr.unice.miage.game.sprite.FakeDecorator;
import fr.unice.miage.game.sprite.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class DecoratorCasteableTest {
    final Player p = new Player(0,0,0,0,0,null,0,false,false,false);
    @Test
    void get_instance_on_empty_decorator() {
        DecoratorCasteable decoratorCasteable = new DecoratorCasteable() {
            final Player player = p;
        };
        assertSame(null, decoratorCasteable.getInstance(Player.class));
    }

    @Test
    void get_instance_on_decorator() {
        DecoratorCasteable decoratorCasteable = new DecoratorCasteable() {
            @Decorate
            final Player player = p;
        };
        assertSame(p, decoratorCasteable.getInstance(Player.class));
    }

    @Test
    void get_instance_on_subClass_decorator() {
        DecoratorCasteable decoratorCasteable = new FakeDecorator(p) {
        };
        assertSame(p, decoratorCasteable.getInstance(Player.class));
        assertSame(decoratorCasteable, decoratorCasteable.getInstance(FakeDecorator.class));
    }
}
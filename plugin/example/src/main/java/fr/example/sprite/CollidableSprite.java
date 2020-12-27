package fr.example.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.FakeDecorator;

/**
 * CollidableSprite make an MapElement collidable with other sprite
 * by overwriting CollidableSprite.handleCollision
 */
public class CollidableSprite extends FakeDecorator {
    public CollidableSprite(MapElement decorated) {
        super(decorated);
    }

    @Override
    public void handleCollision(GameBoard b, MapElement p) {
        System.out.println("Handle the collision");
        super.handleCollision(b, p);
    }
}

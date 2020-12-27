package fr.example.gamemode;

import fr.example.sprite.CollidableSprite;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.gamemode.Gamemode;

/**
 * Object who wrap any MapElement into CollidableSprite to enable collision
 */
public class CollisionMechanic implements Gamemode {
    @Override
    public MapElement register(GameEngine gameEngine, MapElement sprite) {
        return new CollidableSprite(sprite);
    }
}

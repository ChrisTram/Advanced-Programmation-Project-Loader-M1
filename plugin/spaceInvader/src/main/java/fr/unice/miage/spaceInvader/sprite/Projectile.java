package fr.unice.miage.spaceInvader.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import javafx.scene.paint.Color;

public class Projectile extends CharSprite {
    public Projectile(double x, double y, double speedX, double speedY, char charSprite, Color color) {
        super(x, y, speedX, speedY, charSprite, color);
    }

    @Override
    public void handleCollision(GameBoard b, MapElement p) {
        if(p.getInstance(PlayerShip.class) != null || p.getInstance(Invader.class) != null || p.getInstance(Projectile.class) != null) {
            b.removeSprite(this);
        }
    }
}

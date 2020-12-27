package fr.unice.miage.spaceInvader.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.spaceInvader.PlayerInput;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

public class PlayerShip extends CharSprite {
    private double remaining;
    private double fireRate;
    private final PlayerInput playerInput;
    private final double speed;
    private MapElement parent;
    private Projectile latest;
    public PlayerShip(double x, double y, double speedX, double speedY, Color color, double fireRate, PlayerInput playerInput, double speed, MapElement parent) {
        super(x, y, speedX, speedY, 'A', color);
        this.remaining = fireRate;
        this.fireRate = fireRate;
        this.playerInput = playerInput;
        this.speed = speed;
        this.parent = parent;
        this.latest = null;
    }

    public void setParent(MapElement parent) {
        this.parent = parent;
    }

    @Override
    public void update(double time, GameBoard b) {
        super.update(time, b);
        if(playerInput.isMovingLeft() && !playerInput.isMovingRight())
            setSpeedX(-speed);
        else if(!playerInput.isMovingLeft() && playerInput.isMovingRight())
            setSpeedX(speed);
        else
            setSpeedX(0);
        if(remaining < time) {
            if(playerInput.isFire()) {
                Bounds bounds = getBoundingShape().getBoundsInParent();
                if (parent != null)
                    bounds = parent.getBoundingShape().localToParent(bounds);
                Projectile projectile = new Projectile((bounds.getMinX()+bounds.getMaxX())/2., bounds.getMinY()-bounds.getHeight(), 0, -speed, '|', color);
                if (latest == null || !latest.getBoundingShape().getBoundsInParent().intersects(projectile.getBoundingShape().getBoundsInParent())) {
                    latest = projectile;
                    b.addSprite(projectile);
                }
                remaining += fireRate;
            }
            else
                remaining = 0;
        }
        else
            remaining -= time;

    }

    @Override
    public void handleCollision(GameBoard b, MapElement p) {
    }
}

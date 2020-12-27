package fr.unice.miage.spaceInvader.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.FakeDecorator;
import fr.unice.miage.game.sprite.Sprite;
import fr.unice.miage.game.sprite.layout.Layout;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

public class Invader extends FakeDecorator<Sprite> {
    private double speed;
    private boolean fireEnable;
    private double remaining;
    private double fireRate;
    private MapElement parent;
    private Color color;
    private Invader behind;
    private Consumer<Invader> destroy;
    public Invader(Sprite sprite, Color color, double speed, boolean fireEnable, double fireRate, MapElement parent, Invader behind, Consumer<Invader> destroy) {
        super(sprite);
        this.color = color;
        this.speed = speed;
        this.fireEnable = fireEnable;
        this.fireRate = fireRate;
        this.parent = parent;
        this.behind = behind;
        this.destroy = destroy;
        this.remaining = fireRate;
    }

    @Override
    public void update(double time, GameBoard b) {
        super.update(time, b);
        if(fireEnable) {
            if (remaining < fireRate) {
                remaining = 0;
                if (Math.random() < 0.05) {
                    Bounds bounds;
                    if (parent != null) {
                        bounds = parent.getBoundingShape().localToParent(getBoundingShape().getBoundsInParent());
                        b.addSprite(
                                new Projectile(bounds.getMinX(), bounds.getMaxY(), 0, speed + getSpeedY()+parent.getSpeedY(), 'T', color)
                        );
                    }
                    else {
                        bounds = getBoundingShape().getBoundsInParent();
                        b.addSprite(
                                new Projectile(bounds.getMinX(), bounds.getMaxY(), 0, speed + getSpeedY(), 'T', color)
                        );
                    }
                }
            } else
                remaining -= time;
        }
    }

    @Override
    public void handleCollision(GameBoard b, MapElement p) {
        super.handleCollision(b, p);
        if(p.getInstance(Projectile.class) != null) {
            Layout layoutParent = parent.getInstance(Layout.class);
            if(layoutParent != null)
                 layoutParent.getChildren().removeIf(c -> equals(c.getInstance(Invader.class)));
            else
                b.removeSprite(this);
            destroy.accept(this);
            if(behind != null)
                behind.fireEnable = true;
        }
    }
}

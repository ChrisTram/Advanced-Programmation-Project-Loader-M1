package fr.unice.miage.game.sprite.layout;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class SpriteLayoutFixed extends GroupeSprite {
    protected Rectangle surface;

    public SpriteLayoutFixed(double x, double y, double speedX, double speedY, Rectangle surface, boolean isRelative) {
        super(x, y, speedX, speedY, isRelative);
        this.surface = surface;
        surface.setTranslateX(x);
        surface.setTranslateY(y);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save();
//        Bounds bounds = surface.getBoundsInParent();
        gc.translate(x, y);
        for (MapElement element : children) {
//            gc.rect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
//            gc.clip();
            element.render(gc);
        }
        gc.restore();
    }

    @Override
    public Rectangle getBoundingShape() {
        return surface;
    }

    @Override
    public void handleCollision(GameBoard b, MapElement p) {
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        surface.setTranslateX(x);
    }

    @Override
    public void setY(double y) {
        super.setY(y);
        surface.setTranslateY(y);
    }
}

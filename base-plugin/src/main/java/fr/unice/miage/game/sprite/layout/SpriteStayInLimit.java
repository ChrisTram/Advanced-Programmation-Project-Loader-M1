package fr.unice.miage.game.sprite.layout;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class SpriteStayInLimit extends SpriteLayoutFixed {
    private StayInLimitListener limitListener;

    public SpriteStayInLimit(double x, double y, double speedX, double speedY, Rectangle surface, boolean isRelative) {
        super(x, y, speedX, speedY, surface, isRelative);
        limitListener = StayInLimitListener.EMPTY_LISTENER;
        this.surface = surface;
    }

    public StayInLimitListener getLimitListener() {
        return limitListener;
    }

    public void setLimitListener(StayInLimitListener limitListener) {
        this.limitListener = limitListener;
    }

    @Override
    public void update(double time, GameBoard b) {
        super.update(time, b);
        for (MapElement sprite : children) {
            Shape spriteShape = sprite.getBoundingShape();
            Bounds localBound = surface.getBoundsInLocal();
            Bounds bounds = spriteShape.getBoundsInParent();
            if (bounds.getMinX() < localBound.getMinX()) {
                double overflow = bounds.getMinX() - localBound.getMinX();
                sprite.setX(sprite.getX() - overflow);
                limitListener.touchLeft(bounds.getMinY());
            } else if (bounds.getMaxX() > localBound.getWidth()) {
                double overflow = bounds.getMaxX() - localBound.getWidth();
                sprite.setX(sprite.getX() - overflow);
                limitListener.touchRight(overflow);
            }

            if (bounds.getMinY() < localBound.getMinY()) {
                double overflow = bounds.getMinY() - localBound.getMinY();
                sprite.setY(sprite.getY() - overflow);
                limitListener.touchTop(overflow);
            } else if (bounds.getMaxY() > localBound.getMaxY()) {
                double overflow = bounds.getMaxY() - localBound.getMaxY();
                sprite.setY(sprite.getY() - overflow);
                limitListener.touchBottom(overflow);
            }
        }
    }

}

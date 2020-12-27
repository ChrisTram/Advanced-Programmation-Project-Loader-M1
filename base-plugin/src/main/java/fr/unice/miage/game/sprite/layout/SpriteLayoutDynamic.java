package fr.unice.miage.game.sprite.layout;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Iterator;

public class SpriteLayoutDynamic extends SpriteLayoutFixed  implements ListChangeListener<MapElement>{
    public SpriteLayoutDynamic(double x, double y, double speedX, double speedY, boolean isRelative) {
        super(x, y, speedX, speedY, new Rectangle(), isRelative);
        children.addListener(this);
    }

    public void refreshShape() {
        Iterator<? extends Shape> iterator = children.stream().map(MapElement::getBoundingShape).iterator();
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE;
        double maxY = Double.MIN_VALUE;
        while (iterator.hasNext()) {
            Bounds bounds = iterator.next().getBoundsInParent();
            minX = Double.min(minX, bounds.getMinX());
            minY = Double.min(minY, bounds.getMinY());
            maxX = Double.max(maxX, bounds.getMaxX());
            maxY = Double.max(maxY, bounds.getMaxY());
        }

        surface.setHeight(maxY - minY);
        surface.setWidth(maxX - minX);
        surface.setX(minX);
        surface.setY(minY);
    }

    @Override
    public void onChanged(Change<? extends MapElement> change) {
        while (change.next())
            if(!change.wasPermutated())
                refreshShape();
    }
}

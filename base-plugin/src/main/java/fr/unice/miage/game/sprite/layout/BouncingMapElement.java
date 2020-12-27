package fr.unice.miage.game.sprite.layout;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.FakeDecorator;
import javafx.geometry.Bounds;

public class BouncingMapElement extends FakeDecorator<MapElement> {
    public BouncingMapElement(MapElement decorated) {
        super(decorated);
    }

    @Override
    public void update(double time, GameBoard b) {
        double oldX = getX();
        double oldY = getY();
        super.update(time, b);

        Bounds bounds = getBoundingShape().getBoundsInParent();
        if (bounds.getMaxX()>b.getWidth() || bounds.getMinX() < 0) {
            if(bounds.getMinX() < 0)
                setX(getX() - bounds.getMinX());
            else if(bounds.getMaxX()>b.getWidth()) {
                setX(getX() - (bounds.getMaxX() - b.getWidth()));
            }
            setSpeedX(-getSpeedX());
        }
        if (bounds.getMaxY()>b.getHeight() || bounds.getMinY() < 0){
            if(bounds.getMinY() < 0)
                setY(getY() - bounds.getMinY());
            else if(bounds.getMaxY()>b.getHeight()) {
                setY(getY() - (bounds.getMaxY() - b.getHeight()));
            }
            setSpeedY(-getSpeedY());
        }
    }

}

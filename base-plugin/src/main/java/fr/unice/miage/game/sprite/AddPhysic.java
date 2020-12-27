package fr.unice.miage.game.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;

public class AddPhysic extends FakeDecorator<MapElement> {
    protected double oldX;
    protected double oldY;

    public AddPhysic(MapElement decorated) {
        super(decorated);
    }

    /** If the other object has physic, we trigger the collision and set the object at a good position
     * @param b
     * @param p
     */
    @Override
    public void handleCollision(GameBoard b, MapElement p) {
        AddPhysic obstacle = p.getInstance(AddPhysic.class);
        int pendingIteration = 100;
        if(obstacle != null) {
            double currentX = getX();
            double currentY = getY();
            double nearestX = oldX;
            double nearestY = oldY;
            while (getBoundingShape().getBoundsInParent().intersects(p.getBoundingShape().getBoundsInParent()) && (getX() != nearestX || getY() != nearestY) && pendingIteration > 0) {
                currentX = getX();
                currentY = getY();
                setX((getX() + nearestX) / 2.);
                setY((getY() + nearestY) / 2.);
                while (!getBoundingShape().getBoundsInParent().intersects(p.getBoundingShape().getBoundsInParent()) && pendingIteration > 0) {
                    nearestX = getX();
                    nearestY = getY();

                    setX((currentX + nearestX) / 2.);
                    setY((currentY + nearestY) / 2.);
                    --pendingIteration;
                }
                --pendingIteration;
            }
        }
        super.handleCollision(b, p);
    }
    /**
     * We keep the working position in memory
     * @param time
     * @param b
     */
    @Override
    public void update(double time, GameBoard b) {
        oldX = getX();
        oldY = getY();
        super.update(time, b);
    }
}
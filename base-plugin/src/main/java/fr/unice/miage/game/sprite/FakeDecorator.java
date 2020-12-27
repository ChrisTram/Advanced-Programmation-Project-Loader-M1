package fr.unice.miage.game.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.reflection.Decorate;
import fr.unice.miage.game.reflection.DecoratorCasteable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Shape;

/**
 * Basic decorator to avoid code duplication.
 * Decorate every sprite
 */
public abstract class FakeDecorator<T extends MapElement> implements MapElement {
    @Decorate
    protected T decorated;

    @Override
    public <T extends DecoratorCasteable> T getInstance(Class<T> tClass) {
        if(tClass.isInstance(this))
            return tClass.cast(this);
        return decorated.getInstance(tClass);
    }

    public FakeDecorator(T decorated) {
        this.decorated = decorated;
    }

    @Override
    public double getX() {
        return decorated.getX();
    }

    @Override
    public void setX(double X) {
        decorated.setX(X);
    }

    @Override
    public double getY() {
        return decorated.getY();
    }

    @Override
    public void setY(double Y) {
        decorated.setY(Y);
    }

    @Override
    public void update(double time, GameBoard b) {
        decorated.update(time, b);
    }

    @Override
    public Shape getBoundingShape() {
        return decorated.getBoundingShape();
    }

    @Override
    public double getSpeedX() {
        return decorated.getSpeedX();
    }

    @Override
    public void setSpeedX(double speed) {
        decorated.setSpeedX(speed);
    }

    @Override
    public double getSpeedY() {
        return decorated.getSpeedY();
    }

    @Override
    public void setSpeedY(double speed) {
        decorated.setSpeedY(speed);
    }

    @Override
    public void render(GraphicsContext gc) {
        decorated.render(gc);
    }

    @Override
    public void handleCollision(GameBoard b, MapElement p) {
        decorated.handleCollision(b, p);
    }
}

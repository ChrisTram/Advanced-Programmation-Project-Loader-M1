package fr.unice.miage.game;

import fr.unice.miage.game.reflection.DecoratorCasteable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Shape;

public interface MapElement extends DecoratorCasteable {
    double getX();
    double getY();
    void setX(double X);
    void setY(double Y);

    void update(double time, GameBoard b);
    Shape getBoundingShape();

    double getSpeedX();
    double getSpeedY();
    void setSpeedX(double speed);
    void setSpeedY(double speed);

    void render(GraphicsContext gc);
    void handleCollision(GameBoard b, MapElement p);
}

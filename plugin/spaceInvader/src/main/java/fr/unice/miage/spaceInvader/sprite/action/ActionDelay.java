package fr.unice.miage.spaceInvader.sprite.action;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class ActionDelay implements MapElement {
    protected double delay;
    protected GameBoard board;

    public ActionDelay(double delay, GameBoard board) {
        this.delay = delay;
        this.board = board;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public void setX(double X) { }

    @Override
    public void setY(double Y) { }

    @Override
    public void update(double time, GameBoard b) {
        if(delay < time) {
            board.removeSprite(this);
            proceed();
        }
        else
            delay -= time;
    }

    protected abstract void proceed();

    @Override
    public Shape getBoundingShape() {
        return new Polygon();
    }

    @Override
    public double getSpeedX() {
        return 0;
    }

    @Override
    public double getSpeedY() {
        return 0;
    }

    @Override
    public void setSpeedX(double speed) {
    }

    @Override
    public void setSpeedY(double speed) {
    }

    @Override
    public void render(GraphicsContext gc) {
    }

    @Override
    public void handleCollision(GameBoard b, MapElement p) {
    }
}

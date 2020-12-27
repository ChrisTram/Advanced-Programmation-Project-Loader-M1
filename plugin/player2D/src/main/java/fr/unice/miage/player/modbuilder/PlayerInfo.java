package fr.unice.miage.player.modbuilder;

import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

public class PlayerInfo {
    private KeyCode up;
    private KeyCode left;
    private KeyCode down;
    private KeyCode right;
    private double speed;
    private Point2D position;

    public PlayerInfo() {
    }

    public PlayerInfo(KeyCode up, KeyCode left, KeyCode down, KeyCode right, double speed, Point2D position) {
        this.up = up;
        this.left = left;
        this.down = down;
        this.right = right;
        this.speed = speed;
        this.position = position;
    }

    public KeyCode getUp() {
        return up;
    }

    public void setUp(KeyCode up) {
        this.up = up;
    }

    public KeyCode getLeft() {
        return left;
    }

    public void setLeft(KeyCode left) {
        this.left = left;
    }

    public KeyCode getDown() {
        return down;
    }

    public void setDown(KeyCode down) {
        this.down = down;
    }

    public KeyCode getRight() {
        return right;
    }

    public void setRight(KeyCode right) {
        this.right = right;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}

package fr.unice.miage.spaceInvader;

import fr.unice.miage.game.Input;
import javafx.scene.input.KeyCode;

public class PlayerInput {
    private final Input input;

    private final KeyCode moveLeft;
    private final KeyCode moveRight;
    private final KeyCode fire;

    public PlayerInput(Input input, KeyCode moveLeft, KeyCode moveRight, KeyCode fire) {
        this.input = input;
        this.moveLeft = moveLeft;
        this.moveRight = moveRight;
        this.fire = fire;
    }

    public boolean isMovingLeft() {
        return input.isPressed(moveRight);
    }

    public boolean isMovingRight() {
        return input.isPressed(moveLeft);
    }

    public boolean isFire() {
        return input.isPressed(fire);
    }
}

package fr.unice.miage.game;

import javafx.scene.input.KeyCode;

public class PlayerInput {
    private KeyCode upKey = KeyCode.UP;
    private KeyCode downKey = KeyCode.DOWN;
    private KeyCode leftKey = KeyCode.LEFT;
    private KeyCode rightKey = KeyCode.RIGHT;
    private final KeyCode primaryWeaponKey = KeyCode.SPACE;
    private final KeyCode secondaryWeaponKey = KeyCode.CONTROL;
    private Input input;

    public PlayerInput(KeyCode upKey, KeyCode downKey, KeyCode leftKey, KeyCode rightKey, Input input) {
        this.upKey = upKey;
        this.downKey = downKey;
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.input = input;
    }

    public boolean isMoveUp() {
        return input.isPressed(upKey) && !input.isPressed(downKey);
    }

    public boolean isMoveDown() {
        return input.isPressed(downKey) && !input.isPressed(upKey);
    }

    public boolean isMoveLeft() {
        return input.isPressed(leftKey) && !input.isPressed(rightKey);
    }

    public boolean isMoveRight() {
        return input.isPressed(rightKey) && !input.isPressed(leftKey);
    }

    public boolean isFirePrimaryWeapon() {
        return input.isPressed(primaryWeaponKey);
    }

    public boolean isFireSecondaryWeapon() {
        return input.isPressed(secondaryWeaponKey);
    }

}

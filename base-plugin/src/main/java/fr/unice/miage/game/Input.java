package fr.unice.miage.game;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.BitSet;


// code from https://stackoverflow.com/questions/28709581/how-do-i-check-a-key-press-in-java
// author : https://stackoverflow.com/users/1844265/roland

public class Input {

    /**
     * Bitset which registers if any {@link KeyCode} keeps being pressed or if it is released.
     */
    private final BitSet keyboardBitSet = new BitSet();

    // -------------------------------------------------
    // default key codes
    // We can manage the key codes and add support for more players
    // -------------------------------------------------

    Scene scene;

    public Input(Scene scene) {
        this.scene = scene;
    }

    public void addListeners() {

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

    }

    public void removeListeners() {

        scene.removeEventFilter(KeyEvent.KEY_PRESSED, keyPressedEventHandler);
        scene.removeEventFilter(KeyEvent.KEY_RELEASED, keyReleasedEventHandler);

    }

    /**
     * "Key Pressed" handler for all input events: register pressed key in the bitset
     */
    private final EventHandler<KeyEvent> keyPressedEventHandler = event -> {

        // register key down
        keyboardBitSet.set(event.getCode().ordinal(), true);

    };

    /**
     * "Key Released" handler for all input events: unregister released key in the bitset
     */
    private final EventHandler<KeyEvent> keyReleasedEventHandler = event -> {
        // register key up
        keyboardBitSet.set(event.getCode().ordinal(), false);
    };


    // -------------------------------------------------
    // Evaluate bitset of pressed keys and return the player input.
    // If direction and its opposite direction are pressed simultaneously, then the direction isn't handled.
    // -------------------------------------------------

    public boolean isPressed(KeyCode keyCode) {
        return keyboardBitSet.get(keyCode.ordinal());
    }
}
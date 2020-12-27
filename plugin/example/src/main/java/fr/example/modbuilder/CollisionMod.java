package fr.example.modbuilder;

import fr.example.gamemode.CollisionMechanic;
import fr.unice.miage.game.gamemode.Gamemode;
import fr.unice.miage.game.gamemode.ModBuilder;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;

/**
 * CollisionMod make any registered MapElement collidable
 */
public class CollisionMod implements ModBuilder {
    private boolean useCollision = false;
    private final CheckBox checkBoxCollision;

    public CollisionMod() {
        // checkbox to enable or disable the wrap of collision
        checkBoxCollision = new CheckBox("Collision enable");
    }

    /**
     * provide an interface to configure the mod
     * @return Parent
     */
    @Override
    public Parent getConfigurePanel() {
        return checkBoxCollision;
    }

    /**
     * confirm the change made from ui
     */
    @Override
    public void validConfiguration() {
        useCollision = checkBoxCollision.isSelected();
    }

    /**
     *
     * @return
     */
    @Override
    public Gamemode[] generates() {
            return useCollision ? new Gamemode[] { new CollisionMechanic() } : new Gamemode[0];
    }
}

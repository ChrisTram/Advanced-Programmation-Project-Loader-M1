package fr.unice.miage.game.gamemode;

import javafx.scene.Parent;

/**
 * Build and configure mods
 */
public interface ModBuilder {
    Parent getConfigurePanel();
    void validConfiguration();
    Gamemode[] generates();
}

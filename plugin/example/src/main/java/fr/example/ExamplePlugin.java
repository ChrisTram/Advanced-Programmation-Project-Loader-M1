package fr.example;

import fr.example.modbuilder.CollisionMod;
import fr.unice.miage.game.Plugin;
import fr.unice.miage.game.gamemode.ModBuilder;

public class ExamplePlugin implements Plugin {
    /**
     * Fonction to tell the name of the plugin
     * @return the name
     */
    @Override
    public String getName() {
        return "Example";
    }

    /**
     * Fonction to tell the description of the plugin
     * @return the description
     */
    @Override
    public String getDescription() {
        return "Hello World";
    }

    /**
     * Fonction return configurable mods
     * @return the mod builder
     */
    @Override
    public ModBuilder[] getMods() {
        return new ModBuilder[]{
                new CollisionMod()
        };
    }
}

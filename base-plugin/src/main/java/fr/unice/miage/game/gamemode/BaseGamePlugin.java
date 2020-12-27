package fr.unice.miage.game.gamemode;

import fr.unice.miage.game.Plugin;

public class BaseGamePlugin implements Plugin {
    @Override
    public String getName() {
        return "first plugin";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public ModBuilder[] getMods() {
        return new ModBuilder[] {
                new BaseGameBuilder()
        };
    }
}

package fr.unice.miage.spaceInvader;

import fr.unice.miage.game.Plugin;
import fr.unice.miage.game.gamemode.ModBuilder;
import fr.unice.miage.spaceInvader.modbuilder.SpaceInvaderBuilder;

public class SpaceInvadePlugin implements Plugin {
    @Override
    public String getName() {
        return "Space Invader";
    }

    @Override
    public String getDescription() {
        return "Old C++ project";
    }

    @Override
    public ModBuilder[] getMods() {
        return new ModBuilder[]{
            new SpaceInvaderBuilder()
        };
    }
}

package fr.unice.miage.game;

import fr.unice.miage.game.gamemode.ModBuilder;

import java.util.*;

public interface Plugin {
    String getName();
    String getDescription();
    ModBuilder[] getMods();
}

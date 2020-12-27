package fr.unice.miage.game.reflection;

import fr.unice.miage.game.gamemode.Gamemode;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequireGamemode {
    Class<? extends Gamemode>[] gamemodes();
}

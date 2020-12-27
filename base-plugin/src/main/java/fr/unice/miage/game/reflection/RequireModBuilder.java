package fr.unice.miage.game.reflection;

import fr.unice.miage.game.gamemode.ModBuilder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequireModBuilder {
    Class<? extends ModBuilder>[] mods();
}

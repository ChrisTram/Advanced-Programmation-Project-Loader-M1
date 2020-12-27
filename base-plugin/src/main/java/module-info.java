open module base.plugin {
    exports fr.unice.miage.game.collision to base.game;
    exports fr.unice.miage.game;
    exports fr.unice.miage.game.utils;
    exports fr.unice.miage.game.sprite;
    exports fr.unice.miage.game.sprite.layout;
    exports fr.unice.miage.game.gamemode;
    exports fr.unice.miage.game.reflection;
    exports fr.unice.miage.graph;
    requires transitive javafx.controls;
    provides fr.unice.miage.game.Plugin with fr.unice.miage.game.gamemode.BaseGamePlugin;
}
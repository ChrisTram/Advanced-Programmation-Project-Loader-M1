module base.game {
    requires javafx.controls;
    requires javafx.fxml;
    requires base.plugin;

    exports fr.unice.miage.engine.gui to javafx.graphics;
    opens fr.unice.miage.engine.gui to javafx.graphics;
    exports fr.unice.miage.engine to javafx.fxml;
    opens fr.unice.miage.engine to javafx.fxml;
    uses fr.unice.miage.game.Plugin;
    opens fr.unice.miage.classloader;
    exports fr.unice.miage.classloader;
}
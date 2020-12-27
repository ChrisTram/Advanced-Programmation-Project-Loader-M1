module player2D {
    requires javafx.fxml;
    requires base.plugin;
    exports fr.unice.miage.player.modbuilder to javafx.fxml;
    opens fr.unice.miage.player.modbuilder to javafx.fxml;
    provides fr.unice.miage.game.Plugin with fr.unice.miage.player.PlayerPlugin;
}
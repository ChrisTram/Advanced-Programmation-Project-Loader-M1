package fr.unice.miage.player;

import fr.unice.miage.game.Plugin;
import fr.unice.miage.game.gamemode.ModBuilder;
import fr.unice.miage.player.modbuilder.PlayerBuilder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class PlayerPlugin implements Plugin {
    @Override
    public String getName() {
        return "player";
    }

    @Override
    public String getDescription() {
        return "allow player";
    }

    @Override
    public ModBuilder[] getMods() {
        Parent root = null;
        final FXMLLoader fxmlLoader = new FXMLLoader(PlayerBuilder.class.getResource("PlayerBuilder.fxml"));
        PlayerBuilder builder;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        builder = fxmlLoader.getController();
        if (builder == null)
            return new ModBuilder[0];
        builder.setRoot(root);
        return new ModBuilder[]{
                builder
        };
    }
}

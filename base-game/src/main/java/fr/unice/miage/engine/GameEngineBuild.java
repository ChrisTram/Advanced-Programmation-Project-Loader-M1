package fr.unice.miage.engine;

import fr.unice.miage.game.GameEngine;
import fr.unice.miage.game.MapGenerator;
import fr.unice.miage.game.gamemode.Gamemode;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

import java.util.List;

public class GameEngineBuild {
    protected Canvas canvas;
    protected Scene scene;
    protected List<Gamemode> gamemodes;
    protected MapGenerator mapGenerator;

    public GameEngineBuild setCanvas(Canvas canvas) {
        this.canvas = canvas;
        return this;
    }

    public GameEngineBuild setGamemodes(List<Gamemode> gamemodes) {
        this.gamemodes = gamemodes;
        return this;
    }

    public GameEngineBuild setMapGenerator(MapGenerator mapGenerator) {
        this.mapGenerator = mapGenerator;
        return this;
    }

    public GameEngineBuild setScene(Scene scene) {
        this.scene = scene;
        return this;
    }

    public GameEngine build() {
        return new BasicGameEngine(scene, canvas, gamemodes, mapGenerator);
    }
}

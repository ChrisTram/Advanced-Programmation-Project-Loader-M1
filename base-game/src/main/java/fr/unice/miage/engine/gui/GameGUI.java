package fr.unice.miage.engine.gui;

import fr.unice.miage.engine.PluginInitializer;
import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.GameEngine;
import fr.unice.miage.engine.GameEngineBuild;
import fr.unice.miage.engine.GameLoader;
import fr.unice.miage.game.Plugin;
import fr.unice.miage.game.gamemode.BaseGameBuilder;
import fr.unice.miage.game.gamemode.ModBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class GameGUI extends Application {
	@Override
	public void start(Stage stage) {
		initGame(stage);
	}

	/**
	 * Create the GUI, start the game engine
	 * @param stage
	 */
	private void initGame(Stage stage) {

		stage.setTitle("Demo de jeu");
		Group root = new Group();
		Scene theScene = new Scene(root);
		stage.setScene(theScene);
		Canvas canvas = new Canvas(512, 512);
		List<String> paths = getParameters().getRaw();

		List<ModBuilder> mods = new ArrayList<>();
		try {
			FXMLLoader loader = new FXMLLoader();
			Stage formStage = new Stage();
//			loader.setController(new PluginInitializer());
			Parent form = loader.load(Objects.requireNonNull(PluginInitializer.class.getResourceAsStream("PluginInitializer.fxml")));
			PluginInitializer initializer = loader.getController();
			initializer.setPrimaryStage(formStage);
			formStage.setScene(new Scene(form));
			formStage.showAndWait();
			mods.addAll(initializer.generates());
		} catch (IOException e) {
			e.printStackTrace();
		}
		GameLoader loader = new GameLoader(mods, new GameEngineBuild().setScene(theScene).setCanvas(canvas).setMapGenerator(GameBoard::new));
		loader.showAndWait();
		GameEngine gameEngine = loader.newGameEngine();
		root.getChildren().add(canvas);
		stage.sizeToScene();

		//c'est notre boucle de jeu principale
		gameEngine.start();
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

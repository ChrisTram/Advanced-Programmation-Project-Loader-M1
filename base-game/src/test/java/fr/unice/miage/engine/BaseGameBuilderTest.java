package fr.unice.miage.engine;

import fr.unice.miage.engine.Loader;
import fr.unice.miage.game.gamemode.BaseGameBuilder;
import fr.unice.miage.game.gamemode.Gamemode;
import fr.unice.miage.game.gamemode.ModBuilder;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseGameBuilderTest extends ApplicationTest {
    private BorderPane root;
    private Scene theScene;
    private BaseGameBuilder gameBuilder;
    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        Scene scene = new Scene(root, 480, 320);
        gameBuilder = new BaseGameBuilder();
        theScene = scene;
        root.setCenter(gameBuilder.getConfigurePanel());
        stage.setScene(scene);
        stage.show();
    }

    @Test
    void test_base_builder() {
        BaseGameBuilder gameBuilder = new BaseGameBuilder();
        Gamemode[] g = gameBuilder.generates();
        assertEquals(3, g.length);
    }

    @Test
    void test_configured_builder_2_to_1_player() {
        clickOn("#buttonAddPlayer");
        clickOn("#buttonAddPlayer");
        clickOn("#textObstacle").write("40 40");
        clickOn("#defineUpKey_2").type(KeyCode.Z);
        clickOn("#defineDownKey_2").type(KeyCode.S);
        clickOn("#defineLeftKey_2").type(KeyCode.Q);
        clickOn("#defineRightKey_2").type(KeyCode.D);
        clickOn("#deleteButton_2");
        gameBuilder.validConfiguration();
        List<ModBuilder> one = new ArrayList<>();
        one.add(gameBuilder);
        List<Gamemode> g = Loader.load(one);
        assertEquals(3, g.size());
    }

/*    @Test
    void test_configured_builder_1_player() {
        clickOn("#buttonAddPlayer");
        clickOn("#defineUpKey_1").type(KeyCode.Z);
        clickOn("#defineDownKey_1").type(KeyCode.S);
        clickOn("#defineLeftKey_1").type(KeyCode.Q);
        clickOn("#defineRightKey_1").type(KeyCode.D);

        clickOn("#loadFileButton");

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(BaseGameBuilderTest.class.getClassLoader().getResource("fr/unice/miage/NullMap").getPath().substring(1).replace("\\", "/"));
        clipboard.setContents(stringSelection, stringSelection);
        press(KeyCode.CONTROL).press(KeyCode.V).release(KeyCode.V).release(KeyCode.CONTROL);
        push(KeyCode.ENTER);

        gameBuilder.validConfiguration();
        List<ModBuilder> one = new ArrayList<>();
        one.add(gameBuilder);
        List<Gamemode> g = Loader.load(one);
        assertEquals(3, g.size());
    }*/

    @Test
    void test_configured_builder_2_player() {
        clickOn("#buttonAddPlayer");
        clickOn("#buttonAddPlayer");
        clickOn("#textObstacle").write("40 40");
        clickOn("#defineUpKey_2").type(KeyCode.Z);
        clickOn("#defineDownKey_2").type(KeyCode.S);
        clickOn("#defineLeftKey_2").type(KeyCode.Q);
        clickOn("#defineRightKey_2").type(KeyCode.D);
        gameBuilder.validConfiguration();
        List<ModBuilder> one = new ArrayList<>();
        one.add(gameBuilder);
        List<Gamemode> g = Loader.load(one);
        assertEquals(3, g.size());
    }
}
package fr.unice.miage.engine;

import fr.unice.miage.game.gamemode.Gamemode;
import fr.unice.miage.game.gamemode.ModBuilder;
import fr.unice.miage.game.reflection.RequireGamemode;
import fr.unice.miage.game.reflection.RequireModBuilder;
import javafx.scene.Parent;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class LoaderTest {
    @Test
    void test_sort_mod() {
        List<ModBuilder> list = new ArrayList<>(2);
        list.add(new TestB());
        list.add(new TestA());
        List<ModBuilder> sorted = Loader.sort(Collections.unmodifiableList(list));
        assertNotSame(list, sorted);
        assertEquals(list.size(), sorted.size());
        assertEquals(list.get(0), sorted.get(1));
        assertEquals(list.get(1), sorted.get(0));
        list.clear();
        list.add(new TestA());
        list.add(new TestB());

        sorted = Loader.sort(Collections.unmodifiableList(list));
        assertNotSame(list, sorted);
        assertEquals(list.size(), sorted.size());
        assertEquals(list.get(0), sorted.get(0));
        assertEquals(list.get(1), sorted.get(1));
    }

    @Test
    void test_loading_mod() {
        List<ModBuilder> list = new ArrayList<>(2);
        list.add(new TestB());
        list.add(new TestA());
        List<Gamemode> gamemodes = Loader.load(Collections.unmodifiableList(list));
        assertEquals(gamemodes.get(0).getClass(), GamemodeA.class);
        assertEquals(gamemodes.get(1).getClass(), GamemodeB.class);

        list.clear();
        list.add(new TestA());
        list.add(new TestB());

        gamemodes = Loader.load(Collections.unmodifiableList(list));
        assertEquals(gamemodes.get(0).getClass(), GamemodeA.class);
        assertEquals(gamemodes.get(1).getClass(), GamemodeB.class);
    }

    class GamemodeA implements Gamemode {

    }

    @RequireGamemode(gamemodes = GamemodeA.class)
    class GamemodeB implements Gamemode {

    }

    class TestA implements ModBuilder {

        @Override
        public Parent getConfigurePanel() {
            return null;
        }

        @Override
        public void validConfiguration() {

        }

        @Override
        public Gamemode[] generates() {
            return new Gamemode[] {
                    new GamemodeA()
            };
        }
    }

    @RequireModBuilder(mods = {TestA.class})
    class TestB implements ModBuilder{
        @Override
        public Parent getConfigurePanel() {
            return null;
        }

        @Override
        public void validConfiguration() {

        }

        @Override
        public Gamemode[] generates() {
            return new Gamemode[] {
                    new GamemodeB()
            };
        }
    }
}
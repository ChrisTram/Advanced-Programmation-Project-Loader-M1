package fr.unice.miage.game.sprite.layout;

import fr.unice.miage.game.MapElement;
import javafx.collections.ObservableList;

public interface Layout extends MapElement {
    ObservableList<MapElement> getChildren();
    boolean isRelative();
}

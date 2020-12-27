package fr.unice.miage.game.sprite.layout;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.Sprite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;

public abstract class GroupeSprite extends Sprite implements Layout {
    protected boolean isRelative;
    protected ObservableList<MapElement> children;

    public GroupeSprite(double x, double y, double speedX, double speedY, boolean isRelative) {
        super(x, y, speedX, speedY);
        this.isRelative = isRelative;
        children = FXCollections.observableArrayList();
    }

    @Override
    public void render(GraphicsContext gc) {
    }

    @Override
    public void update(double time, GameBoard b) {
        super.update(time, b);
    }

    @Override
    public void handleCollision(GameBoard b, MapElement p) {
    }

    public ObservableList<MapElement> getChildren() {
        return children;
    }

    @Override
    public boolean isRelative() {
        return isRelative;
    }
}

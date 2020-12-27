package fr.unice.miage.game.collision;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.layout.Layout;
import javafx.scene.canvas.GraphicsContext;

public class UpdateVisitor implements SpriteVisitor {    private final GameBoard board;
    private final double timestamp;

    public UpdateVisitor(double timestamp, GameBoard board) {
        this.timestamp = timestamp;
        this.board = board;
    }

    @Override
    public void visit(MapElement element) {
        element.update(timestamp, board);
    }

    @Override
    public void enter(Layout layout) {}

    @Override
    public void exit(Layout layout) {}
}

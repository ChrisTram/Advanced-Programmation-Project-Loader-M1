package fr.unice.miage.spaceInvader.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class DefaultBackGround extends Sprite {
    private final double cellSize;
    private final int column;
    private final int line;
    private final String image =
                    "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMM   MMMMMMMMMMMMMMMMMMM    MMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMM   MMMMMMMMMMMMMMMMMMM    MMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMMMMM    MMMMMMMMMMM    MMMMMMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMMMMM    MMMMMMMMMMM    MMMMMMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMMMMM    MMMMMMMMMMM    MMMMMMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMM                          MMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMM                          MMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMM       XXXX           XXXX        MMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMM       XXXX           XXXX        MMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMM       XXXX           XXXX        MMMMMMMMMMMMMMM\n"+
                    "MMMMMMM                                         MMMMMMMMMMMM\n"+
                    "MMMMMMM                                         MMMMMMMMMMMM\n"+
                    "MMMMMMM    MMMM                          MMMM   MMMMMMMMMMMM\n"+
                    "MMMMMMM    MMMM                          MMMM   MMMMMMMMMMMM\n"+
                    "MMMMMMM    MMMM   MMMMMMMMMMMMMMMMMMM    MMMM   MMMMMMMMMMMM\n"+
                    "MMMMMMM    MMMM   MMMMMMMMMMMMMMMMMMM    MMMM   MMMMMMMMMMMM\n"+
                    "MMMMMMM   MMMMM   MMMMMMMMMMMMMMMMMMM   MMMMM   MMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMMMMM        MMMM       MMMMMMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMMMMM        MMMM       MMMMMMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n"+
                    "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM";

    public DefaultBackGround(double x, double y, double speedX, double speedY, double cellSize) {
        super(x, y, speedX, speedY);
        this.cellSize = cellSize;
        String[] lines = image.split("\n");
        line = lines.length;
        column = lines[0].length();
    }

    /**
     * .--------------------`
     * |
     * '---------------------
     * @return
     */
    @Override
    public final Shape getBoundingShape() {
        return new Rectangle(x, y, column*cellSize, line*cellSize);
    }

    @Override
    public final void render(GraphicsContext gc) {
        TextRender.render(x, y, cellSize, image, Color.GREEN, Color.RED, Color.BLACK, gc);
    }

    @Override
    public void handleCollision(GameBoard b, MapElement p) {
    }
}

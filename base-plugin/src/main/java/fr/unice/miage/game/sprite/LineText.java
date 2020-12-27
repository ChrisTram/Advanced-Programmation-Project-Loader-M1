package fr.unice.miage.game.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.utils.FXRender;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

public class LineText extends Sprite {
    private Text text;
    private Rectangle shape;

    public LineText(double x, double y, double speedX, double speedY, Text text) {
        super(x, y, speedX, speedY);
        this.text = text;
        Bounds bounds = text.getBoundsInParent();
        shape = new Rectangle(0,0, bounds.getMaxX(), bounds.getMaxY());
    }

    @Override
    public Shape getBoundingShape() {
        return shape;
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        shape.setTranslateX(x);
    }

    @Override
    public void setY(double y) {
        super.setY(y);
        shape.setTranslateY(y);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save();
        gc.translate(x, y);
        FXRender.renderFill(text, gc);
        gc.restore();
    }

    @Override
    public void handleCollision(GameBoard board, MapElement target) {

    }
}

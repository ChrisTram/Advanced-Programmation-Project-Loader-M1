package fr.unice.miage.spaceInvader.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.Sprite;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CharSprite extends Sprite {
    Rectangle inner = new Rectangle();
    private final Text text;
    private final static Font font = new Font("Lucida Console Bold", 25);
    protected Color color;

    public CharSprite(double x, double y, double speedX, double speedY, char charSprite, Color color) {
        super(x, y, speedX, speedY);
        this.text = new Text(String.valueOf(charSprite));
        this.text.setFill(color);
        this.text.setFont(font);
        this.color = color;

        Bounds local = text.getBoundsInParent();
        inner.setX(x);
        inner.setY(y);
        inner.setHeight(local.getHeight());
        inner.setWidth(local.getWidth());

        Bounds bounds = inner.getBoundsInParent();
        if(bounds.getMinY() < 0)
            throw new RuntimeException("invalid bound");
    }

    @Override
    public Shape getBoundingShape() {
        return inner;
    }

    @Override
    public void setX(double x) {
        super.setX(x);
        inner.setX(x);
    }

    @Override
    public void setY(double y) {
        super.setY(y);
        inner.setY(y);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(text.getFill());
        gc.setFont(text.getFont());
        gc.setTextAlign(text.getTextAlignment());
        gc.setTextBaseline(text.getTextOrigin());
        Bounds textBound = inner.getBoundsInParent();
        gc.fillText(text.getText(), textBound.getMinX(), textBound.getMaxY());
    }

    @Override
    public void handleCollision(GameBoard board, MapElement target) {
    }
}

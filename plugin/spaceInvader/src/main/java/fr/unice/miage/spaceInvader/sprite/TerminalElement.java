package fr.unice.miage.spaceInvader.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.Sprite;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TerminalElement extends Sprite {
    private Color foreground;
    private Color background;
    private Rectangle surface;
    private List<Text> lineTexts = new ArrayList<>();


    public TerminalElement(double x, double y, double speedX, double speedY, Color foreground, Color background, Rectangle surface) {
        super(x, y, speedX, speedY);
        this.foreground = foreground;
        this.background = background;
        this.surface = surface;
    }

    public void addLine(Text lineText) {
        lineTexts.add(lineText);
    }

    public int linesCount() {
        return lineTexts.size();
    }

    public Text lastLine() {
        return lineTexts.get(lineTexts.size()-1);
    }

    public void clear() {
        lineTexts.clear();
    }

    public boolean isOverflow(Text text) {
        return getBoundingShape().getWidth() <= (text.getBoundsInParent().getWidth()+20);
    }

    @Override
    public Rectangle getBoundingShape() {
        return surface;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.save();
        gc.rect(surface.getX()+x, surface.getY()+y, surface.getWidth(), surface.getHeight());
        gc.setFill(background);
        gc.fillRect(surface.getX()+x, surface.getY()+y, surface.getWidth(), surface.getHeight());
        gc.clip();
        final double innerPadding = Math.sqrt(50);// 10 padding
        gc.setFill(foreground);
        gc.translate(x+innerPadding, y+innerPadding);
        for (Text text : lineTexts) {
            Bounds textBound = text.getBoundsInParent();
            gc.setFont(text.getFont());
            gc.fillText(text.getText(), textBound.getMinX(), textBound.getMinY()+textBound.getHeight());
            gc.translate(0, textBound.getHeight());
        }
        gc.restore();
    }

    @Override
    public void handleCollision(GameBoard board, MapElement target) {
    }
}

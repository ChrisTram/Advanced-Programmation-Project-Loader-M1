package fr.unice.miage.game.utils;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;

public class FXRender {
    public static void renderFill(Text text, GraphicsContext gc) {
        gc.setFill(text.getFill());
        gc.setFont(text.getFont());
        gc.setTextAlign(text.getTextAlignment());
        gc.setTextBaseline(text.getTextOrigin());
        Bounds textBound = text.getBoundsInParent();
        gc.fillText(text.getText(), textBound.getMinX(), textBound.getMaxY());
    }
    public static void renderStroke(Text text, GraphicsContext gc) {
        gc.setStroke(text.getStroke());
        gc.setFont(text.getFont());
        gc.setTextAlign(text.getTextAlignment());
        gc.setTextBaseline(text.getTextOrigin());
        Bounds textBound = text.getBoundsInParent();
        gc.strokeText(text.getText(), textBound.getMinX(), textBound.getMaxY());
    }
}

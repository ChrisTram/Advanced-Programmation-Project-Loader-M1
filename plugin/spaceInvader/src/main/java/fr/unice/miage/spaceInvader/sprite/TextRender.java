package fr.unice.miage.spaceInvader.sprite;

import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.util.Iterator;

public class TextRender {
    public static void render(double x, double y, double size, String text, Color eye, Color body, Color background, GraphicsContext cg) {
        Iterator<Integer> in = text.chars().iterator();
        double oldX = x;

        Polygon polygon = new Polygon();
        while (in.hasNext()) {
            char value = (char)(int)in.next();
            if(value == ' ') {
                cg.setFill(body);
                cg.fillRect(x-1, y-1, size+1, size+1);
            }
            else if(value == 'X') {
                cg.setFill(eye);
                cg.fillRect(x-1, y-1, size+1, size+1);
            }
            else if(value == 'M') {
                cg.setFill(background);
                cg.fillRect(x-1, y-1, size+1, size+1);
            }
            else if(value == '\n') {
                x = oldX;
                y += size;
                continue;
            }
            x += size;
        }
    }
}

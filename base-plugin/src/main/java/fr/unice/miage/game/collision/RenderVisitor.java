package fr.unice.miage.game.collision;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.layout.Layout;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.*;

public class RenderVisitor implements SpriteVisitor {
    private final GraphicsContext context;
    private final Queue<Translate> currentTransform;

    public RenderVisitor(GraphicsContext context) {
        this.context = context;
        currentTransform = Collections.asLifoQueue(new ArrayDeque<>());
        currentTransform.add(Translate.translate(0,0));
    }

    @Override
    public void visit(MapElement element) {
        element.render(context);
    }

    @Override
    public void enter(Layout layout) {
        context.save();
        if(layout.isRelative()) {
            Bounds bounds = layout.getBoundingShape().getBoundsInParent();
            context.translate(bounds.getMinX(), bounds.getMinY());
            Translate translate = currentTransform.peek();
            if(translate != null)
                currentTransform.add(Translate.translate(bounds.getMinX()+translate.getX(), bounds.getMinY()+translate.getY()));
            else
                currentTransform.add(Translate.translate(bounds.getMinX(), bounds.getMinY()));
        }
        else
            currentTransform.add(currentTransform.peek());
    }

    @Override
    public void exit(Layout layout) {
        currentTransform.poll();
        context.restore();
    }
}

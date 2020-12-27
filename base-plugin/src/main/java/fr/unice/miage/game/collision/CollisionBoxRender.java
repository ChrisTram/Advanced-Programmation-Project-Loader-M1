package fr.unice.miage.game.collision;

import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.layout.Layout;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;

import java.util.*;

public class CollisionBoxRender implements SpriteVisitor {
    private static final Color[] colors = new Color[]{
            Color.color(0.1,0.1,0.1),
            Color.color(0.2,0.1,0.1),
            Color.color(0.3,0.1,0.1),
            Color.color(0.4,0.1,0.1),
            Color.color(0.5,0.1,0.1),
            Color.color(0.6,0.1,0.1),
            Color.color(0.7,0.1,0.1),
            Color.color(0.8,0.1,0.1),
            Color.color(0.9,0.2,0.1),
            Color.color(0.9,0.3,0.1),
            Color.color(0.9,0.4,0.1),
            Color.color(0.9,0.5,0.1),
            Color.color(0.9,0.6,0.1),
            Color.color(0.9,0.7,0.1),
            Color.color(0.9,0.8,0.1),
            Color.color(0.1,0.3,0.9),
            Color.color(0.1,0.4,0.9),
            Color.color(0.1,0.5,0.9),
            Color.color(0.1,0.6,0.9),
            Color.color(0.1,0.8,0.9),
            Color.color(0.8,0.8,0.8),
            Color.color(0.7,0.7,0.7),
            Color.color(0.6,0.6,0.6),
            Color.color(0.5,0.5,0.5),
            Color.color(0.4,0.4,0.4),
            Color.color(0.3,0.3,0.3)
    };
    private int drawId = 0;
    private final GraphicsContext context;
    private Queue<List<Layout>> parents;

    public CollisionBoxRender(GraphicsContext context) {
        this.context = context;
        parents = Collections.asLifoQueue(new ArrayDeque<>());
        parents.add(Collections.emptyList());
    }

    @Override
    public void visit(MapElement element) {
        if(drawId >= colors.length)
            drawId = 0;
        Color color = colors[drawId++];
        context.setFill(color);
        Bounds bounds = element.getBoundingShape().getBoundsInParent();
        context.fillRect(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
    }

    @Override
    public void enter(Layout layout) {
        List<Layout> parent = new ArrayList<>(parents.peek().size());
        parent.addAll(parents.peek());
        parent.add(layout);
        parents.add(parent);
        context.save();
        if(layout.isRelative()) {
            Bounds bounds = layout.getBoundingShape().getBoundsInParent();
            context.translate(bounds.getMinX(), bounds.getMinY());
        }
    }

    @Override
    public void exit(Layout layout) {
        parents.poll();
        context.restore();
    }
}

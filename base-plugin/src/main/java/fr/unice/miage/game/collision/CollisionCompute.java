package fr.unice.miage.game.collision;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.AddPhysic;
import fr.unice.miage.game.sprite.layout.Layout;
import javafx.geometry.Bounds;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.*;

public class CollisionCompute implements SpriteVisitor {
    private static class Node {
        public final MapElement element;
        public final Translate parentToLocal;
        public final List<Layout> parents;

        public Node(MapElement element, Translate parentToLocal, List<Layout> parents) {
            this.element = element;
            this.parentToLocal = parentToLocal;
            this.parents = parents;
        }
    }
    private final GameBoard board;
    private final List<Node> previous;
    private final Queue<Translate> currentTransform;
    private Queue<List<Layout>> parents;

    public CollisionCompute(GameBoard board) {
        this.board = board;
        previous = new ArrayList<>();
        parents = Collections.asLifoQueue(new ArrayDeque<>());
        parents.add(Collections.emptyList());
        currentTransform = Collections.asLifoQueue(new ArrayDeque<>());
        currentTransform.add(Translate.translate(0,0));
    }

    @Override
    public void visit(MapElement element) {
        Translate current = currentTransform.peek();
        Shape elementShape = element.getBoundingShape();
        Bounds elementBound = elementShape.getBoundsInParent();
        if(element instanceof AddPhysic) {
            element.getBoundingShape();
        }

        for (Node translateEntry : previous) {
            MapElement target = translateEntry.element;
            List<? extends MapElement> layoutList = parents.peek();
            if(translateEntry.parents.contains(element) || layoutList.contains(target))
                continue;
            Shape targetShape = target.getBoundingShape();
            Translate targetTranslate = translateEntry.parentToLocal;
            Transform elementToTarget = targetTranslate.createInverse().createConcatenation(current);
//            Transform elementToTarget = current.createInverse().createConcatenation(targetTranslate);

            Bounds elementBoundInTarget = elementToTarget.transform(elementBound);
            Bounds targetBound = targetShape.getBoundsInParent();

            if (targetBound.intersects(elementBoundInTarget)) {
//            if (elementShape.intersects(targetToElement.transform(targetShape.getBoundsInParent())) || targetShape.intersects(elementToTarget.transform(elementShape.getBoundsInParent()))) {
                element.handleCollision(board, target);
                target.handleCollision(board, element);
            }
        }
        previous.add(new Node(element, current, parents.peek()));
    }

    @Override
    public void enter(Layout layout) {
        List<Layout> parent = new ArrayList<>(parents.peek().size()+1);
        parent.addAll(parents.peek());
        parent.add(layout);
        parents.add(parent);
        Translate translate = currentTransform.peek();
        if(layout.isRelative()) {
            Bounds bounds = layout.getBoundingShape().getBoundsInParent();
            currentTransform.add(
                    Transform.translate(translate.getX() + bounds.getMinX(), translate.getY() +  bounds.getMinY())
            );
        }
        else
            currentTransform.add(currentTransform.peek());
    }

    @Override
    public void exit(Layout layout) {
        parents.poll();
        currentTransform.poll();
    }
}

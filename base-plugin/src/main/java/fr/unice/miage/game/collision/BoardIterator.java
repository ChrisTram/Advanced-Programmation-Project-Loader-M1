package fr.unice.miage.game.collision;

import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.layout.Layout;

import java.util.*;

public class BoardIterator {
    private Collection<MapElement> elements;
    public BoardIterator(Collection<MapElement> elements) {
        this.elements = elements;
    }


    public void explore(SpriteVisitor visitor) {
        Deque<Iterator<MapElement>> elementsIterators = new ArrayDeque<>();
        Deque<Layout> parent = new ArrayDeque<>();
        for (MapElement element : elements) {
            visitor.visit(element);
            Layout layout = element.getInstance(Layout.class);
            if(layout != null) {
                if (layout.getChildren().isEmpty())
                    continue;
                visitor.enter(layout);
                Iterator<MapElement> iterator = new ArrayList<>(layout.getChildren()).iterator();
                while (iterator.hasNext()) {
                    MapElement e = iterator.next();
                    visitor.visit(e);
                    Layout layout1 = e.getInstance(Layout.class);
                    if(layout1 != null && !layout1.getChildren().isEmpty()) {
                        visitor.enter(layout1);
                        parent.add(layout1);
                        elementsIterators.add(iterator);
                        iterator = new ArrayList<>(layout1.getChildren()).iterator();
                    }
                    while (!iterator.hasNext() && !parent.isEmpty()) {
                        visitor.exit(parent.removeLast());
                        iterator = elementsIterators.removeLast();
                    }
                }
                visitor.exit(layout);
            }
        }
    }
}

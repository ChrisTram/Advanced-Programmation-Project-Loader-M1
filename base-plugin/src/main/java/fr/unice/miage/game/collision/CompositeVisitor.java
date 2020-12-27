package fr.unice.miage.game.collision;

import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.layout.Layout;

import java.util.Collection;
import java.util.List;

public class CompositeVisitor implements SpriteVisitor {
    private Collection<SpriteVisitor> visitors;

    public CompositeVisitor(SpriteVisitor... visitors) {
        this.visitors = List.of(visitors);
    }

    public CompositeVisitor(Collection<SpriteVisitor> visitors) {
        this.visitors = visitors;
    }

    @Override
    public void visit(MapElement element) {
        for (SpriteVisitor spriteVisitor : visitors)
            spriteVisitor.visit(element);
    }

    @Override
    public void enter(Layout layout) {
        for (SpriteVisitor spriteVisitor : visitors)
            spriteVisitor.enter(layout);
    }

    @Override
    public void exit(Layout layout) {
        for (SpriteVisitor spriteVisitor : visitors)
            spriteVisitor.exit(layout);
    }
}

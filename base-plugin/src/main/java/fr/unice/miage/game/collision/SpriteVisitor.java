package fr.unice.miage.game.collision;

import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.sprite.layout.Layout;

public interface SpriteVisitor {
    void visit(MapElement element);
    void enter(Layout layout);
    void exit(Layout layout);
}

package fr.unice.miage.game.sprite;

import fr.unice.miage.game.GameBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class AddPhysicTest {
    @Mock
    GameBoard board;
    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void test_position() {
        AddPhysic e1 = new AddPhysic(new RectangleSprite(0,0,0, 0));
        AddPhysic e2 = new AddPhysic(new RectangleSprite(-100,0,100, 0));
        assertFalse(e1.getBoundingShape().getBoundsInParent().intersects(e2.getBoundingShape().getBoundsInParent()));
        e1.update(0, board);
        e2.update(1, board);
        assertTrue(e1.getBoundingShape().getBoundsInParent().intersects(e2.getBoundingShape().getBoundsInParent()));
        double baseX = e1.getX();
        double baseY = e1.getY();
        e1.handleCollision(board, e2);
        assertEquals(baseX, e1.getX());
        assertEquals(baseY, e1.getY());
        baseX = e2.getX();
        baseY = e2.getY();
        e2.handleCollision(board, e1);
        assertNotEquals(baseX, e2.getX(), 1e-9);
        assertEquals(baseY, e2.getY(), 1e-9);
    }
}
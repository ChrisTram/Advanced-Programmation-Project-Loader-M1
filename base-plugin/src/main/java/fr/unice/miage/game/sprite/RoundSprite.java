package fr.unice.miage.game.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class RoundSprite extends Sprite {
	
	private int diameter; 

	public RoundSprite(double x, double y, double speedX, double speedY, int diameter) {
		super(x, y, speedX, speedY);
		this.diameter = diameter;
	}

	@Override
	public void render(GraphicsContext gc) {
		gc.setFill(Color.RED);
		gc.strokeOval(x,y, diameter, diameter);
		gc.fillOval(x, y, diameter, diameter);
	}


	@Override
	public void handleCollision(GameBoard b, MapElement p) {
		System.out.println("RoundSprite.handleCollision()");
	}


	@Override
	public Shape getBoundingShape() {
		return new Circle((x+x+diameter)/2, (y+y+diameter)/2, diameter/2.);
	}

}

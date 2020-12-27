package fr.unice.miage.game.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.Random;

public class RectangleSprite extends Sprite {
	private int w =20;
	private int h = 20;
	
	private Color[] colors = new Color[] { Color.RED, Color.CYAN, Color.DARKCYAN };
	private int currentColor = 0; 
	
	private Random r = new Random();
	
	public RectangleSprite(double x, double y, double speedX, double speedY) {
		super(x, y, speedX, speedY);
	}
	

	@Override
	public void render(GraphicsContext gc) {
		Paint save = gc.getFill();
//		System.out.println(" colors " + colors[currentColor] );
		gc.setFill(colors[currentColor]);
		gc.fillRect(x, y, w, h);
		
		gc.setFill(save);
	}

	@Override
	public void handleCollision(GameBoard b, MapElement p) {
		currentColor =  r.nextInt(colors.length); 
	}

	@Override
	public Shape getBoundingShape() {
		return new Rectangle(x,y,w,h);
		
	}

}

package fr.unice.miage.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

public class GameBoard {
	private Color background = Color.AZURE;
	private int width;
	private int height;
	private final Rectangle surface;
	private final List<MapElement> list = new ArrayList<>();
	
	public GameBoard(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		surface = new Rectangle(width, height);
	}

	public Rectangle getSurface() {
		return surface;
	}

	public void addSprite(MapElement p) {
		this.list.add(Objects.requireNonNull(p));
	}

	public void removeSprite(MapElement p) {
		Objects.requireNonNull(p);
		this.list.removeIf(c -> p.equals(c.getInstance(p.getClass())));
	}

	public Iterator<MapElement> spriteIterator() {
		return list.iterator();
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}
}

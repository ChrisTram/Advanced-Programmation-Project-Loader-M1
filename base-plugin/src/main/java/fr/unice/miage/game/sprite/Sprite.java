package fr.unice.miage.game.sprite;


import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.reflection.DecoratorCasteable;

public abstract class Sprite implements MapElement {
	protected double x;
	protected double y;
	protected double speedX;
	protected double speedY;


	public Sprite(double x, double y, double speedX, double speedY) {
		super();
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
	}

	public void update(double time, GameBoard b) {
		setX(x + speedX * time);
		setY(y + speedY * time);
	}

	@Override
	public <T extends DecoratorCasteable> T getInstance(Class<T> tClass) {
		if(tClass.isInstance(this))
			return tClass.cast(this);
		return null;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public double getSpeedX() {
		return speedX;
	}

	@Override
	public void setSpeedX(double speed) {
		this.speedX = speed;
	}

	@Override
	public double getSpeedY() {
		return speedY;
	}

	@Override
	public void setSpeedY(double speed) {
		this.speedY = speed;
	}

}

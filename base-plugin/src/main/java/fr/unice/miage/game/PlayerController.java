package fr.unice.miage.game;

import fr.unice.miage.game.sprite.FakeDecorator;
import fr.unice.miage.game.sprite.Sprite;

public class PlayerController extends FakeDecorator<Sprite> {
    PlayerInput input;

    double speed; //for bonus

    public PlayerController(Sprite player, PlayerInput input, double speed) {
        super(player);
        this.input = input;
        this.speed = speed;
    }

    public void processInput(GameBoard b) {
        // vertical direction
        if (input.isMoveUp()) {
            setSpeedY(-speed);
        } else if (input.isMoveDown()) {
            setSpeedY(speed);
        } else {
            setSpeedY(0);
        }

        // horizontal direction
        if (input.isMoveLeft()) {
            setSpeedX(-speed);
        } else if (input.isMoveRight()) {
            setSpeedX(speed);
        } else {
            setSpeedX(0);
        }

    }

    @Override
    public void update(double time, GameBoard b) {
        super.update(time, b);
        this.processInput(b);
    }

    @Override
    public void handleCollision(GameBoard b, MapElement p) {
        System.out.println("Player.handleCollision()");
    }

}
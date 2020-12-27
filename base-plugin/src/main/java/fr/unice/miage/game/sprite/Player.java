package fr.unice.miage.game.sprite;

import fr.unice.miage.game.GameBoard;
import fr.unice.miage.game.Input;
import fr.unice.miage.game.MapElement;
import fr.unice.miage.game.PlayerInput;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.Random;
import static java.lang.Math.sqrt;


public class Player extends Sprite {

    private Color[] colors = new Color[]{Color.RED, Color.CYAN, Color.DARKCYAN};
    private int currentColor = 0;
    private int diameter;
    PlayerInput input;

    double speed; //for bonus
    private int cpt = 0;
    private boolean cbRandom;
    private boolean cbEscape;
    private boolean cbFollow;

    private static double playersNb;
    private int id;
    private static ArrayList<ArrayList<Double>> playersList = new ArrayList<ArrayList<Double>>();
    private static double oldDistance=512;
    private static double newDistance=512;
    private static double idClosestPlayer = 99;

    public Player(double x, double y, double speedX, double speedY, int diameter, PlayerInput input, int speed, boolean cbRandom, boolean cbEscape, boolean cbFollow) {
        super(x, y, speedX, speedY);
        this.diameter = diameter;
        this.input = input;
        this.speed = speed;
        this.cbRandom = cbRandom;
        this.cbEscape = cbEscape;
        this.cbFollow = cbFollow;
        this.id = (int)playersNb;
        this.playersNb++;
        ArrayList<Double> temp = new ArrayList<>();
        temp.add((double)id);
        temp.add(x);
        temp.add(y);
        playersList.add(temp);
    }

    public void processInput(GameBoard b) {


        // vertical direction
        if (input.isMoveUp()) {
            if ((this.y) < 0) {
                speedY = 0;
            } else {
                speedY = -speed;
            }
        } else if (input.isMoveDown()) {
            if ((this.y + diameter) > b.getWidth()) {
                speedY = 0;
            } else {
                speedY = speed;
            }
        } else {
            speedY = 0d;
        }


        // horizontal direction
        if (input.isMoveLeft()) {
            if ((this.x) < 0) {
                speedX = 0;
            } else {
                speedX = -speed;
            }
        } else if (input.isMoveRight()) {
            if ((this.x + diameter) > b.getWidth()) {
                speedX = 0;
            } else {
                speedX = speed;
            }
        } else {
            speedX = 0d;
        }

    }
    public void random(GameBoard b){
        Random rand = new Random();

        int rand_int1 = rand.nextInt(2);
        int rand_int2 = rand.nextInt(2);

        //deplacements verticaux
        if(rand_int1==0){
            if ((this.y) < 0) {
                speedY = 0;
            } else {
                speedY = -speed;
            }
        }
        else {
            if ((this.y + diameter) > b.getWidth()) {
                speedY = 0;
            } else {
                speedY = speed;
            }
        }

        //deplacements horizontaux
        if (rand_int2==0) {
            if ((this.x) < 0) {
                speedX = 0;
            } else {
                speedX = -speed;
            }
        }
        else {
            if ((this.x + diameter) > b.getWidth()) {
                speedX = 0;
            } else {
                speedX = speed;
            }
        }
    }

    public void escape(GameBoard b){
        for(int i=0;i<playersList.size();i++){
            if(playersList.get(i).get(0)!=this.id) {
                //System.out.println("player : "+i);
                //System.out.println(playersList.get(i).get(1));
                //System.out.println(playersList.get(i).get(2));
                //System.out.println("Distance entre "+this.id+" et "+i+" : "+ calculateDistance(this.x, this.y, playersList.get(i).get(1), playersList.get(i).get(2)));
                newDistance =  calculateDistance(this.x, this.y, playersList.get(i).get(1), playersList.get(i).get(2));
                //System.out.println("oldDistance : "+oldDistance);
                //System.out.println("newDistance : "+newDistance);
                if(newDistance < oldDistance){
                    oldDistance = newDistance;
                    idClosestPlayer = i;
                }
                if(i==idClosestPlayer){
                    oldDistance=newDistance;
                }
                //System.out.println("Joueur le plus proche de "+this.id+" est : "+idClosestPlayer);

                if(this.x<playersList.get((int)idClosestPlayer).get(1)){ //si le bot doit aller à gauche

                    if ((this.x) < 0) {
                        speedX = 0;
                    } else {
                        speedX = -speed;
                    }
                }else if(this.x>playersList.get((int)idClosestPlayer).get(1)){ //si le bot doit aller à droite
                    if ((this.x + diameter) > b.getWidth()) {
                        speedX = 0;
                    } else {
                        speedX = speed;
                    }
                }

                if(this.y<playersList.get((int)idClosestPlayer).get(2)){ //si le bot doit aller en bas
                    if ((this.y) < 0) {
                        speedY = 0;
                    } else {
                        speedY = -speed;
                    }
                }else if(this.y>playersList.get((int)idClosestPlayer).get(2)){ //si le bot doit aller en haut
                    if ((this.y + diameter) > b.getWidth()) {
                        speedY = 0;
                    } else {
                        speedY = speed;
                    }
                }
            }
        }
    }

    public void follow(GameBoard b){
        for(int i=0;i<playersList.size();i++){
            if(playersList.get(i).get(0)!=this.id) {
                //System.out.println("player : "+i);
                //System.out.println(playersList.get(i).get(1));
                //System.out.println(playersList.get(i).get(2));
                //System.out.println("Distance entre "+this.id+" et "+i+" : "+ calculateDistance(this.x, this.y, playersList.get(i).get(1), playersList.get(i).get(2)));
                newDistance =  calculateDistance(this.x, this.y, playersList.get(i).get(1), playersList.get(i).get(2));
                //System.out.println("oldDistance : "+oldDistance);
                //System.out.println("newDistance : "+newDistance);
                if(newDistance < oldDistance){
                    oldDistance = newDistance;
                    idClosestPlayer = i;
                }
                if(i==idClosestPlayer){
                    oldDistance=newDistance;
                }
                //System.out.println("Joueur le plus proche de "+this.id+" est : "+idClosestPlayer);

                if(this.x<playersList.get((int)idClosestPlayer).get(1)){ //si le bot doit aller à droite
                    if ((this.x + diameter) > b.getWidth()) {
                        speedX = 0;
                    } else {
                        speedX = speed;
                    }
                }else if(this.x>playersList.get((int)idClosestPlayer).get(1)){ //si le bot doit aller à gauche
                    if ((this.x) < 0) {
                        speedX = 0;
                    } else {
                        speedX = -speed;
                    }
                }

                if(this.y<playersList.get((int)idClosestPlayer).get(2)){ //si le bot doit aller en haut
                    if ((this.y + diameter) > b.getWidth()) {
                        speedY = 0;
                    } else {
                        speedY = speed;
                    }
                }else if(this.y>playersList.get((int)idClosestPlayer).get(2)){ //si le bot doit aller en bas
                    if ((this.y) < 0) {
                        speedY = 0;
                    } else {
                        speedY = -speed;
                    }
                }
            }
        }
    }

    public void playersPositions(){
        for(int x=0; x<playersList.size();x++){
            if(this.id == playersList.get(x).get(0)){
                playersList.get(x).set(1,this.x);
                playersList.get(x).set(2,this.y);
            }
        }
    }

    public double calculateDistance(double ax, double ay, double bx, double by){
        return sqrt(Math.pow((ax-bx),2)+Math.pow((ay-by),2));
    }

    @Override
    public void update(double time, GameBoard b) {
        playersPositions();
        if(cbRandom){
            if(cpt==0){
                this.random(b);
                cpt=50;
            }
            cpt--;
        }else if(cbEscape){
            this.escape(b);
        }
        else if(cbFollow){
            this.follow(b);
        }else{
            this.processInput(b);
        }
        super.update(time, b);
    }
    @Override
    public Shape getBoundingShape() {
        return new Circle((x+x+diameter)/2, (y+y+diameter)/2, diameter/2.);
    }

    @Override
    public void render(GraphicsContext gc) {
        Paint save = gc.getFill();
        gc.setFill(colors[currentColor]);
        gc.strokeOval(x, y, diameter, diameter);
        gc.fillOval(x, y, diameter, diameter);
        gc.setFill(save);
    }

    @Override
    public void handleCollision(GameBoard b, MapElement p) {
        //System.out.println("Player.handleCollision()");
    }

}
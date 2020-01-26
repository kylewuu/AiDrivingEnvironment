package driver.game.entities.cars;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import driver.game.Game;
import driver.game.calculations.Movement;
import driver.game.drawings.Assets;


public class Player extends Cars {

    private BufferedImage car;
    private Game game;

    // temporary calculations that should be moved into its own class
    private double velocity;
    private double acceleration;
    private double deceleration;

    private Movement movement;
    


    public Player(Game game, float x, float y) {
        super(x, y);
        this.game = game;

        // tempoary inits that need to be moved into another class
        velocity = 0;
        acceleration = 0.3;
        deceleration = 0.7;

        movement = new Movement(velocity, acceleration, deceleration);


       }

    @Override
    public void tick() {
        // temporarily keeping the car straight... will need to add in different cars
        // these calcs are all temporary and should be moved
        car = Assets.carStraightNo;
        if(game.getKeyManager().accelerate){
            movement.accelerate();
        }
        if(game.getKeyManager().decelerate){
            car = Assets.carStraightBrake;
            movement.decelerate();
        }
        else if(!game.getKeyManager().accelerate && !game.getKeyManager().decelerate){
            movement.slowDown();
        }

        x += movement.tick(12);
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(car, (int) x, (int) y, null);
        // g.setColor(new Color(89, 18, 82));
        // g.fillRect((int) x,(int) y, 30, 52); // put in actual image of the car!
        // g.setColor(new Color(166, 166, 166));
    }
    
}
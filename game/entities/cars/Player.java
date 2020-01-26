package driver.game.entities.cars;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import driver.game.Game;
import driver.game.drawings.ImageLoader;

public class Player extends Cars {
    private BufferedImage carStraightNo;
    private BufferedImage carStraightBrake;
    private BufferedImage car;

    private Game game;

    public Player(Game game, float x, float y) {
        super(x, y);
        this.game = game;
        carStraightNo = ImageLoader.loadImage("/driver/res/textures/carStraightNoBrake.png");
        carStraightBrake = ImageLoader.loadImage("/driver/res/textures/carStraightBrake.png");
    }

    @Override
    public void tick() {
        // temporarily keeping the car straight... will need to add in different cars
        car = carStraightNo;
        if(game.getKeyManager().accelerate){
            y -= 10;
        }
        if(game.getKeyManager().decelerate){
            y += 10;
        }
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(car, (int) x, (int) y, null);
        // g.setColor(new Color(89, 18, 82));
        // g.fillRect((int) x,(int) y, 30, 52); // put in actual image of the car!
        // g.setColor(new Color(166, 166, 166));
    }
    
}
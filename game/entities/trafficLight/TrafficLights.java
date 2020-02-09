package driver.game.entities.trafficLight;

import java.awt.Graphics;

import driver.game.Game;
import driver.game.drawings.Assets;
import driver.game.entities.Entity;

public class TrafficLights extends Entity {
    private double x, y;

    private Game game;

    public TrafficLights(Game game, float x, float y) {
        super(x,y);
        this.game = game;
        this.x = x;
        this.y = y;
    }

    @Override
    public void tick() {
        // make timer to change lights
        
    }

    public void render(Graphics g){
        g.drawImage(Assets.trafficLight, (int) x, (int) y, null);
    }





}
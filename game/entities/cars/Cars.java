package driver.game.entities.cars;

import java.awt.Graphics;

import driver.game.entities.Entity;

public abstract class Cars extends Entity {

    protected int speed;

    public Cars(float x, float y) {
        super(x,y);
        speed = 0;
    }


}
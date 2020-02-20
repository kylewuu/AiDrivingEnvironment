package driver.game.entities.cars;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import driver.game.drawings.Assets;
import driver.game.drawings.Environment;
import driver.game.Launcher;


public class CPU{
    public double x, y;
    public double velocity;
    public BufferedImage carState;
    public double deceleration, acceleration;

    public String road; // which road the car travels
    public BufferedImage brake, noBrake;
    private int topSpeed;
    public int base;


    public CPU(String road){

        deceleration = 0.4;
        acceleration = 0.3;

        topSpeed = 50;
        velocity = topSpeed;
        base = 12;

        brake = Assets.carStraightBrake;
        noBrake = Assets.carStraightNo;
        this.road = road;
        
        switch(road){
            case "side":
                brake = Assets.rotate(brake, 180);
                noBrake = Assets.rotate(noBrake, 180);
                x = Launcher.width + 20;
                y = Launcher.height/2 - Environment.roadWidth/2;

                break;
            case "topUp":
                brake = Assets.rotate(brake, 90);
                noBrake = Assets.rotate(noBrake, 90);
                x = Launcher.width/2 - 35;
                y = -Launcher.width + 200;

                break;
            case "topDown":
                brake = Assets.rotate(brake, 270);
                noBrake = Assets.rotate(noBrake, 270);
                x = Launcher.width/2 + Environment.roadWidth/2 - 35;
                y = Launcher.width - 200;

                break;
        }
        carState = noBrake;

    }

    public void decelerate(){
        if(velocity > deceleration){
            velocity -= deceleration;
        }
        else velocity = 0.00000;
    }
    public void accelerate(){
        if(velocity < topSpeed){
            velocity += acceleration;
        }

        else velocity = topSpeed;

    }

    public void tick(){
        if(road == "side"){
            x -= velocity/base;
        }

        if(road == "topUp"){
            y += velocity/base;
        }

        if(road == "topDown"){
            y -= velocity/base;
        }
    }

    public void render(Graphics g) {
        g.drawImage(carState, (int) x, (int) y, null);

    }
}
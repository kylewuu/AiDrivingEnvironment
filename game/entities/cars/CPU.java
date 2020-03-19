package driver.game.entities.cars;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import driver.game.drawings.Assets;
import driver.game.drawings.Environment;
import driver.game.Launcher;


public class CPU{
    public double x, y;
    public double x1, y1;
    public double velocity;
    public BufferedImage carState;
    public double deceleration, acceleration;

    public String road; // which road the car travels
    public BufferedImage brake, noBrake;
    private int topSpeed;
    public int base;


    public CPU(String road){

        deceleration = 0.4;
        acceleration = 0.3 + (Math.random() * 0.3);

        topSpeed = 50 + ((int)(Math.random() * (5)));
        velocity = topSpeed;
        base = 12;

        int color = (int)(Math.random() * 5);

        brake = Assets.cpuColorArrayNo[color];
        noBrake = Assets.cpuColorArrayBrake[color];
        this.road = road;
        
        switch(road){
            case "side":
                brake = Assets.rotate(brake, 270);
                noBrake = Assets.rotate(noBrake, 270);
                x = Launcher.width + 25;
                y = Launcher.height/2 - Environment.roadWidth/2 + 18;
                x1 = x + 41;
                y1 = y + 23;

                break;
            case "topUp":
                brake = Assets.rotate(brake, 180);
                noBrake = Assets.rotate(noBrake, 180);
                x = Launcher.width/2 - 40;
                y = -200;

                x1 = x + 23;
                y1 = y + 41;

                break;
            case "topDown":
                brake = Assets.rotate(brake, 0);
                noBrake = Assets.rotate(noBrake, 0);
                x = Launcher.width/2 + Environment.roadWidth/2 - 45;
                y = Launcher.width - 200;

                x1 = x + 23;
                y1 = y + 41;

                break;

            case "side1":
                brake = Assets.rotate(brake, 270);
                noBrake = Assets.rotate(noBrake, 270);
                x = Launcher.width + 25;
                y = Launcher.height/2 - Environment.roadWidth/2 - 32;

                x1 = x + 41;
                y1 = y + 23;

                break;
            case "topUp1":
                brake = Assets.rotate(brake, 180);
                noBrake = Assets.rotate(noBrake, 180);
                x = Launcher.width/2 - 92;
                y = -200;

                x1 = x + 23;
                y1 = y + 41;

                break;
            case "topDown1":
                brake = Assets.rotate(brake, 0);
                noBrake = Assets.rotate(noBrake, 0);
                x = Launcher.width/2 + Environment.roadWidth/2 + 6;
                y = Launcher.width - 200;

                x1 = x + 23;
                y1 = y + 41;

                break;

            case "sideLeft":
                brake = Assets.rotate(brake, 90);
                noBrake = Assets.rotate(noBrake, 90);
                x = -105;
                y = Launcher.height/2 - Environment.roadWidth/2 + 126;

                x1 = x + 41;
                y1 = y + 23;

                break;
        }
        carState = noBrake;

    }

    public void decelerate(){
        carState = brake;
        if(velocity > deceleration){
            velocity -= deceleration;
        }
        else velocity = 0.00000;
    }
    public void accelerate(){
        carState = noBrake;
        if(velocity < topSpeed){
            velocity += acceleration;
        }

        else velocity = topSpeed;

    }

    public void tick(){
        if(road == "side" || road == "side1"){
            x -= velocity/base;
            x1 -= velocity/base;
        }
        if(road == "sideLeft"){
            x += velocity/base;
            x1 += velocity/base;
        }

        if(road == "topUp" || road == "topUp1"){
            y += velocity/base;
            y1 += velocity/base;
        }

        if(road == "topDown" || road == "topDown1"){
            y -= velocity/base;
            y1 -= velocity/base;
        }


    }

    public void render(Graphics g) {
        g.drawImage(carState, (int) x, (int) y, null);
        // g.drawRect((int)x1,(int) y1, 10, 10);

    }
}
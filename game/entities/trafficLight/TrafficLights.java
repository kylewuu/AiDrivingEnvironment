package driver.game.entities.trafficLight;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.EventSetDescriptor;

import driver.game.Game;
import driver.game.drawings.Assets;
import driver.game.drawings.Environment;
import driver.game.entities.Entity;

public class TrafficLights extends Entity {
    private double x, y;

    private Game game;

    private BufferedImage light11, light12, light21, light22;
    private BufferedImage yellowLight, redLight, greenLight;
    private int lightCount11, lightCount12, lightCount21, lightCount22;

    private int greenLength, redLength, yellowLength;
    private int redDelay;

    private int lightSpacing;

    public TrafficLights(Game game, float x, float y) {
        super(x,y);
        this.game = game;
        this.x = x;
        this.y = y;
        yellowLight = Assets.trafficLightYellow;
        redLight = Assets.trafficLightRed;
        greenLight = Assets.trafficLightGreen;

        redDelay = 120;

        yellowLength = 2 * 60;
        greenLength = 5 * 60; // 60 frames per second
        redLength = greenLength + yellowLength + redDelay + redDelay; // 60 second delay between lights changing

        light11 = greenLight;
        light12 = redLight;
        light21 = redLight;
        light22 = greenLight;

        lightCount12 = redDelay;
        lightCount21 = redDelay;

        lightSpacing = Environment.roadWidth/2;

    }

    @Override
    public void tick() {
        // make timer to change lights
        lightsChange();
        
    }
    private void lightsChange(){
        // light11
        if(light11 == greenLight){
            if(lightCount11 <= greenLength){
                lightCount11 ++;
            }
            else{
                lightCount11 = 0;
                light11 = yellowLight;
            }
        }

        if(light11 == yellowLight){
            if(lightCount11 < yellowLength){
                lightCount11++;
            }
            else{
                lightCount11 = 0;
                light11 = redLight;
            }
        }

        if(light11 == redLight){
            if(lightCount11 < redLength){
                lightCount11++;
            }
            else{
                lightCount11 = 0;
                light11 = greenLight;
            }
        }

        // light12
        if(light12 == greenLight){
            if(lightCount12 <= greenLength){
                lightCount12 ++;
            }
            else{
                lightCount12 = 0;
                light12 = yellowLight;
            }
        }

        if(light12 == yellowLight){
            if(lightCount12 < yellowLength){
                lightCount12++;
            }
            else{
                lightCount12 = 0;
                light12 = redLight;
            }
        }

        if(light12 == redLight){
            if(lightCount12 < redLength){
                lightCount12 ++;
            }
            else{
                lightCount12 = 0;
                light12 = greenLight;
            }
        }

        // light21
        if(light21 == greenLight){
            if(lightCount21 <= greenLength){
                lightCount21 ++;
            }
            else{
                lightCount21 = 0;
                light21 = yellowLight;
            }
        }

        if(light21 == yellowLight){
            if(lightCount21 < yellowLength){
                lightCount21++;
            }
            else{
                lightCount21 = 0;
                light21 = redLight;
            }
        }

        if(light21 == redLight){
            if(lightCount21 < redLength){
                lightCount21++;
            }
            else{
                lightCount21 = 0;
                light21 = greenLight;
            }
        }

        // light22
        if(light22 == greenLight){
            if(lightCount22 <= greenLength){
                lightCount22 ++;
            }
            else{
                lightCount22 = 0;
                light22 = yellowLight;
            }
        }

        if(light22 == yellowLight){
            if(lightCount22 < yellowLength){
                lightCount22++;
            }
            else{
                lightCount22 = 0;
                light22 = redLight;
            }
        }

        if(light22 == redLight){
            if(lightCount22 < redLength){
                lightCount22++;
            }
            else{
                lightCount22 = 0;
                light22 = greenLight;
            }
        }

        
    }

    public void render(Graphics g){
        g.drawImage(Assets.rotate(light11, 180), (int) x - lightSpacing - 15, (int) y - lightSpacing, null);
        g.drawImage(Assets.rotate(light12, 270), (int) x + lightSpacing - 50, (int) y - lightSpacing - 15, null);
        g.drawImage(Assets.rotate(light21, 90), (int) x - lightSpacing, (int) y + lightSpacing, null);
        g.drawImage(light22, (int) x + lightSpacing, (int) y + 5, null);
    }





}
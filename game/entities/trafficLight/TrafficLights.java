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
    private int[] stateArray;

    public TrafficLights(Game game, float x, float y) {
        super(x, y);
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

        double randomLightState = Math.round(Math.random());
        light11 = randomLightState == 1 ? greenLight : redLight;
        light12 = randomLightState == 1 ? redLight : greenLight;
        ;
        light21 = randomLightState == 1 ? redLight : greenLight;
        ;
        light22 = randomLightState == 1 ? greenLight : redLight;
        ;

        stateArray = new int[] { randomLightState == 1 ? 1 : 0, randomLightState == 1 ? 0 : 1,
                randomLightState == 1 ? 0 : 1, randomLightState == 1 ? 1 : 0 };

        if (light11 == redLight)
            lightCount11 = redDelay;
        if (light12 == redLight)
            lightCount12 = redDelay;
        if (light21 == redLight)
            lightCount21 = redDelay;
        if (light22 == redLight)
            lightCount22 = redDelay;

        lightSpacing = Environment.roadWidth / 2;
    }

    public int[] stateGetter() {
        return stateArray;
    }

    @Override
    public void tick() {
        // make timer to change lights
        lightsChange();
    }

    private void lightsChange() {
        // light11
        if (light11 == greenLight) {
            if (lightCount11 <= greenLength) {
                lightCount11++;
            } else {
                lightCount11 = 0;
                light11 = yellowLight;
                stateArray[0] = 0;
            }
        }

        if (light11 == yellowLight) {
            if (lightCount11 < yellowLength) {
                lightCount11++;
            } else {
                lightCount11 = 0;
                light11 = redLight;
                stateArray[0] = 0;
            }
        }

        if (light11 == redLight) {
            if (lightCount11 < redLength) {
                lightCount11++;
            } else {
                lightCount11 = 0;
                light11 = greenLight;
                stateArray[0] = 1;
            }
        }

        // light12
        if (light12 == greenLight) {
            if (lightCount12 <= greenLength) {
                lightCount12++;
            } else {
                lightCount12 = 0;
                light12 = yellowLight;
                stateArray[1] = 0;
            }
        }

        if (light12 == yellowLight) {
            if (lightCount12 < yellowLength) {
                lightCount12++;
            } else {
                lightCount12 = 0;
                light12 = redLight;
                stateArray[1] = 0;
            }
        }

        if (light12 == redLight) {
            if (lightCount12 < redLength) {
                lightCount12++;
            } else {
                lightCount12 = 0;
                light12 = greenLight;
                stateArray[1] = 1;
            }
        }

        // light21
        if (light21 == greenLight) {
            if (lightCount21 <= greenLength) {
                lightCount21++;
            } else {
                lightCount21 = 0;
                light21 = yellowLight;
                stateArray[2] = 0;
            }
        }

        if (light21 == yellowLight) {
            if (lightCount21 < yellowLength) {
                lightCount21++;
            } else {
                lightCount21 = 0;
                light21 = redLight;
                stateArray[2] = 0;
            }
        }

        if (light21 == redLight) {
            if (lightCount21 < redLength) {
                lightCount21++;
            } else {
                lightCount21 = 0;
                light21 = greenLight;
                stateArray[2] = 1;
            }
        }

        // light22
        if (light22 == greenLight) {
            if (lightCount22 <= greenLength) {
                lightCount22++;
            } else {
                lightCount22 = 0;
                light22 = yellowLight;
                stateArray[3] = 0;
            }
        }

        if (light22 == yellowLight) {
            if (lightCount22 < yellowLength) {
                lightCount22++;
            } else {
                lightCount22 = 0;
                light22 = redLight;
                stateArray[3] = 0;
            }
        }

        if (light22 == redLight) {
            if (lightCount22 < redLength) {
                lightCount22++;
            } else {
                lightCount22 = 0;
                light22 = greenLight;
                stateArray[3] = 1;
            }
        }
    }

    public void render(Graphics g) {
        g.drawImage(Assets.rotate(light11, 180), (int) x - lightSpacing - 85, (int) y - lightSpacing + 180, null);
        g.drawImage(Assets.rotate(light12, 270), (int) x + lightSpacing - 202, (int) y - lightSpacing - 80, null);
        g.drawImage(Assets.rotate(light21, 90), (int) x - lightSpacing + 180, (int) y + lightSpacing - 40, null);
        g.drawImage(light22, (int) x + lightSpacing - 40, (int) y - 140, null);
    }
}
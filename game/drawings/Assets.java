package driver.game.drawings;

import driver.game.drawings.ImageLoader;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public class Assets{
    // cars
    public static BufferedImage carStraightNo;
    public static BufferedImage carStraightBrake;

    public static void init(){
        carStraightNo = ImageLoader.loadImage("/driver/res/textures/carStraightNoBrake.png");
        carStraightBrake = ImageLoader.loadImage("/driver/res/textures/carStraightBrake.png");
    }

}
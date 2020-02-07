package driver.game.drawings;

import driver.game.drawings.ImageLoader;
import java.awt.image.BufferedImage;


public class Assets{
    // cars
    public static BufferedImage carStraightNo;
    public static BufferedImage carStraightBrake;
    public static BufferedImage carLeftNo;
    public static BufferedImage carLeftBrake;
    public static BufferedImage carRightNo;
    public static BufferedImage carRightBrake;

    public static void init(){
        carStraightNo = ImageLoader.loadImage("/driver/res/textures/carStraightNoBrake.png");
        carStraightBrake = ImageLoader.loadImage("/driver/res/textures/carStraightBrake.png");
        carLeftNo = ImageLoader.loadImage("/driver/res/textures/carLeftNo.png");
        carLeftBrake = ImageLoader.loadImage("/driver/res/textures/carLeftBrake.png");
        carRightNo = ImageLoader.loadImage("/driver/res/textures/carRightNo.png");
        carRightBrake = ImageLoader.loadImage("/driver/res/textures/carRightBrake.png");
        
    }

}
package driver.game.drawings;

import driver.game.drawings.ImageLoader;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;


public class Assets{
    // cars
    public static BufferedImage carStraightNo;
    public static BufferedImage carStraightBrake;
    public static BufferedImage carLeftNo;
    public static BufferedImage carLeftBrake;
    public static BufferedImage carRightNo;
    public static BufferedImage carRightBrake;
    public static BufferedImage trafficLight;
    public static BufferedImage trafficLightRed;
    public static BufferedImage trafficLightGreen;
    public static BufferedImage trafficLightYellow;
    public static int trafficLightWidth;

    public static void init(){
        carStraightNo = ImageLoader.loadImage("/driver/res/textures/carStraightNoBrake.png");
        carStraightBrake = ImageLoader.loadImage("/driver/res/textures/carStraightBrake.png");
        carLeftNo = ImageLoader.loadImage("/driver/res/textures/carLeftNo.png");
        carLeftBrake = ImageLoader.loadImage("/driver/res/textures/carLeftBrake.png");
        carRightNo = ImageLoader.loadImage("/driver/res/textures/carRightNo.png");
        carRightBrake = ImageLoader.loadImage("/driver/res/textures/carRightBrake.png");

        // rotating
        trafficLight = ImageLoader.loadImage("/driver/res/textures/trafficLightAll.png");
        trafficLightRed = ImageLoader.loadImage("/driver/res/textures/trafficLightRed.png");
        trafficLightGreen = ImageLoader.loadImage("/driver/res/textures/trafficLightGreen.png");
        trafficLightYellow = ImageLoader.loadImage("/driver/res/textures/trafficLightYellow.png");

        trafficLightWidth = trafficLightRed.getWidth(); // uselss??
    }

    public static BufferedImage rotate(BufferedImage image, double degrees) {
        final double rads = Math.toRadians(degrees);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads,0, 0);
        at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(image,rotatedImage);
        return rotatedImage;
    }

}
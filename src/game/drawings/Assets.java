package game.drawings;

import game.drawings.ImageLoader;
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
    public static BufferedImage environment;
    
    public static int trafficLightWidth,carLength, carWidth;

    public static BufferedImage 
    carStraightNoWhite,
    carStraightBrakeWhite,
    carStraightNoRed,
    carStraightBrakeRed,
    carStraightNoPurple,
    carStraightBrakePurple,
    carStraightNoGreen,
    carStraightBrakeGreen,
    carStraightNoYellow,
    carStraightBrakeYellow
    ;

    public static BufferedImage[] cpuColorArrayNo, cpuColorArrayBrake;

    public static void init(){
        carStraightNo = ImageLoader.loadImage("/res/textures/carStraightNoBrake.png");
        carStraightBrake = ImageLoader.loadImage("/res/textures/carStraightBrake.png");
        carLeftNo = ImageLoader.loadImage("/res/textures/carLeftNo.png");
        carLeftBrake = ImageLoader.loadImage("/res/textures/carLeftBrake.png");
        carRightNo = ImageLoader.loadImage("/res/textures/carRightNo.png");
        carRightBrake = ImageLoader.loadImage("/res/textures/carRightBrake.png");

        // cpu loading
        carStraightNoWhite = ImageLoader.loadImage("/res/textures/cpu/carStraightNoWhite.png");
        carStraightBrakeWhite = ImageLoader.loadImage("/res/textures/cpu/carStraightBrakeWhite.png");

        carStraightNoRed = ImageLoader.loadImage("/res/textures/cpu/carNoRed.png");
        carStraightBrakeRed = ImageLoader.loadImage("/res/textures/cpu/carBrakeRed.png");

        carStraightNoPurple = ImageLoader.loadImage("/res/textures/cpu/carNoPurple.png");
        carStraightBrakePurple = ImageLoader.loadImage("/res/textures/cpu/carBrakePurple.png");

        carStraightNoGreen = ImageLoader.loadImage("/res/textures/cpu/carNoGreen.png");
        carStraightBrakeGreen = ImageLoader.loadImage("/res/textures/cpu/carBrakeGreen.png");

        carStraightNoYellow = ImageLoader.loadImage("/res/textures/cpu/carNoYellow.png");
        carStraightBrakeYellow = ImageLoader.loadImage("/res/textures/cpu/carBrakeYellow.png");

        // rotating
        cpuColorArrayNo = new BufferedImage[]{
            carStraightNoWhite,
            carStraightNoRed,
            carStraightNoPurple,
            carStraightNoGreen,
            carStraightNoYellow
        };
        cpuColorArrayBrake = new BufferedImage[]{
            carStraightBrakeWhite,
            carStraightBrakeRed,
            carStraightBrakePurple,
            carStraightBrakeGreen,
            carStraightBrakeYellow
        };

        // trafficLight = ImageLoader.loadImage("/res/textures/trafficLightAll.png");
        trafficLightRed = ImageLoader.loadImage("/res/textures/trafficLightRed.png");
        trafficLightGreen = ImageLoader.loadImage("/res/textures/trafficLightGreen.png");
        trafficLightYellow = ImageLoader.loadImage("/res/textures/trafficLightYellow.png");

        carLength = carStraightNo.getWidth();
        carWidth = carStraightNo.getHeight();
        trafficLightWidth = trafficLightRed.getWidth(); // uselss??

        environment = ImageLoader.loadImage("/res/textures/environment.png");
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
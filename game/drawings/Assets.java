package driver.game.drawings;

import driver.game.drawings.ImageLoader;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

public class Assets {
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
    public static BufferedImage cloud, cloud1, cloud2, cloud3;
    public static BufferedImage n0, n1, n2, n3, n4, n5, n6, n7, n8, n9;
    public static BufferedImage start, freePlay, display, increment, decrement, checked, unchecked, pointsDisplay,
            iterationDisplay;

    public static int trafficLightWidth, carLength, carWidth;

    public static BufferedImage carStraightNoWhite, carStraightBrakeWhite, carStraightNoRed, carStraightBrakeRed,
            carStraightNoPurple, carStraightBrakePurple, carStraightNoGreen, carStraightBrakeGreen, carStraightNoYellow,
            carStraightBrakeYellow;

    public static BufferedImage[] cpuColorArrayNo, cpuColorArrayBrake, cloudArray, numbersArray;

    public static void init() {
        carStraightNo = ImageLoader.loadImage("/driver/res/textures/carStraightNoBrake.png");
        carStraightBrake = ImageLoader.loadImage("/driver/res/textures/carStraightBrake.png");
        carLeftNo = ImageLoader.loadImage("/driver/res/textures/carLeftNo.png");
        carLeftBrake = ImageLoader.loadImage("/driver/res/textures/carLeftBrake.png");
        carRightNo = ImageLoader.loadImage("/driver/res/textures/carRightNo.png");
        carRightBrake = ImageLoader.loadImage("/driver/res/textures/carRightBrake.png");

        // cpu loading
        carStraightNoWhite = ImageLoader.loadImage("/driver/res/textures/cpu/carStraightNoWhite.png");
        carStraightBrakeWhite = ImageLoader.loadImage("/driver/res/textures/cpu/carStraightBrakeWhite.png");

        carStraightNoRed = ImageLoader.loadImage("/driver/res/textures/cpu/carNoRed.png");
        carStraightBrakeRed = ImageLoader.loadImage("/driver/res/textures/cpu/carBrakeRed.png");

        carStraightNoPurple = ImageLoader.loadImage("/driver/res/textures/cpu/carNoPurple.png");
        carStraightBrakePurple = ImageLoader.loadImage("/driver/res/textures/cpu/carBrakePurple.png");

        carStraightNoGreen = ImageLoader.loadImage("/driver/res/textures/cpu/carNoGreen.png");
        carStraightBrakeGreen = ImageLoader.loadImage("/driver/res/textures/cpu/carBrakeGreen.png");

        carStraightNoYellow = ImageLoader.loadImage("/driver/res/textures/cpu/carNoYellow.png");
        carStraightBrakeYellow = ImageLoader.loadImage("/driver/res/textures/cpu/carBrakeYellow.png");

        // rotating
        cpuColorArrayNo = new BufferedImage[] { carStraightNoWhite, carStraightNoRed, carStraightNoPurple,
                carStraightNoGreen, carStraightNoYellow };
        cpuColorArrayBrake = new BufferedImage[] { carStraightBrakeWhite, carStraightBrakeRed, carStraightBrakePurple,
                carStraightBrakeGreen, carStraightBrakeYellow };

        trafficLightRed = ImageLoader.loadImage("/driver/res/textures/trafficLightRed.png");
        trafficLightGreen = ImageLoader.loadImage("/driver/res/textures/trafficLightGreen.png");
        trafficLightYellow = ImageLoader.loadImage("/driver/res/textures/trafficLightYellow.png");

        carLength = carStraightNo.getWidth();
        carWidth = carStraightNo.getHeight();
        trafficLightWidth = trafficLightRed.getWidth();

        // clouds
        cloud = ImageLoader.loadImage("/driver/res/textures/cloud.png");
        cloud1 = ImageLoader.loadImage("/driver/res/textures/cloud1.png");
        cloud2 = ImageLoader.loadImage("/driver/res/textures/cloud2.png");
        cloud3 = ImageLoader.loadImage("/driver/res/textures/cloud3.png");
        cloudArray = new BufferedImage[] { cloud, cloud1, cloud2, cloud3 };

        environment = ImageLoader.loadImage("/driver/res/textures/environment.png");

        // number/points
        n0 = ImageLoader.loadImage("/driver/res/textures/numbers/0.png");
        n1 = ImageLoader.loadImage("/driver/res/textures/numbers/1.png");
        n2 = ImageLoader.loadImage("/driver/res/textures/numbers/2.png");
        n3 = ImageLoader.loadImage("/driver/res/textures/numbers/3.png");
        n4 = ImageLoader.loadImage("/driver/res/textures/numbers/4.png");
        n5 = ImageLoader.loadImage("/driver/res/textures/numbers/5.png");
        n6 = ImageLoader.loadImage("/driver/res/textures/numbers/6.png");
        n7 = ImageLoader.loadImage("/driver/res/textures/numbers/7.png");
        n8 = ImageLoader.loadImage("/driver/res/textures/numbers/8.png");
        n9 = ImageLoader.loadImage("/driver/res/textures/numbers/9.png");

        numbersArray = new BufferedImage[] { n0, n1, n2, n3, n4, n5, n6, n7, n8, n9 };

        start = ImageLoader.loadImage("/driver/res/textures/start.png");
        freePlay = ImageLoader.loadImage("/driver/res/textures/freePlay.png");
        display = ImageLoader.loadImage("/driver/res/textures/display.png");
        increment = ImageLoader.loadImage("/driver/res/textures/increment.png");
        decrement = ImageLoader.loadImage("/driver/res/textures/decrement.png");
        checked = ImageLoader.loadImage("/driver/res/textures/checked.png");
        unchecked = ImageLoader.loadImage("/driver/res/textures/unchecked.png");

        pointsDisplay = ImageLoader.loadImage("/driver/res/textures/pointsDisplay.png");
        iterationDisplay = ImageLoader.loadImage("/driver/res/textures/iterationDisplay.png");
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
        at.rotate(rads, 0, 0);
        at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(image, rotatedImage);
        return rotatedImage;
    }
}
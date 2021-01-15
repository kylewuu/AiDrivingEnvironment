package driver.game.drawings;

import java.awt.Graphics;
import java.awt.Color;

public class Environment {
    private static int width;
    private static int height;
    public static int roadWidth = 120;

    public static void init(int width, int height) {
        Environment.width = width;
        Environment.height = height;
    }

    public static void draw(Graphics g) {
        g.drawImage(Assets.environment, 0, 0, null);
    }

}
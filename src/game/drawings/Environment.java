package game.drawings;
import java.awt.Graphics;
import java.awt.Color;

public class Environment{
    private static int width;
    private static int height;
    public static int roadWidth = 120;
    
    public static void init(int width, int height){
        Environment.width = width;
        Environment.height = height;
    }

    public static void draw(Graphics g){
        // g.setColor(new Color(50, 168, 82));
        // g.fillRect(0, 0, width, height);
        // g.setColor(new Color(166, 166, 166));
        // g.fillRect(width/2 - roadWidth/2, 0, roadWidth, height);
        // g.fillRect(0,height/2 - roadWidth/2, width, roadWidth);

        g.drawImage(Assets.environment, 0, 0, null);
    }

    public static void test(){
        System.out.println(width);
    }

}
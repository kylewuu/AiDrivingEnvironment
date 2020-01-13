import java.awt.Graphics;
import java.awt.Color;

public class Environment{
    private int width;
    private int height;
    public int roadWidth = 300;

    public Environment(int width, int height, Graphics g){
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics g){
        g.setColor(new Color(50, 168, 82));
        g.fillRect(0, 0, width, height);
        g.setColor(new Color(166, 166, 166));
        g.fillRect(width/2 - roadWidth/2, 0, roadWidth, height);
    }
}
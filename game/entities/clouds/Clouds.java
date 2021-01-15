package driver.game.entities.clouds;

import java.awt.Graphics;
import driver.game.Launcher;
import driver.game.drawings.Assets;

import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;

public class Clouds {
    private int y, velocity;
    private BufferedImage cloud;
    public int x;
    public double opacity;

    public Clouds() {
        x = -300;
        y = (int) (Math.random() * (Launcher.height)) - 200;
        velocity = 1 + (int) (Math.random() * 2);
        opacity = 0.5 + Math.random() * (1 - 0.5);

        cloud = Assets.cloudArray[(int) (Math.random() * Assets.cloudArray.length)];
    }

    public void tick() {
        x += velocity;
    }

    public void render(Graphics g) {
        float alpha = (float) opacity;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(ac);
        g.drawImage(cloud, x, y, null);

        alpha = (float) 1.0;
        ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d = (Graphics2D) g;
        g2d.setComposite(ac);
    }
}
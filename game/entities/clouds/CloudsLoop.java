package driver.game.entities.clouds;

import java.awt.Graphics;
import driver.game.Launcher;
import driver.game.drawings.Assets;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class CloudsLoop{
    List<Clouds> cloudArray;

    private int spacerCount, cloudCountLimitter, roomBetweenClouds, spacerCountThreshold, cloudMax;

    public CloudsLoop(){
        cloudArray = new ArrayList<Clouds>();
        initNew();
        initNew();

        spacerCount = 0;
        cloudCountLimitter = 10;
        roomBetweenClouds = 300;
        spacerCountThreshold = roomBetweenClouds ;
        cloudMax = 3;

    }

    private void destroy(){
        for(int i=0;i<cloudArray.size();i++){
            Clouds temp = cloudArray.get(i);
            if(temp.x > Launcher.width + 100){
                cloudArray.remove(i);
                i--;
            }
        }
    }

    public void spawn(){
        if((int)(Math.random() * cloudCountLimitter) == 1 && spacerCount > spacerCountThreshold && cloudArray.size() < cloudMax){
            initNew();
            spacerCount = 0;
        }
        spacerCount++;
        for(int i = 0; i< cloudArray.size();i++){
            Clouds temp = cloudArray.get(i);
            temp.tick();
            cloudArray.set(i, temp);
        }
    }

    private void initNew(){
        cloudArray.add(new Clouds());
    }

    public void tick(){
        spawn();
    }


    public void render(Graphics g){
        destroy();
        // System.out.println("Render ticking...");
        // System.out.println(cloudArray.size());
        for(int i = 0; i< cloudArray.size();i++){
            Clouds temp = cloudArray.get(i);
            temp.render(g);
            // cloudArray.set(i, temp);
        }

    }


}
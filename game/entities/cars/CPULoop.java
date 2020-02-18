package driver.game.entities.cars;

import driver.game.Launcher;
import driver.game.entities.cars.CPU;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;


public class CPULoop{
    private String[] directionArray;
    List<CPU> carArray;
    private int spacerCount, spacerCountThreshold;
    private int carCountLimitter; // smaller the fewer cars

    public CPULoop(){
        carArray = new ArrayList<CPU>();
        directionArray = new String[]{"side", "topUp", "topDown"};
        initNew();
        spacerCount = 0;
        spacerCountThreshold = 20;
        carCountLimitter = 10;
    }

    public void initNew(){
        carArray.add(new CPU(directionArray[(int)(Math.random() * 3)]));
    }

    public void destroy(){
        for(int i = 0; i < carArray.size();i++){
            CPU temp = carArray.get(i);
            switch(temp.road){
                case "side":
                    if(temp.x < -30){
                        carArray.remove(i);
                        i--;
                    }
                    break;
                case "topUp":
                    if(temp.y > Launcher.height + 30){
                        carArray.remove(i);
                        i--;
                    }
                    break;
                case "topDown":
                    if(temp.y < - 30){
                        carArray.remove(i);
                        i--;
                    }
                    break;
            }
            
        }
    }

    public void tick(){
        if((int)(Math.random() * carCountLimitter) == 1 && spacerCount > spacerCountThreshold){
            initNew();
            spacerCount = 0;
        }
        spacerCount++;
        // pass in traffic lights to interact with game
        // acceleration and deceleration on here
        for(int i = 0; i< carArray.size();i++){
            CPU temp = carArray.get(i);
            temp.tick();
            carArray.set(i, temp);
        }
    }

    public void render(Graphics g){
        destroy();
        for(int i = 0; i< carArray.size();i++){
            CPU temp = carArray.get(i);
            temp.render(g);
            carArray.set(i, temp);
        }
        System.out.println(carArray.size());

    }
}
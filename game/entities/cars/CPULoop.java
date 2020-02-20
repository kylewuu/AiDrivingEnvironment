package driver.game.entities.cars;

import driver.game.Launcher;
import driver.game.drawings.Environment;
import driver.game.entities.cars.CPU;
import driver.game.drawings.Assets;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CPULoop{
    private String[] directionArray;
    List<CPU> carArray;
    private double spacerCount, spacerCountThreshold;
    private int carCountLimitter; // smaller the fewer cars
    private double deceleration;
    private double roomBetweenCars;

    double velocity;
    double time;
    double distance;

    private int carMax;


    public CPULoop(){
        carArray = new ArrayList<CPU>();
        directionArray = new String[]{"side", "topUp", "topDown"};
        initNew();
        deceleration = carArray.get(0).deceleration;
        spacerCount = 0;
        carCountLimitter = 10;
        roomBetweenCars = 30;
        spacerCountThreshold = roomBetweenCars ;
        carMax = 8;
    }

    public void initNew(){
        carArray.add(new CPU(directionArray[(int)(Math.random() * 3)]));
        // carArray.add(new CPU(directionArray[2]));

    }

    public void speedControl(int[] lightStates){
        // System.out.println(Arrays.toString(lightStates));
        double topUpStopLine = Launcher.height/2 - Environment.roadWidth/2 - Assets.carLength;
        double sideStopLine = Launcher.width/2 + Environment.roadWidth/2;
        double topDownStopLine = Launcher.height/2 + Environment.roadWidth/2 ;

        if(lightStates[0] == 1) sideStopLine = 10000;
        if(lightStates[1] == 1){
            topDownStopLine = 10000;
            topUpStopLine = -10000;
        } 

        double velocity;
        double time;
        double distance;

        for(int i = 0; i < carArray.size();i++){
            CPU temp = carArray.get(i);
            velocity = temp.velocity/temp.base;
            switch(temp.road){
                case "side":
                    if(temp.x >= sideStopLine + roomBetweenCars){
                        // System.out.println("stopping, tempx: " + temp.x+" side + room: " + ( sideStopLine + roomBetweenCars));
                        time = velocity/(deceleration);
                        distance = time + ((deceleration*temp.base)/2)*(time * time);
                        
                        // System.out.println("velocity: "+velocity+" distance: " + distance + " time: "+ " full: "+ (temp.x - (sideStopLine + roomBetweenCars)));
                        if((distance >= temp.x - (sideStopLine + roomBetweenCars)) && temp.velocity > 0) temp.decelerate();
                        else if(temp.x - (sideStopLine + roomBetweenCars) > roomBetweenCars) temp.accelerate();

                        carArray.set(i, temp);
                        
                    }
                    else{
                        temp.accelerate();
                        carArray.set(i, temp);
                    }

                    if(temp.x <= Launcher.width/2 + Environment.roadWidth/2 ){
                        // System.out.println(sideStopLine + " : " + temp.velocity);

                        sideStopLine = Launcher.width/2 + Environment.roadWidth/2;
                        if(lightStates[0] == 1) sideStopLine = 10000;
                    }
                    else sideStopLine = temp.x + roomBetweenCars;

                    break;

                case "topDown":
                    if(temp.y >= topDownStopLine + roomBetweenCars){
                        // System.out.println("stopping, tempx: " + temp.x+" side + room: " + ( sideStopLine + roomBetweenCars));
                        time = velocity/(deceleration);
                        distance = time + ((deceleration*temp.base)/2)*(time * time);
                        
                        // System.out.println("velocity: "+velocity+" distance: " + distance + " time: "+ " full: "+ (temp.x - (sideStopLine + roomBetweenCars)));
                        if((distance >= temp.y - (topDownStopLine + roomBetweenCars)) && temp.velocity > 0) temp.decelerate();
                        else if(temp.y - (topDownStopLine + roomBetweenCars) > roomBetweenCars) temp.accelerate();

                        carArray.set(i, temp);
                        
                    }
                    else{
                        temp.accelerate();
                        carArray.set(i, temp);
                    }
                    if(temp.y <= Launcher.height/2 + Environment.roadWidth/2){
                        topDownStopLine = Launcher.height/2 + Environment.roadWidth/2;
                        if(lightStates[1] == 1) topDownStopLine = 10000;
                    }
                    else topDownStopLine = temp.y + roomBetweenCars;
                    // System.out.println(topDownStopLine);

                    break;
                case "topUp":
                
                    if(temp.y <= topUpStopLine - roomBetweenCars){
                        // System.out.println(temp.y);
                        // System.out.println("stopping, tempx: " + temp.x+" side + room: " + ( sideStopLine + roomBetweenCars));
                        time = velocity/(deceleration);
                        distance = time + ((deceleration*temp.base)/2)*(time * time);
                        
                        // System.out.println("velocity: "+velocity+" distance: " + distance + " time: "+ " full: "+ (temp.x - (sideStopLine + roomBetweenCars)));
                        if(distance >= Math.abs(temp.y - (topUpStopLine - roomBetweenCars)) && temp.velocity > 0) temp.decelerate();
                        else if(Math.abs(temp.y - (topUpStopLine - roomBetweenCars)) > roomBetweenCars) temp.accelerate();

                        carArray.set(i, temp);
                        
                    }
                    else{
                        temp.accelerate();
                        carArray.set(i, temp);
                    }
                    if(temp.y >= (Launcher.height/2 - Environment.roadWidth/2- Assets.carLength)){ // car is flipped so the starting x axis is higher
                        topUpStopLine = Launcher.height/2 - Environment.roadWidth/2 - Assets.carLength;
                        if(lightStates[1] == 1) topUpStopLine = -10000;
                    }
                    else topUpStopLine = temp.y - roomBetweenCars;
                    // System.out.println(topUpStopLine);

                    break;
            }

        }
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

    public void spawn(){
        if((int)(Math.random() * carCountLimitter) == 1 && spacerCount > spacerCountThreshold && carArray.size() < carMax){
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

    public void tick(){
        spawn();
    }

    public void render(Graphics g){
        destroy();
        for(int i = 0; i< carArray.size();i++){
            CPU temp = carArray.get(i);
            temp.render(g);
            carArray.set(i, temp);
        }
        // System.out.println(carArray.size());

    }
}
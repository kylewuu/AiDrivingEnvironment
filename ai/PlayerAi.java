package driver.ai;

import java.util.Random;
import java.util.Arrays;

import driver.game.Launcher;

public class PlayerAi{
    // environment -----
    double 
    sideBottom,
    sideTop,
    sideLeft,
    sideRight,

    topLeft,
    topRight,
    topTop,
    topBottom,

    middleLeft,
    middleTop,
    middleRight,
    middleBottom;

    double[][] environmentTrainData, environmentTrainResult;

    double[][] stopLightTrainData;
    double[][] stopLightTrainResult;

    private Train environmentAi;

    private Train stopLightAi;

    // empty constructor
    public PlayerAi(double[][] thresholds){
        sideTop = thresholds[0][0]/Launcher.height;
        sideRight = thresholds[0][1]/Launcher.width;
        sideBottom = thresholds[0][2]/Launcher.height;
        sideLeft = thresholds[0][3]/Launcher.width;

        middleTop = thresholds[1][0]/Launcher.height;
        middleRight = thresholds[1][1]/Launcher.width;
        middleBottom = thresholds[1][2]/Launcher.height;
        middleLeft = thresholds[1][3]/Launcher.width;
        
        topTop = thresholds[2][0]/Launcher.height;
        topRight = thresholds[2][1]/Launcher.width;
        topBottom = thresholds[2][2]/Launcher.height;
        topLeft = thresholds[2][3]/Launcher.width;

        environmentAi = new Train();
        stopLightAi = new Train();
        randomizeEnvironment();
        randomizeStopLine();
        environmentAi.train(environmentTrainData, environmentTrainResult, 2, 4, 3);
        stopLightAi.train(stopLightTrainData, stopLightTrainResult, 2, 3, 1);

    }
    private void randomizeEnvironment(){
        // Random ran = new Random();
        int numPerSide = 100;
        environmentTrainData = new double[numPerSide*3][2];
        environmentTrainResult = new double[numPerSide*3][2];

        double[][] height= {
            {sideTop, sideBottom},
            {middleTop, middleBottom},
            {topTop, topBottom}
        };
        double[][] width= {
            {sideLeft, sideRight},
            {middleLeft, middleRight},
            {topLeft, topRight}
        };
        for(int side=0; side< 3; side++){
            for(int i=0; i< numPerSide; i++){
                environmentTrainData[i + side*numPerSide][0] = width[side][0] + (((width[side][1] - width[side][0])/numPerSide) * i);
                environmentTrainData[i + side*numPerSide][1] = height[side][0] + (((height[side][1] - height[side][0])/numPerSide) * i);

                environmentTrainResult[i + side*numPerSide] = new double[] {
                    (side == 0)?1:0,
                    (side == 1)?1:0,
                    (side == 2)?1:0,
                };

            }

        }

    }

    private void randomizeStopLine(){
        // (traffic light, distance) 1==green, 1== within distance
        stopLightTrainData = new double[][] {
            {0,0},
            {0,1},
            {1,0},
            {1,1}
        };
        // 1 == stop, 0 == go
        stopLightTrainResult= new double[][] {
            {0},
            {1},
            {0},
            {0}
        };
        
    }

    public void tick(double x, double y){
        environmentTick(x, y);
        sideStateTick();
    }
    
    // main tick for ai loop
    private void environmentTick(double x, double y){
        x = x/Launcher.width;
        y = y/Launcher.height;
        double[] stateArray = environmentAi.run(new double[]{x, y});
        
        // side state
        if(Math.round(stateArray[0]) == 1){
            
        }
        // intersection
        else if(Math.round(stateArray[1]) == 1){

        }
        // upper street
        else if(Math.round(stateArray[2]) == 1){

        }
    }

    private void sideStateTick(double x, double velocity, int lightStateHorizontal){
        // acceleration
        if(x <= sideRight){
            // System.out.println("stopping, tempx: " + temp.x+" side + room: " + ( sideStopLine + roomBetweenCars));
            double time = velocity/(deceleration);
            distance = time + ((deceleration*temp.base)/2)*(time * time);
            
            // System.out.println("velocity: "+velocity+" distance: " + distance + " time: "+ " full: "+ (temp.x - (sideStopLine + roomBetweenCars)));
            if((distance >= Math.abs(temp.x - (sideLeftStopLine - roomBetweenCars))) && temp.velocity > 0) temp.decelerate();
            else if(Math.abs(temp.x - (sideLeftStopLine - roomBetweenCars)) > roomBetweenCars) temp.accelerate();

            carArray.set(i, temp);
            
        }
        else{
            temp.accelerate();
            carArray.set(i, temp);
        }

        if(temp.x >= sideLeftLightLine ){
            // System.out.println(sideStopLine + " : " + temp.velocity);

            sideLeftStopLine = sideLeftLightLine;
            if(lightStates[1] == 1) sideLeftStopLine = 10000;
        }
        else sideLeftStopLine = temp.x - roomBetweenCars;

        System.out.println(Arrays.toString(stopLightAi.run(new double[]{0,1})));

    }

}
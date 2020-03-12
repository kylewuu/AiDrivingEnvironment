package driver.ai;

import java.util.Random;
import java.util.Arrays;

import driver.game.Launcher;
import driver.game.entities.cars.Player;

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

    // public void tick(double x, double y, int[] trafficLightStates, double velocity, double deceleration, int base){
    public void tick(Player player, int[] trafficLightStates){
        // environmentTick(player);
        sideStateTick(player, trafficLightStates[1]);
    }
    
    // main tick for ai loop
    private void environmentTick(Player player){
        double x = player.xGetter()/Launcher.width;
        double y = player.yGetter()/Launcher.height;
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

    // private void sideStateTick(double x, double velocity, int lightStateHorizontal, double deceleration, int base){
    private void sideStateTick(Player player, int trafficLightState){

        // acceleration
        double trafficLine = sideRight*Launcher.width - 50;
        int distanceFlag, trafficLightStateFlag;
        double velocity = player.velocityGetter()/player.baseGetter();
        distanceFlag = 0;
        trafficLightStateFlag = trafficLightState;
        // System.out.println(trafficLightStateFlag);
        // System.out.println("stopping, tempx: " + temp.x+" side + room: " + ( sideStopLine + roomBetweenCars));

        double time = velocity/(player.decelerationGetter());
        double distance = time + ((player.decelerationGetter()*player.baseGetter())/2)*(time * time);

        if(distance >= Math.abs(player.xGetter() - trafficLine) && (player.velocityGetter() > 0)) distanceFlag = 1;
        if(distanceFlag == 1 && Math.abs(player.xGetter() - trafficLine)<0.07){
            player.xSetter(trafficLine);
        }

        // else if(Math.abs(player.xGetter() - trafficLine ) > roomBetweenCars) distanceFlag = 0;

        // System.out.println("PLAYER " +trafficLightStateFlag+" : " + distanceFlag);
        // System.out.println("PLAYER " +time+" : " + player.decelerationGetter() + " : "+ player.baseGetter());
        // System.out.println(distance+" : "+(player.xGetter()-trafficLine)+ " : "+(player.velocityGetter()));
        // System.out.println(((float)stopLightAi.run(new double[]{trafficLightStateFlag, distanceFlag})[0]) + "PLAYER " +trafficLightStateFlag+" : " + distanceFlag);
        // System.out.println(distanceFlag);
        // System.out.println(trafficLightStateFlag);
        if(Math.round((float)stopLightAi.run(new double[]{trafficLightStateFlag, distanceFlag})[0]) == 0) player.accelerate();
        else player.decelerate();

    }

}
package driver.ai;

import java.util.Random;
import java.util.Arrays;
import java.util.List;

import driver.game.entities.cars.CPU;

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
    int sideStateTurnThreshold;

    double[][] environmentTrainData, environmentTrainResult;

    double[][] stopLightSideTrainData, stopLightSideTrainResult;

    double[][] turnLineSideTrainData, turnLineSideTrainResult;

    double[][] stopMiddleTrainData, stopMiddleTrainResult;

    private Train environmentAi;
    private Train stopLightSideAi;
    private Train turnLineSideAi;
    private Train middleLineAi;

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

        sideStateTurnThreshold = 20;

        environmentAi = new Train();
        stopLightSideAi = new Train();
        turnLineSideAi = new Train();
        middleLineAi = new Train();
        
        randomizeEnvironment();
        randomizeSideStopLine();
        randomizeTurnLine();
        randomizeMiddleStopLine();

        environmentAi.train(environmentTrainData, environmentTrainResult, 2, 4, 3, 100);
        stopLightSideAi.train(stopLightSideTrainData, stopLightSideTrainResult, 2, 3, 1, 1000);
        turnLineSideAi.train(turnLineSideTrainData, turnLineSideTrainResult, 2, 3, 2, 1000);
        middleLineAi.train(stopMiddleTrainData, stopMiddleTrainResult, 3, 4, 1, 10000);


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

    private void randomizeSideStopLine(){
        // (traffic light, distance) 1==green, 1== within distance
        stopLightSideTrainData = new double[][] {
            {0,0},
            {0,1},
            {1,0},
            {1,1}
        };
        // 1 == stop, 0 == go
        stopLightSideTrainResult= new double[][] {
            {0},
            {1},
            {0},
            {0}
        };
        
    }

    private void randomizeTurnLine(){
        // (traffic light, distance) 1 = leftOver, 1 = rightOver
        turnLineSideTrainData = new double[][] {
            {0,0},
            {0,1},
            {1,0},
            {1,1}
        };
        // 1 = go
        turnLineSideTrainResult= new double[][] {
            {0,0},
            {0,1},
            {1,0},
            {0,0}
        };
        
    }

    private void randomizeMiddleStopLine(){
        // (traffic light, distance) 1==cars, 1== within distance, 1 == green
        stopMiddleTrainData = new double[][] {
            {1,0,0}, // car in the way, not within distance, their red
            {1,1,0}, // car, within d, their red
            {1,1,1}, // car, within d, their green
            {0,1,0}, // no car, within d, their red
            {0,1,1}, // no car, withint distance, their green
            {0,0,1}, // no car, not within d, their green
            {1,0,1}, // car, no within d, their green
            {0,0,0} // no car, not within d, their red
        };
        // 1 == stop, 0 == go
        stopMiddleTrainResult= new double[][] {
            {0},
            {1},
            {0},
            {0},
            {0},
            {0},
            {0},
            {0}
            // {1},
            // {1},
            // {1},
            // {1},
            // {1},
            // {1},
            // {1},
            // {1}
        };
    }



    // public void tick(double x, double y, int[] trafficLightStates, double velocity, double deceleration, int base){
    public void tick(Player player, int[] trafficLightStates, List<CPU> cpuList){
        environmentTick(player, trafficLightStates, cpuList);
    }
    
    // main tick for ai loop
    private void environmentTick(Player player, int[] trafficLightStates, List<CPU> cpuList){
        double x = player.xGetter()/Launcher.width;
        double y = player.yGetter()/Launcher.height;
        double[] stateArray = environmentAi.run(new double[]{x, y});
        
        // side state
        if(Math.round(stateArray[0]) == 1){
            sideStateTurnTick(player);
            sideStateStopTick(player, trafficLightStates[1]);
            middleStateStopTick(player, cpuList, trafficLightStates[0]);
        }
        // intersection
        else if(Math.round(stateArray[1]) == 1){
            // System.out.println("middle");
            middleStateStopTick(player, cpuList, trafficLightStates[0]);
        }
        // upper street
        else if(Math.round(stateArray[2]) == 1){

        }
    }

    // private void sideStateTick(double x, double velocity, int lightStateHorizontal, double deceleration, int base){
    private void sideStateStopTick(Player player, int trafficLightState){

        // acceleration
        double trafficLine = sideRight*Launcher.width - 60;
        int distanceFlag, trafficLightStateFlag;
        double velocity = player.velocityGetter()/player.baseGetter();
        distanceFlag = 0;
        trafficLightStateFlag = trafficLightState;
        // System.out.println(trafficLightStateFlag);
        // System.out.println("stopping, tempx: " + temp.x+" side + room: " + ( sideStopLine + roomBetweenCars));

        double time = velocity/(player.decelerationGetter());
        double distance = time + ((player.decelerationGetter()*player.baseGetter())/2)*(time * time);

        if(distance >= Math.abs(player.xGetter() - trafficLine) && (player.velocityGetter() > 0)) distanceFlag = 1;
        if(distanceFlag == 1 && Math.abs(player.xGetter() - trafficLine)<0.7){
            player.xSetter(trafficLine);
        }
        // System.out.println(trafficLightStateFlag);
        if(Math.round((float)stopLightSideAi.run(new double[]{trafficLightStateFlag, distanceFlag})[0]) == 0) player.accelerate();
        else player.decelerate();

    }

    private void sideStateTurnTick(Player player){
        int rightFlag = 0;
        int leftFlag = 0;
        double[][] corners = player.cornersGetter();
        double highestPoint = corners[0][1];
        double lowestPoint = corners[3][1];

        if((highestPoint - sideTop*Launcher.height) < sideStateTurnThreshold){
            rightFlag = 1;
        }
        if((lowestPoint - sideBottom*Launcher.height) > -sideStateTurnThreshold){
            leftFlag = 1;
        }
        // System.out.println(Math.abs(player.angleRightGetter())%360);
        if(player.angleRightGetter()%360 > 180){
            rightFlag = 1;
        }
        if(player.angleRightGetter()%360 < 180){
            leftFlag = 1;
        }

        // System.out.println("left: "+leftFlag +  " right: " + rightFlag);

        // System.out.println(Arrays.toString(turnLineSideAi.run(new double[]{leftFlag, rightFlag})));
        double[] turnResponse = turnLineSideAi.run(new double[]{leftFlag, rightFlag});
        // double[] turnResponse = new double[]{0,0};
        if(Math.round(turnResponse[0]) == 1) player.turnLeft();
        if(Math.round(turnResponse[1]) == 1) player.turnRight();

        
    }


    private void middleStateStopTick(Player player, List<CPU> cpuList, int trafficLightState){

        // acceleration
        double trafficLine = middleLeft*Launcher.width + 100;
        double safeDistanceToTurnStart = middleRight*Launcher.width + 100;
        double safeDistanceToTurnEnd = middleRight*Launcher.width - 100;
        int distanceFlag;
        int trafficFlag = 0;

        double velocity = player.velocityGetter()/player.baseGetter();
        distanceFlag = 0;
        // System.out.println(trafficLightStateFlag);
        // System.out.println("stopping, tempx: " + temp.x+" side + room: " + ( sideStopLine + roomBetweenCars));
        
        double time = velocity/(player.decelerationGetter());
        double distance = time + ((player.decelerationGetter()*player.baseGetter())/2)*(time * time);
        
        if(distance >= Math.abs(player.xGetter() - trafficLine) && (player.velocityGetter() > 0)) distanceFlag = 1;
        if(distanceFlag == 1 && Math.abs(player.xGetter() - trafficLine)<0.7){
            player.xSetter(trafficLine);
            // player.velocitySetter(0);
        }
        
        for(int i=0;i<cpuList.size();i++){
            CPU temp = cpuList.get(i);
            if(temp.road == "side" || temp.road == "side1"){
                if(temp.x < safeDistanceToTurnStart && temp.x > safeDistanceToTurnEnd && temp.velocity > 0){
                    trafficFlag = 1;
                }
            }
        }

        System.out.println(trafficFlag + " : "+distanceFlag + " : "+trafficLightState);
        if(Math.round((float)middleLineAi.run(new double[]{trafficFlag, distanceFlag, trafficLightState})[0]) == 0) player.accelerate();
        else player.decelerate();

    }

}
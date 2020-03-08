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

    private Train environmentAi;

    public PlayerAi(){
    }
    public void initEnvironment(double[][] environmentThresholds){
        sideTop = environmentThresholds[0][0]/Launcher.height;
        sideRight = environmentThresholds[0][1]/Launcher.width;
        sideBottom = environmentThresholds[0][2]/Launcher.height;
        sideLeft = environmentThresholds[0][3]/Launcher.width;

        middleTop = environmentThresholds[1][0]/Launcher.height;
        middleRight = environmentThresholds[1][1]/Launcher.width;
        middleBottom = environmentThresholds[1][2]/Launcher.height;
        middleLeft = environmentThresholds[1][3]/Launcher.width;
        
        topTop = environmentThresholds[2][0]/Launcher.height;
        topRight = environmentThresholds[2][1]/Launcher.width;
        topBottom = environmentThresholds[2][2]/Launcher.height;
        topLeft = environmentThresholds[2][3]/Launcher.width;

        environmentAi = new Train();
        randomizeEnvironment();
        environmentAi.train(environmentTrainData, environmentTrainResult, 2, 4, 3);

    //     double[][] trainingData = new double[][] { 
    //         new double[] { 0, 0 }, 
    //         new double[] { 0, 1 }, 
    //         new double[] { 1, 0 },
    //         new double[] { 1, 1 } 
    //  };

    //     double[][] trainingResults = new double[][] {
    //             new double[] { 0 }, 
    //             new double[] { 1 }, 
    //             new double[] { 1 },
    //             new double[] { 0 } 
    //     };

    //     environmentAi.train(trainingData, trainingResults, 2, 3, 1);
        

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

        // results (side, middle, top) eg (1, 0, 0) == side
        // System.out.print(Arrays.deepToString(environmentTrainData));
        // System.out.print(Arrays.deepToString(environmentTrainResult));
        // return new double[] {trainData, result};
        
    }

    public void tick(double x, double y){
        environmentTick(x, y);
    }
    
    private void environmentTick(double x, double y){
        x = x/Launcher.width;
        y = y/Launcher.height;
        System.out.println(Arrays.toString(environmentAi.run(new double[]{x, y})));

        // System.out.println(Arrays.toString(environmentAi.run(new double[]{0, 0})));
        // // System.out.println(Arrays.toString(environmentAi.run(new double[]{1, 0})));
        // // System.out.println(Arrays.toString(environmentAi.run(new double[]{0,1})));
        // System.out.println(Arrays.toString(environmentAi.run(new double[]{1, 1})));

    }
}
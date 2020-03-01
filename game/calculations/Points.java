package driver.game.calculations;

import driver.game.Launcher;
import driver.game.drawings.Environment;

public class Points {

    // borders/ edges
    int 
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

    public Points(){
        // initiating the borders

        sideBottom = 455;
        sideTop = 400;
        sideLeft = 0;
        sideRight = 614;
        
        // intersection
        middleLeft = 614;
        middleRight = 855;
        middleTop = 292;
        middleBottom = 504;
        
        topLeft = 749;
        topRight = 802;
        topTop = 0;
        topBottom = 292;


    }

    public double sideCheck(double y, double x){
        // System.out.println("sidetop: " + sideTop + " y: " + y + " x: " + x);
        if(x > sideLeft && x <= sideRight){
            if(y < sideTop){
                return -50;
            }
            if(y > sideBottom){
                return -1;
            }
        }
        else if(x < 0){
            return -5;
        }
        return 0;
    }

    public double intersectionCheck(double y, double x, int[] trafficLights){
        // System.out.println("sidetop: " + sideTop + " y: " + y + " x: " + x);
        if(x > middleLeft && x <= middleRight && y >= middleTop){
            if(trafficLights[1] == 0){
                return -5;
            }
            if(y >= middleBottom){
                return -5;
            }
            else if( y > middleBottom){
                return - 10;
            }
        }
        else if(x > middleRight){
            return -5;
        }

        return 0;
    }

    public double topCheck(double y, double x){
        // System.out.println("sidetop: " + sideTop + " y: " + y + " x: " + x);
        if( x > sideRight && y < topBottom){
            if(x < topLeft){
                return -50;
            }
            if(x > topRight){
                return -10;
            }
        }
        return 0;
    }

}
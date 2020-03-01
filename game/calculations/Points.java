package driver.game.calculations;

import driver.game.Launcher;
import driver.game.drawings.Environment;
import java.awt.Graphics;
import java.lang.reflect.Array;

import driver.game.calculations.Movement;

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

    public double[][] renderHitbox(Movement movement, double x, double y){
        // System.out.println( Math.abs(movement.angleRight - 90)%360);

        // locations for each corner
        double[] frontRight = new double[]{ x + 43, y + 5};
        double[] frontLeft = new double[]{ x , y + 5};
        double[] backLeft = new double[]{ x , y + 50};
        double[] backRight = new double[]{ x + 41, y + 50};

        double carAngle = Math.abs(movement.angleRight - 90)%360;
        if((carAngle < 105 && carAngle > 75) || (carAngle < 295 && carAngle > 255)){
            frontRight = new double[]{ x + 41, y};
            frontLeft = new double[]{ x + 41, y + 26 + 5};
            backLeft = new double[]{ x, y};
            backRight = new double[]{ x, y + 26 + 5};
            
        }

        if((carAngle < 195 && carAngle > 165) || (carAngle > 345 || carAngle < 15)){
            frontRight = new double[]{ x + 33, y };
            frontLeft = new double[]{ x, y};
            backLeft = new double[]{ x, y + 26 + 15};
            backRight = new double[]{ x + 33, y + 26 + 15};
            
        }


        // g.drawRect((int) frontRight[0], (int) frontRight[1], 10, 10);
        // g.drawRect((int) frontLeft[0], (int) frontLeft[1], 10, 10);
        // g.drawRect((int) backLeft[0], (int) backLeft[1], 10, 10);
        // g.drawRect((int) backRight[0], (int) backRight[1], 10, 10);
        
        return new double[][]{frontRight, frontLeft, backLeft, backRight};

    }

}
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

        sideBottom = Launcher.height/2 - Environment.roadWidth/2 - 10;
        sideTop = Launcher.width/2 - Environment.roadWidth/2 - 60;
        sideLeft = 0;
        sideRight = 0;

        topLeft = 0;
        topRight = 0;
        topTop = 0;
        topBottom = 0;

        middleLeft = 0;
        middleRight = 0;
        middleTop = 0;
        middleBottom = 0;

    }

    public boolean sideCheck(double y){
        if(y < sideBottom){
            return true;
        }
        return false;
    }

}
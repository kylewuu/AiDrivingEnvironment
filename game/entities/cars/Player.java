package driver.game.entities.cars;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import driver.game.Game;
import driver.game.Launcher;
import driver.game.calculations.Movement;
import driver.game.calculations.Points;
import driver.game.drawings.Assets;
import java.util.List;
import driver.game.entities.cars.CPU;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;


public class Player extends Cars {

    private BufferedImage car;
    private Game game;

    public double velocity;
    private double acceleration;
    private double deceleration;
    private double velocityLimit;

    private Movement movement;
    private Points pointsClass;

    double yTemp;
    double xTemp;

    private int points;

    private int[] trafficLights;

    private double pointsCounterThreshold;
    private double pointsCounter;
    private double pointsCounterMultiplier;
    private double pointsCounterMultiplierMultiplier;
    
    private double[][] playerCorners;
    private double[][] cornerHitboxes;
    private boolean freeplay;
    private int pointsEndStall, pointsEndStallMax;
    private int boundEndStall, boundEndStallMax;

    private boolean endLock; // for keeping the points at what it is

    // ai inputs
    boolean go, stop, left, right;

    public Player(Game game, float x, float y, boolean freeplay) {
        super(x, y);
        this.game = game;
        this.freeplay = freeplay;
        
        velocity = 0;
        acceleration = 0.3;
        deceleration = 0.4;
        velocityLimit = 100;
        
        movement = new Movement(velocity, acceleration, deceleration, velocityLimit);
        pointsClass = new Points();
        yTemp = y;
        xTemp = x;

        points = 10000; // starting amount of points

        // for decreasing points if you dont move
        pointsCounter = 0;
        pointsCounterThreshold = 60;
        pointsCounterMultiplier = 1;
        pointsCounterMultiplierMultiplier = 0.1;

        // ai inputs
        go = false;
        stop = false;
        left = false;
        right = false;

        pointsEndStall = 0;
        pointsEndStallMax = 200; // how long to pause for after you reach zero points

        boundEndStall = 0;
        boundEndStallMax = 200;

        endLock = false;
    }

    // @Override
    public void tickUser() {
        
        // physics -----------------------
        // if not turning==
        if(!game.getKeyManager().right && !game.getKeyManager().left){
            // yTemp = y;
            // xTemp = x;
            // movement.angle = 0;
        }

        // if accelerating==
        if(game.getKeyManager().accelerate){
            //if not turning
            if(!game.getKeyManager().right && !game.getKeyManager().left){
                movement.accelerate();
            }
            //if turning
            else if(game.getKeyManager().right || game.getKeyManager().left){
                if(game.getKeyManager().left && !game.getKeyManager().right){
                    // System.out.println("left acc-- velocity: " + movement.velocity);
                    movement.accelerate();
                    movement.calcTurnLeft();
                    y = (float) (yTemp + movement.actualRiseY);
                    x = (float) (xTemp + movement.actualRiseX);
                    yTemp = y;
                    xTemp = x;

                }
                if(!game.getKeyManager().left && game.getKeyManager().right){
                    // System.out.println("right accc-- velocity: " + movement.velocity);
                    movement.accelerate();
                    movement.calcTurnRight();
                    y = (float) (yTemp + movement.actualRiseY);
                    x = (float) (xTemp + movement.actualRiseX);
                    yTemp = y;
                    xTemp = x;

                }
            }
        }

        // if deccelerating==
        if(game.getKeyManager().decelerate){
            //if not turning
            if(!game.getKeyManager().right && !game.getKeyManager().left){
                movement.decelerate();
            }
            //if turning
            else if(game.getKeyManager().right || game.getKeyManager().left){
                if(game.getKeyManager().left && !game.getKeyManager().right){
                    movement.decelerate();
                    if(movement.velocity > 0){
                        // System.out.println("left dec");
                        movement.calcTurnLeft();
                        y = (float) (yTemp + movement.actualRiseY);
                        x = (float) (xTemp + movement.actualRiseX);
                        yTemp = y;
                        xTemp = x;

                    }
                }
                if(!game.getKeyManager().left && game.getKeyManager().right){
                    movement.decelerate();
                    if(movement.velocity > 0){
                        // System.out.println("right dec");
                        movement.calcTurnRight();
                        y = (float) (yTemp + movement.actualRiseY);
                        x = (float) (xTemp + movement.actualRiseX);
                        yTemp = y;
                        xTemp = x;
    
                    }
                }
            }
        }

        // if gliding==
        if(!game.getKeyManager().decelerate && !game.getKeyManager().accelerate){
            //if not turning
            if(!game.getKeyManager().right && !game.getKeyManager().left){
                movement.slowDown();
            }
            //if turning
            else if(game.getKeyManager().right || game.getKeyManager().left){
                if(game.getKeyManager().left && !game.getKeyManager().right){
                    movement.turnSlowDown();
                    if(movement.velocity > 0){
                        // System.out.println("left glide");
                        movement.calcTurnLeft();
                        y = (float) (yTemp + movement.actualRiseY);
                        x = (float) (xTemp + movement.actualRiseX);
                        yTemp = y;
                        xTemp = x;

                    }
                }
                if(!game.getKeyManager().left && game.getKeyManager().right){
                    movement.turnSlowDown();
                    if(movement.velocity > 0){
                        // System.out.println("right glide");
                        movement.calcTurnRight();
                        y = (float) (yTemp + movement.actualRiseY);
                        x = (float) (xTemp + movement.actualRiseX);
                        yTemp = y;
                        xTemp = x;
    

                    }
                }
            }
        }
        // if not turning 
        if(!game.getKeyManager().left && !game.getKeyManager().right){
            // System.out.println("straight nothing");
            movement.calcStraight();
            y += movement.riseYLinear;
            x += movement.riseXLinear;
            yTemp = y;
            xTemp = x;
        }

        // if trying to turn both ways just go straight
        if(game.getKeyManager().left && game.getKeyManager().right){
            // System.out.println("both rn");
            movement.calcStraight();
            y += movement.riseYLinear;
            x += movement.riseXLinear;
            yTemp = y;
            xTemp = x;
        }

        // drawings only --------
        // base figure, straight and no brake
        car = Assets.carStraightNo;

        // braking
        if(game.getKeyManager().decelerate){
            // System.out.println("brake");
            car = Assets.carStraightBrake;
            movement.decelerate();
        }
        // turning without braking
        if(game.getKeyManager().left && !game.getKeyManager().right && !game.getKeyManager().decelerate){
            car = Assets.carLeftNo;
        }
        if(game.getKeyManager().right && !game.getKeyManager().left && !game.getKeyManager().decelerate){
            car = Assets.carRightNo;
        }
        // turning with braking
        if(game.getKeyManager().left && !game.getKeyManager().right && game.getKeyManager().decelerate){
            car = Assets.carLeftBrake;
        }
        if(game.getKeyManager().right && !game.getKeyManager().left && game.getKeyManager().decelerate){
            car = Assets.carRightBrake;
        }
        if(game.getKeyManager().left && game.getKeyManager().right && game.getKeyManager().decelerate){
            car = Assets.carStraightBrake;
        }
        if(game.getKeyManager().left && game.getKeyManager().right && game.getKeyManager().accelerate){
            car = Assets.carStraightNo;
        }

        // rotates the image
        car = Assets.rotate(car, -movement.angleLeft);
        
        if(!endLock) collisionEnvironment(pointsClass.renderHitbox(movement, x, y));
    }

    public void tick(){
        if(!freeplay){
            tickAi();
    
            // resetting ai inputs
            go = false;
            stop = false;
            left = false;
            right = false;
        }

        else if(freeplay){
            tickUser();
        }

        // if less than 0 points then restart the game
        if(points < 0){
            if(pointsEndStall > pointsEndStallMax){
                if(game.getFreePlay() && game.lockGetter()) game.restartFreePlay();
                else if(game.getFreePlay() && !game.lockGetter()) game.restartFreePlay();
                else if(!game.getFreePlay() && game.lockGetter()) game.restartFreePlay();
                else if(!game.getFreePlay() && !game.lockGetter()) game.restartAndIterate();
            }
            pointsEndStall++;
        }

        if(y < 0 && x > 614 ){
            endLock = true;
            if(boundEndStall > boundEndStallMax){
                if(game.getFreePlay() && game.lockGetter()) game.restartFreePlay();
                else if(game.getFreePlay() && !game.lockGetter()) game.restartFreePlay();
                else if(!game.getFreePlay() && game.lockGetter()) game.restartFreePlay();
                else if(!game.getFreePlay() && !game.lockGetter()) game.restartAndIterate();
            }
            boundEndStall++;
        }
        else boundEndStall = 0;
    }

    public void tickAi() {
        // System.out.println(movement.velocity);
        // physics -----------------------
        // if not turning==
        if(!right && !left){
            // yTemp = y;
            // xTemp = x;
            // movement.angle = 0;
        }

        // if accelerating==
        if(go){
            //if not turning
            if(!right && !left){
                movement.accelerate();
            }
            //if turning
            else if(right || left){
                if(left && !right){
                    movement.accelerate();
                    movement.calcTurnLeft();
                    y = (float) (yTemp + movement.actualRiseY);
                    x = (float) (xTemp + movement.actualRiseX);
                    yTemp = y;
                    xTemp = x;

                }
                if(!left && right){
                    movement.accelerate();
                    movement.calcTurnRight();
                    y = (float) (yTemp + movement.actualRiseY);
                    x = (float) (xTemp + movement.actualRiseX);
                    yTemp = y;
                    xTemp = x;

                }
            }
        }

        // if deccelerating==
        if(stop){
            //if not turning
            if(!right && !left){
                movement.decelerate();
            }
            //if turning
            else if(right || left){
                if(left && !right){
                    movement.decelerate();
                    if(movement.velocity > 0 && !go){
                        movement.calcTurnLeft();
                        y = (float) (yTemp + movement.actualRiseY);
                        x = (float) (xTemp + movement.actualRiseX);
                        yTemp = y;
                        xTemp = x;

                    }
                }
                if(!left && right){
                    movement.decelerate();
                    if(movement.velocity > 0 && !go){
                        movement.calcTurnRight();
                        y = (float) (yTemp + movement.actualRiseY);
                        x = (float) (xTemp + movement.actualRiseX);
                        yTemp = y;
                        xTemp = x;
    
                    }
                }
            }
        }

        // if gliding==
        if(!stop && !go){
            //if not turning
            if(!right && !left){
                movement.slowDown();
            }
            //if turning
            else if(right || left){
                if(left && !right){
                    movement.turnSlowDown();
                    if(movement.velocity > 0){
                        movement.calcTurnLeft();
                        y = (float) (yTemp + movement.actualRiseY);
                        x = (float) (xTemp + movement.actualRiseX);
                        yTemp = y;
                        xTemp = x;

                    }
                }
                if(!left && right){
                    movement.turnSlowDown();
                    if(movement.velocity > 0){
                        movement.calcTurnRight();
                        y = (float) (yTemp + movement.actualRiseY);
                        x = (float) (xTemp + movement.actualRiseX);
                        yTemp = y;
                        xTemp = x;
    

                    }
                }
            }
        }
        // if not turning 
        if(!left && !right){
            movement.calcStraight();
            y += movement.riseYLinear;
            x += movement.riseXLinear;
            yTemp = y;
            xTemp = x;
        }

        // if trying to turn both ways just go straight
        if(left && right){
            movement.calcStraight();
            y += movement.riseYLinear;
            x += movement.riseXLinear;
            yTemp = y;
            xTemp = x;
        }

        // drawings only --------
        // base figure, straight and no brake
        car = Assets.carStraightNo;

        // braking
        if(stop){
            car = Assets.carStraightBrake;
            movement.decelerate();
        }
        // turning without braking
        if(left && !right && !stop){
            car = Assets.carLeftNo;
        }
        if(right && !left && !stop){
            car = Assets.carRightNo;
        }
        // turning with braking
        if(left && !right && stop){
            car = Assets.carLeftBrake;
        }
        if(right && !left && stop){
            car = Assets.carRightBrake;
        }
        if(left && right && stop){
            car = Assets.carStraightBrake;
        }
        if(left && right && go){
            car = Assets.carStraightNo;
        }

        // rotates the image
        car = Assets.rotate(car, -movement.angleLeft);
        cornerHitboxes = pointsClass.renderHitbox(movement, x, y);
        if(!endLock) collisionEnvironment(cornerHitboxes);
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(car, (int) x, (int) y, null);
        // pointsClass.renderHitbox(g, movement, x, y);
    }

    public void trafficLightGetter(int [] trafficLight){
        this.trafficLights = trafficLight;

    }


    public void collisionEnvironment( double[][] corners){

        this.playerCorners = corners;
        // points loss for taking too long
        if(pointsCounter >= pointsCounterThreshold){
            points += -1;
            pointsCounter = 0;
            pointsCounterMultiplier += pointsCounterMultiplierMultiplier;
        }
        pointsCounter += 1 * pointsCounterMultiplier;

        // points handler for collision against environment
        
        for( int i = 0; i< 4; i++){
            int tempPoints = points;

            points += pointsClass.sideCheck(corners[i][1], corners[i][0]);
            points += pointsClass.intersectionCheck(y, x, trafficLights);
            points += pointsClass.topCheck(corners[i][1], corners[i][0]);
    
            if(pointsCounter >= pointsCounterThreshold){
                points += -1;
                pointsCounter = 0;
                pointsCounterMultiplier += pointsCounterMultiplierMultiplier;
            }
            pointsCounter += 1 * pointsCounterMultiplier;
    


            // for displaying the points
            if(tempPoints != points){
                // System.out.println(points);
                break;
            }
        }

    }

    public void collisionCPU(List<CPU> carArray){
        int tempPoints = points;
        for(int i = 0; i< carArray.size();i++){
            CPU temp = carArray.get(i);
            for(int j=0;j<4;j++){
                if((playerCorners[j][0] >= temp.x) && (playerCorners[j][0] <= temp.x1) && (playerCorners[j][1] >= temp.y) && (playerCorners[j][1] <= temp.y1)){
                    points += -1000;
                }
                
            }

            if(tempPoints != points){
                break;
            }
            
        }
    }

    public void pointsRender(Graphics g){
        int digitSpacing = 30;
        int digit = 0;
        int temp = points;
        // System.out.println("Starting points -------- "+points);
        int y = Launcher.height - 52;
        int xStarting = Launcher.width - 100;
        int x = xStarting;
        float alpha = (float) 1.0;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(ac);

        g.drawImage(Assets.pointsDisplay, x - 265, y - 5, null);
        while(temp > 0){
            digit = temp%10;
            if(digit==1) x+=8;
            temp = temp/10;
            g.drawImage(Assets.numbersArray[digit], x, y, null);
            x -= digitSpacing;
        }
        if(points < 0 ){
            g.drawImage(Assets.numbersArray[0], xStarting, y, null);
        }
    }

    public void iterationsRender(Graphics g){
        int digitSpacing = 30;
        int digit = 0;
        int temp = game.iterationsGetter();
        // System.out.println("iteratoins: " + temp);
        // System.out.println("Starting points -------- "+points);
        int y = Launcher.height - 110;
        int xStarting = Launcher.width - 100;
        int x = xStarting;
        float alpha = (float) 1.0;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(ac);
        
        g.drawImage(Assets.iterationDisplay, x - 265, y - 5, null);
        while(temp > 0){
            digit = temp%10;
            if(digit==1) x+=8;
            temp = temp/10;
            g.drawImage(Assets.numbersArray[digit], x, y, null);
            x -= digitSpacing;
        }
        if(game.iterationsGetter() <= 0 ){
            g.drawImage(Assets.numbersArray[0], xStarting, y, null);
        }
        
    }

    // getters
    public double xGetter(){
        return (double) x;

    }

    public double yGetter(){
        return (double) y;
        
    }

    public double velocityGetter(){
        return movement.velocity;
    }

    public double decelerationGetter(){
        return movement.deceleration;
    }

    public int baseGetter(){
        return movement.base;
    }

    public double[][] cornersGetter(){
        return cornerHitboxes;
    }

    // setters
    public void xSetter(double x){
        this.x = (float) x;
    }

    public double angleRightGetter(){
        return movement.angleRight;
    }

    public void velocitySetter(double velocity){
        movement.velocity = velocity;
    }

    // ai inputs
    public void accelerate(){
        // movement.accelerate();
        go = true;
    }

    public void decelerate(){
        // movement.decelerate();
        stop = true;
    }

    public void turnLeft(){
        left = true;
    }

    public void turnRight(){
        right = true;
    }

    
}
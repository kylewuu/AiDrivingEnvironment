package driver.game.entities.cars;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import driver.game.Game;
import driver.game.calculations.Movement;
import driver.game.calculations.Points;
import driver.game.drawings.Assets;
import java.util.List;
import driver.game.entities.cars.CPU;


public class Player extends Cars {

    private BufferedImage car;
    private Game game;

    private double velocity;
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

    public Player(Game game, float x, float y) {
        super(x, y);
        this.game = game;
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
    }

    @Override
    public void tick() {
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
                    movement.accelerate();
                    movement.calcTurnLeft();
                    y = (float) (yTemp + movement.actualRiseY);
                    x = (float) (xTemp + movement.actualRiseX);
                    yTemp = y;
                    xTemp = x;

                }
                if(!game.getKeyManager().left && game.getKeyManager().right){
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
            movement.calcStraight();
            y += movement.riseYLinear;
            x += movement.riseXLinear;
            yTemp = y;
            xTemp = x;
        }

        // if trying to turn both ways just go straight
        if(game.getKeyManager().left && game.getKeyManager().right){
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
        
        collisionEnvironment(pointsClass.renderHitbox(movement, x, y));
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
     
            // System.out.println("x: " + x + " y: " + y);


            if(tempPoints != points){
                System.out.println(points);
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
                System.out.println(points);
                break;
            }
            
        }
    }
    
}
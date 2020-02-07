package driver.game.entities.cars;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import driver.game.Game;
import driver.game.calculations.Movement;
import driver.game.drawings.Assets;


public class Player extends Cars {

    private BufferedImage car;
    private Game game;

    private double velocity;
    private double acceleration;
    private double deceleration;

    private Movement movement;

    double yTemp;
    double xTemp;
    

    public Player(Game game, float x, float y) {
        super(x, y);
        this.game = game;
        velocity = 0;
        acceleration = 0.3;
        deceleration = 0.7;
        movement = new Movement(velocity, acceleration, deceleration);

        yTemp = y;
        xTemp = x;
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

        // x += movement.tick(12); // this needs to get changed to not just be the x axis
        System.out.println(movement.velocity + ":" + movement.angleLeft + "--" + movement.angleRight);
        
    }

    @Override
    public void render(Graphics g) {

        g.drawImage(car, (int) x, (int) y, null);
    }
    
}
package driver.game.calculations;

public class Movement{
    public double velocity;
    public double acceleration;
    public double deceleration;

    // testing
    public double angleLeft, angleRight;
    public int r, rBase;
    private double riseX, riseY;
    private double riseYTemp, riseXTemp;
    public double actualRiseX, actualRiseY;
    private double angleRate;
    public double riseXLinear, riseYLinear;
    public double linearVelocity;
    public double velocityLimit;
    public int base;

    public Movement(double velocity,double acceleration,double deceleration, double velocityLimit){
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.velocityLimit = velocityLimit;

        int startingAngle = 10;
        angleLeft = startingAngle;
        angleRight = startingAngle + 180;
        rBase = 50; // 50 is the base
        riseX = 0;
        riseY = 0;
        angleRate = 0.9; // probably don't need to adjust this?
        base = 12;
    }

    public void accelerate(){
        // System.out.println("asc");
        if(velocity <= velocityLimit){
            velocity += acceleration;
        }
    }

    public void decelerate(){
        // System.out.println("dec");
        if(velocity > deceleration){
            velocity -= deceleration;
        }
        else velocity = 0.000000000;
    }

    public void turnAccelerate(){
        velocity += acceleration/2;
    }

    public void turnDecelerate(){
        velocity -= deceleration/2;
            if(velocity < deceleration/2){
                velocity = 0;
            }
    }

    public void slowDown(){
        velocity -= deceleration/3;
        if(velocity < deceleration/3){
            velocity = 0;
        }
    }
    public void turnSlowDown(){
        velocity -= deceleration/1.2;
        if(velocity < deceleration/1.2){
            velocity = 0;
        }
    }

    public double tick(int base){
        return velocity / base;
    }

    public void calcTurnLeft(){
        r = (int) (velocity*3 + 50);
        if(velocity == 0){
            r = rBase;
        }

        if(velocity < 32) angleRate = velocity/22;

        // the formulas for turning left and right!
        riseXTemp = r * Math.sin(Math.toRadians(angleLeft));
        riseYTemp = r * Math.cos(Math.toRadians(angleLeft));

        riseX = r * Math.sin(Math.toRadians(angleLeft + angleRate));
        riseY = r * Math.cos(Math.toRadians(angleLeft + angleRate));

        actualRiseX = riseX - riseXTemp;
        actualRiseY = riseY - riseYTemp;

        angleLeft += angleRate;
        angleRight = angleLeft - 180;

        // System.out.println("angle: "+angle+" coordinates: "+riseX + " : "+riseY);

    }
    public void calcTurnRight(){
        r = (int) (velocity*3 + 50);
        if(velocity == 0){
            r = rBase;
        }

        if(velocity < 32) angleRate = velocity/22;

        // the formulas for turning left and right!
        riseXTemp = r * Math.sin(Math.toRadians(angleRight));
        riseYTemp = r * Math.cos(Math.toRadians(angleRight));

        riseX = r * Math.sin(Math.toRadians(angleRight - angleRate));
        riseY = r * Math.cos(Math.toRadians(angleRight - angleRate));

        actualRiseX = riseX - riseXTemp;
        actualRiseY = riseY - riseYTemp;

        angleRight -= angleRate;
        angleLeft = angleRight + 180;

        // System.out.println("angle: "+angle+" coordinates: "+riseX + " : "+riseY);

    }

    public void calcStraight(){
        linearVelocity = tick(base);
        // can use either left or right angle, they're all the same
        riseXLinear = (linearVelocity * Math.cos(Math.toRadians(angleLeft)));
        riseYLinear = - (linearVelocity * Math.sin(Math.toRadians(angleLeft)));
    }
}
package driver.game.calculations;

public class Movement{
    public double velocity;
    public double acceleration;
    public double deceleration;

    // testing
    public double angle, angleR;
    public int r, rBase;
    public double riseX;
    public double riseY;
    public double angleRate;

    public Movement(double velocity,double acceleration,double deceleration){
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.deceleration = deceleration;

        angle = 0;
        rBase = 50; // 50 is the base
        riseX = 0;
        riseY = 0;
        angleRate = 0.9; // probably don't need to adjust this?
    }

    public void accelerate(){
        velocity += acceleration;
    }

    public void decelerate(){
        velocity -= deceleration;
            if(velocity < deceleration){
                velocity = 0;
            }
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

    public void calcTurn(){
        r = (int) (velocity + 50);
        if(velocity == 0){
            r = rBase;
        }

        angleRate = velocity/10;

        angle += angleRate;
        // the formulas for turning left and right!
        riseX = r * Math.sin(Math.toRadians(angle));
        riseY = r * Math.cos(Math.toRadians(angle));
        // System.out.println("angle: "+angle+" coordinates: "+riseX + " : "+riseY);

    }
}
package driver.game.calculations;

public class Movement{
    public double velocity;
    public double acceleration;
    public double deceleration;

    public Movement(double velocity,double acceleration,double deceleration){
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
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

    public void slowDown(){
        velocity -= deceleration/3;
        if(velocity < deceleration/3){
            velocity = 0;
        }
    }

    public double tick(int base){
        return velocity / base;
    }
}
package game;
public class GameController{
    int fps = 60;
    double timePerTick = 1000000000/fps;
    public double delta = 0;
    long now;
    long lastTime = System.nanoTime();

    public void init(){

    }

    public double running(){
        now = System.nanoTime();
        delta += (now - lastTime) / timePerTick;
        lastTime = now;
        return delta;
    }

}
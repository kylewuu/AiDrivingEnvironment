package driver.game;

public class Launcher{

    public static int width = 1500;
    public static int height = 800;
    public static void main(String[] args){
        Game game = new Game("Drive",width,height);
        game.start();
        
    }

}
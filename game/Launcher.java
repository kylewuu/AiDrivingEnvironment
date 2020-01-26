package driver.game;

public class Launcher{

    public static int width = 800;
    public static int height = 600;
    public static void main(String[] args){
        
        Game game = new Game("Drive",800,600);
        game.start();
    }

}
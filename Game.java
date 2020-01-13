import java.awt.image.BufferStrategy;
import java.awt.Graphics;
import java.awt.Color;

public class Game implements Runnable {
    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;
    private Environment environment;
    private GameController gameController;   

    int x;
    int y;
    
    
    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
    }
    
    private void init(){
        display = new Display(title, width, height);
        environment = new Environment(width, height, g);
        gameController = new GameController();
        display.getCanvas();
        
    }

    // for updating
    private void tick(){
        x+=1;

    }

    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        // clear the screen
        g.clearRect(0, 0, width, height);

        // drawing starts here -------
        environment.draw(g);
        g.fillRect(x, y, 10, 10);


        // drawing ends here --------
        bs.show();
        g.dispose();
    }

    public void run() {
        init();

        gameController.init();



        while(running){
            if(gameController.running() >= 1){
                tick();
                render();
                gameController.delta --;
            }

        }
        stop();
    }

    public synchronized void start() {
        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
      
    }

    public synchronized void stop() {
        if(!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
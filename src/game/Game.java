package game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import display.Display;
import game.drawings.Assets;
import game.drawings.Environment;
import input.KeyManager;
import states.GameState;
import states.MenuState;
import states.State;

public class Game implements Runnable {
    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    public Graphics g;
    private GameController gameController;  
    
    
    
    //States
    private State gameState;
    private State menuState;
    
    // input
    private KeyManager keyManager;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
    }
    
    private void init(){
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        gameController = new GameController();
        display.getCanvas();
        Environment.init(width, height);
        Assets.init();

        gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(gameState);

    }

    // for updating
    private void tick(){
        keyManager.tick();
        
        if(State.getState() != null){
            State.getState().tick();
        }
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


        // rendering starts here ------
        if(State.getState() != null){
            State.getState().render(g);
        }
        // rendering ends here --------

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

    public KeyManager getKeyManager(){
        return keyManager;
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
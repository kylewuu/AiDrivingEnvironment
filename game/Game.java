package driver.game;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import driver.ai.PlayerAi;
import driver.ai.Train;

import driver.display.Display;
import driver.game.drawings.Assets;
import driver.game.drawings.Environment;
import driver.input.KeyManager;
import driver.input.MouseManager;
import driver.states.GameState;
import driver.states.MenuState;
import driver.states.State;

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
    public State gameState;
    public State menuState;
    private PlayerAi initTraining;
    private boolean freeplay;
    private boolean lock;
    
    // input
    private KeyManager keyManager;
    private MouseManager mouseManager;

    private int iterations;

    public Game(String title, int width, int height) {
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
        mouseManager = new MouseManager();
    }
    
    private void init(){
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        display.getFrame().addMouseListener(mouseManager);
        display.getFrame().addMouseMotionListener(mouseManager);
        display.getCanvas().addMouseListener(mouseManager);
        display.getCanvas().addMouseMotionListener(mouseManager);

        
        display.getCanvas();
        Environment.init(width, height);
        Assets.init();

        // gameState = new GameState(this);
        menuState = new MenuState(this);
        State.setState(menuState);

    }

    public void initGameState(int iterations, boolean freeplay, boolean lock){
        this.iterations = iterations;
        this.freeplay = freeplay;
        this.lock = lock;
        if(!freeplay) initTraining = new PlayerAi(new double[][] {{400, 614, 455, 0}, {292, 855, 504, 614}, {0, 802, 292, 749}}, iterations);
        else if(freeplay) initTraining = new PlayerAi(new double[][] {{400, 614, 455, 0}, {292, 855, 504, 614}, {0, 802, 292, 749}}, 0);
        gameState = new GameState(this, initTraining, freeplay);
        running = true;
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

        if(!running){
            gameController = new GameController();
            gameController.init();
        }
        
        // menu state
        while(!running){
            if(gameController.running() >= 1){
                tick();
                render();
                gameController.delta --;
            }
        }

        // don't start counting the time yet so it doens't do any weird speeding up thing
        if(running){
            gameController = new GameController();
            gameController.init();
        }

        while(running){
            if(gameController.running() >= 1){
                tick();
                render();
                gameController.delta --;
            }

            // restart the game not from menu
            if(keyManager.restart){
                gameState = new GameState(this, initTraining, freeplay);
                State.setState(gameState);
            }

            if(keyManager.menu){
                mouseManager.leftPressedSetFalse();
                menuState = new MenuState(this);
                State.setState(menuState);

            }
            

        }
        stop();
    }

    // getters 
    public KeyManager getKeyManager(){
        return keyManager;
    }

    public MouseManager getMouseManager(){
        return mouseManager;
    }

    public synchronized void start() {
        // if(running) return;
        // running = true;
        thread = new Thread(this);
        thread.start();
    }

    public boolean getFreePlay(){
        return freeplay;
    }

    public int iterationsGetter(){
        return iterations;
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

    public void restartAndIterate(){
        // System.out.println(iterations);
        menuState = new MenuState(this);
        State.setState(menuState);
        iterations += 5;
        initGameState(iterations, false, false);
        State.setState(gameState);
        
    }

    public void restartFreePlay(){
        gameState = new GameState(this, initTraining, freeplay);
        State.setState(gameState);
    }

    public boolean lockGetter(){
        return lock;
    }
}
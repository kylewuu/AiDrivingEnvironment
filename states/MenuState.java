package driver.states;

import java.awt.Graphics;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Color;

import driver.game.Game;
import driver.game.Launcher;
import driver.game.drawings.Assets;
import driver.game.drawings.Environment;
import driver.game.entities.clouds.CloudsLoop;
import driver.game.entities.trafficLight.TrafficLights;



public class MenuState extends State {
    private CloudsLoop cloudLoop;
    float trafficLightStartingX, trafficLightStartingY;
    private TrafficLights trafficLight;

    int startX, startY;
    int decreaseX, decreaseY;
    int displayX, displayY;
    int increaseX, increaseY;
    int increaseDecreaseWidth, displayWidth, startWidth;
    int increaseDecreaseHeight, displayHeight, startHeight;
    int freeX, freeY , freeWidth, freeHeight;
    int lockX, lockY, lockWidth, lockHeight;

    public boolean lock;
    public boolean freeplay;

    public static int  iterationsChosen;
    int iterationsChooseJump;

    public MenuState(Game game) {
        super(game);
        cloudLoop = new CloudsLoop();
        trafficLightStartingX = Launcher.width/2;
        trafficLightStartingY = Launcher.height/2;
        trafficLight = new TrafficLights(game, trafficLightStartingX, trafficLightStartingY);

        iterationsChooseJump = 5;
        iterationsChosen = iterationsChooseJump; // default iterations to 100;

        freeplay = false;
        lock = false;

        int gapBetweenButton = 25;
        startX = Launcher.width/2 - 150;
        startY = 350;
        startWidth = 300;
        startHeight = 50;

        lockX = startX + startWidth + 50;
        lockY = startY;
        lockWidth = 50;
        lockHeight = 50;

        freeX = startX;
        freeY = startY + startHeight + 50;
        freeWidth = startWidth;
        freeHeight = startHeight;

        increaseDecreaseWidth = 50;
        increaseDecreaseHeight = 50;

        decreaseX = startX;
        decreaseY = startY - increaseDecreaseHeight - gapBetweenButton;

        displayX = decreaseX + increaseDecreaseWidth + gapBetweenButton;
        displayY = decreaseY;
        displayWidth = startWidth - increaseDecreaseWidth*2 - gapBetweenButton*2; // 100 is since 50 gap between each
        displayHeight = increaseDecreaseHeight;

        increaseX = (startX + startWidth) - increaseDecreaseWidth;
        increaseY = decreaseY;

    }

    @Override
    public void tick() {
        cloudLoop.tick();
        trafficLight.tick();

        if(game.getMouseManager().isLeftPressed()){
            if(startDetection()){
                game.initGameState(iterationsChosen, freeplay, lock);
                State.setState(game.gameState);
            }

            if(increaseDetection() && iterationsChosen<9995) iterationsChosen += iterationsChooseJump;
            if(decreaseDetection() && iterationsChosen>= iterationsChooseJump) iterationsChosen -= iterationsChooseJump;
            if(freeplayDetection()){
                freeplay = true;
                game.initGameState(iterationsChosen, freeplay, lock);
                State.setState(game.gameState);
            }
            
            if(lockDetection()){
                if(lock) lock = false;
                else if(!lock) lock = true;
            }



        }
    }

    @Override
    public void render(Graphics g) {
        Environment.draw(g);
        trafficLight.render(g);
        cloudLoop.render(g);
        renderMenuLights(g);
        g.drawImage(Assets.start, startX, startY, null); // start button
        g.drawImage(Assets.decrement, decreaseX, decreaseY, null); // decrease button
        g.drawImage(Assets.display, displayX, displayY, null); // display button
        g.drawImage(Assets.increment, increaseX, increaseY, null); // increase button
        g.drawImage(Assets.freePlay, freeX, freeY, null);
        if(lock) g.drawImage(Assets.checked, lockX, lockY, null);
        else if(!lock) g.drawImage(Assets.unchecked, lockX, lockY, null);
        displayIteration(g); // rendering points
    }

    public void displayIteration(Graphics g){
        int digitSpacing = 30;
        int digit = 0;
        int temp = iterationsChosen;
        // System.out.println("Starting points -------- "+points);
        int y = displayY + 5;
        int xStarting = displayX + displayWidth - digitSpacing - 10;
        int x = xStarting;
        float alpha = (float) 1.0;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(ac);

        while(temp > 0){
            digit = temp%10;
            if(digit==1) x+=8;
            temp = temp/10;
            g.drawImage(Assets.numbersArray[digit], x, y, null);
            x -= digitSpacing;
        }
        if(iterationsChosen <= 0 ){
            g.drawImage(Assets.numbersArray[0], xStarting, y, null);
        }

    }


    public boolean startDetection(){
        game.getMouseManager().leftPressedSetFalse();
        // System.out.println(game.getMouseManager().getMouseX() + " : " + game.getMouseManager().getMouseY());
        if(game.getMouseManager().getMouseX() >= startX && 
        game.getMouseManager().getMouseX() <= startX + startWidth && 
        game.getMouseManager().getMouseY() >= startY &&
        game.getMouseManager().getMouseY() <= startY + startHeight) return true;
        return false;
    }

    public boolean increaseDetection(){
        game.getMouseManager().leftPressedSetFalse();
        if(game.getMouseManager().getMouseX() >= increaseX && 
        game.getMouseManager().getMouseX() <= increaseX + increaseDecreaseWidth && 
        game.getMouseManager().getMouseY() >= increaseY &&
        game.getMouseManager().getMouseY() <= increaseY + increaseDecreaseHeight) return true;
        return false;
    }

    public boolean decreaseDetection(){
        game.getMouseManager().leftPressedSetFalse();
        if(game.getMouseManager().getMouseX() >= decreaseX && 
        game.getMouseManager().getMouseX() <= decreaseX + increaseDecreaseWidth && 
        game.getMouseManager().getMouseY() >= decreaseY &&
        game.getMouseManager().getMouseY() <= decreaseY + increaseDecreaseHeight) return true;
        return false;
    }

    public boolean freeplayDetection(){
        game.getMouseManager().leftPressedSetFalse();
        if(game.getMouseManager().getMouseX() >= freeX && 
        game.getMouseManager().getMouseX() <= freeX + freeWidth && 
        game.getMouseManager().getMouseY() >= freeY &&
        game.getMouseManager().getMouseY() <= freeY + freeHeight) return true;
        return false;
    }

    public boolean lockDetection(){
        game.getMouseManager().leftPressedSetFalse();
        if(game.getMouseManager().getMouseX() >= lockX && 
        game.getMouseManager().getMouseX() <= lockX + lockWidth && 
        game.getMouseManager().getMouseY() >= lockY &&
        game.getMouseManager().getMouseY() <= lockY + lockHeight) return true;
        return false;
    }

    public boolean lockGetter(){
        return lock;
    }

    public void renderMenuLights(Graphics g){
        // float alpha = (float) 0.5;
        // AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        // Graphics2D g2d = (Graphics2D) g;
        // g2d.setComposite(ac);
        int alpha = 127; // out of 256
        Color menuBackground = new Color(0, 0, 0, alpha);
        g.setColor(menuBackground);
        g.fillRect(0, 0, Launcher.width, Launcher.height);

        // alpha = (float) 1.0;
        // ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        // g2d = (Graphics2D) g;
        // g2d.setComposite(ac);
    }
}
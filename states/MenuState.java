package driver.states;

import java.awt.Graphics;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;

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

        int gapBetweenButton = 25;
        startX = Launcher.width/2 - 150;
        startY = 350;
        startWidth = 300;
        startHeight = 50;

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
                game.initGameState(iterationsChosen, freeplay);
                State.setState(game.gameState);
            }

            if(increaseDetection()) iterationsChosen += iterationsChooseJump;
            if(decreaseDetection() && iterationsChosen>= iterationsChooseJump) iterationsChosen -= iterationsChooseJump;
            if(freeplayDetection()){
                freeplay = true;
                game.initGameState(iterationsChosen, freeplay);
                State.setState(game.gameState);
            }
            



        }
    }

    @Override
    public void render(Graphics g) {
        Environment.draw(g);
        trafficLight.render(g);
        cloudLoop.render(g);
        g.fillRect(startX, startY, startWidth, startHeight); // start button
        g.fillRect(decreaseX, decreaseY, increaseDecreaseWidth, increaseDecreaseHeight); // decrease button
        g.fillRect(displayX, displayY, displayWidth, displayHeight); // display button
        g.fillRect(increaseX, increaseY, increaseDecreaseWidth, increaseDecreaseHeight); // increase button
        g.fillRect(freeX, freeY, freeWidth, freeHeight);
        displayIteration(g); // rendering points
    }

    public void displayIteration(Graphics g){
        int digit = 0;
        int temp = iterationsChosen;
        // System.out.println("Starting points -------- "+points);
        int y = displayY + 5;
        int xStarting = displayX + displayWidth - 10;
        int x = xStarting;
        float alpha = (float) 1.0;
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(ac);

        while(temp > 0){
            digit = temp%10;
            // System.out.println("points digit: " + digit);
            temp = temp/10;
            g.drawImage(Assets.numbersArray[digit], x, y, null);
            x -= 20;
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
}
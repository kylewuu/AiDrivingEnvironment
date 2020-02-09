package driver.states;

import java.awt.Graphics;

import driver.game.Game;
import driver.game.Launcher;
import driver.game.drawings.Environment;
import driver.game.entities.cars.Player;
import driver.game.entities.trafficLight.TrafficLights;


public class GameState extends State {
    float carStartingX,carStartingY;
    float trafficLightStartingX, trafficLightStartingY; 

    private Player player;
    private TrafficLights trafficLight;

    public GameState(Game game){
        super(game);
        carStartingX = 15;
        carStartingY = (Launcher.height/2 + 20);
        player = new Player(game, carStartingX, carStartingY);

        trafficLightStartingX = Launcher.width/2 + 50;
        trafficLightStartingY = 330;

        trafficLight = new TrafficLights(game, trafficLightStartingX, trafficLightStartingY);
    }

    @Override
    public void tick() {

        player.tick();
        trafficLight.tick();
    }

    @Override
    public void render(Graphics g) {
        Environment.draw(g); // fix up environment
        player.render(g);
        trafficLight.render(g);
    }
    
}
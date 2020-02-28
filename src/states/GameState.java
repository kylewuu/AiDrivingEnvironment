package states;

import java.awt.Graphics;

import game.Game;
import game.Launcher;
import game.drawings.Environment;
import game.entities.cars.Player;
import game.entities.trafficLight.TrafficLights;
import game.entities.cars.CPULoop;


public class GameState extends State {
    float carStartingX,carStartingY;
    float trafficLightStartingX, trafficLightStartingY; 

    private Player player;
    private TrafficLights trafficLight;
    private CPULoop cpuLoop;

    public GameState(Game game){
        super(game);
        carStartingX = 15;
        carStartingY = (Launcher.height/2 + 20) - 9;
        player = new Player(game, carStartingX, carStartingY);

        trafficLightStartingX = Launcher.width/2;
        trafficLightStartingY = Launcher.height/2;

        trafficLight = new TrafficLights(game, trafficLightStartingX, trafficLightStartingY);
        cpuLoop = new CPULoop();
    }

    @Override
    public void tick() {

        player.tick();
        trafficLight.tick();
        cpuLoop.speedControl(trafficLight.stateGetter());
        cpuLoop.tick();

    }

    @Override
    public void render(Graphics g) {
        Environment.draw(g); // fix up environment
        player.render(g);
        cpuLoop.render(g);
        trafficLight.render(g);
    }
    
}
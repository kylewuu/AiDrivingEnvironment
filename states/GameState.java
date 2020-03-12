package driver.states;

import java.awt.Graphics;

import driver.ai.PlayerAi;
import driver.game.Game;
import driver.game.Launcher;
import driver.game.drawings.Environment;
import driver.game.entities.cars.Player;
import driver.game.entities.trafficLight.TrafficLights;
import driver.game.entities.cars.CPULoop;


public class GameState extends State {
    float carStartingX,carStartingY;
    float trafficLightStartingX, trafficLightStartingY; 

    private Player player;
    private TrafficLights trafficLight;
    private CPULoop cpuLoop;
    private PlayerAi playerAi;

    public GameState(Game game){
        super(game);
        carStartingX = 15;
        carStartingY = (Launcher.height/2 + 20) - 9;
        player = new Player(game, carStartingX, carStartingY);

        trafficLightStartingX = Launcher.width/2;
        trafficLightStartingY = Launcher.height/2;

        trafficLight = new TrafficLights(game, trafficLightStartingX, trafficLightStartingY);
        cpuLoop = new CPULoop();
        playerAi = new PlayerAi(new double[][] {{400, 614, 455, 0}, {292, 855, 504, 614}, {0, 802, 292, 749}});
        

    }

    @Override
    public void tick() {
        player.tick();
        trafficLight.tick();
        cpuLoop.speedControl(trafficLight.stateGetter());
        player.trafficLightGetter(trafficLight.stateGetter());
        cpuLoop.tick();
        player.collisionCPU(cpuLoop.getter());
        // playerAi.tick(
        //     player.xGetter(),
        //     player.yGetter(),
        //     trafficLight.stateGetter(),
        //     player.velocityGetter(),
        //     player.decelerationGetter(),
        //     player.baseGetter()
        //     );
        playerAi.tick(player, trafficLight.stateGetter());

    }

    @Override
    public void render(Graphics g) {
        Environment.draw(g); // fix up environment
        player.render(g);
        cpuLoop.render(g);
        trafficLight.render(g);
    }
    
}
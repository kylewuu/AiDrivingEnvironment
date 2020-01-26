package driver.states;

import java.awt.Graphics;

import driver.game.Game;
import driver.game.Launcher;
import driver.game.drawings.Environment;
import driver.game.entities.cars.Player;


public class GameState extends State {
    float x,y;

    private Player player;

    public GameState(Game game){
        super(game);
        x = (Launcher.width/2 - Environment.roadWidth/2) + 105;
        y = (Launcher.height - 50);
        player = new Player(game, x, y);
    }

    @Override
    public void tick() {
        x += 1;
        player.tick();

    }

    @Override
    public void render(Graphics g) {
        Environment.draw(g); // fix up environment
        player.render(g);

    }
    
}
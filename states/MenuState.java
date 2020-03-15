package driver.states;

import java.awt.Graphics;

import driver.game.Game;

public class MenuState extends State {
    public MenuState(Game game) {
        super(game);
    }

    @Override
    public void tick() {
        // TODO Auto-generated method stub
        System.out.println(game.getMouseManager().getMouseX() + " : " + game.getMouseManager().getMouseY());
        if(game.getMouseManager().isLeftPressed()){
            game.initGameState();
            State.setState(game.gameState);

        }
    }

    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub

    }
}
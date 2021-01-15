package driver.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
    private boolean[] keys;
    public boolean accelerate, decelerate, left, right;
    public boolean restart;
    public boolean menu;

    public KeyManager() {
        keys = new boolean[256];
    }

    public void tick() {
        accelerate = keys[KeyEvent.VK_W];
        decelerate = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        restart = keys[KeyEvent.VK_R];
        menu = keys[KeyEvent.VK_ESCAPE];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }
}

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private boolean keyPressed;
    public int keyCode = 0;

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed = true;
        keyCode = e.getKeyCode();
    }
    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed = false;
        keyCode = e.getKeyCode();
    }
    public int keyDown() {
        if(keyPressed) return keyCode;
        return 0;
    }
    public int keyUp() {
        if(!keyPressed) return keyCode;
        return 0;
    }
}

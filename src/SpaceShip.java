
import java.awt.event.KeyEvent;

public class SpaceShip extends Entity{
    String texture = "assets\\spaceship.png";
    SpaceShip(int spawnX, int spawnY) {
        loadTexture(texture, Main.scale);
        x = spawnX;
        y = spawnY;
        speed = 10;
    }

    public void update(int keyCodePressed, int keyCodeReleased) {
        handleKeyPressed(keyCodePressed);
        handleKeyReleased(keyCodeReleased);
        movePlayer();
    }

    private void handleKeyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_D:
                movement = 1;
                break;
            case KeyEvent.VK_A:
                movement = -1;
                break;
            default:
                break;
        }
    }
    private void handleKeyReleased(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_D:
                movement = 0;
                break;
            case KeyEvent.VK_A:
                movement = -0;
                break;
            default:
                break;
        }
    }
    private void movePlayer() {
        x += movement * speed;
        if(x >= 896 - getWidth()) {
            x = 896 - getWidth();
        } else if (x <= 0) {
            x = 0;
        }
    }

   @Override
   public boolean isColliding(Entity e) {
        if(e.getX() <= x + 20 + getWidth() - 40 && e.getX() >= x + 20
            && e.getX() + e.getWidth() <= x + 20 + getWidth() - 40
            && e.getX() + e.getWidth() > x +20
            && e.getY() <= y + 20 + getHeight() - 70
            && e.getY() > y + 20) return true;
        return false;
   }
}



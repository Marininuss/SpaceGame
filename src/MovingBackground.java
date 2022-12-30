
import javax.swing.*;
import java.awt.*;

public class MovingBackground extends Entity{
    int speed;

    MovingBackground(int moveSpeed, String texturePath, int x, int y) {
        speed = moveSpeed;
        loadTexture(texturePath, Main.scale);
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += speed;
        if(y >= 896) {
            y = -896;
        }
    }
}

import javax.swing.*;
import java.awt.*;

public class Entity {
    public int x,y,speed;
    public int movement;
    private ImageIcon imageIcon;
    public Image texture;

    public void loadTexture(String texturePath, int scale) {
        imageIcon = new ImageIcon(texturePath);
        texture = imageIcon.getImage().getScaledInstance(imageIcon.getIconWidth() * scale, imageIcon.getIconHeight() * scale, Image.SCALE_DEFAULT);
    }

    public boolean isColliding(Entity entity) {
        return (entity.getX() >= x 
            && entity.getX() <= x + getWidth() && entity.getY() >= y
            && entity.getY() <= y + getWidth()
            || entity.getX() + entity.getWidth() >= x
            && entity.getX() + entity.getWidth() <= x + getWidth()
            && entity.getY() + entity.getHeight() >= y
            && entity.getY() + entity.getHeight() <= y + getWidth());
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public Image getTexture() {
        return texture;
    }
    public int getWidth() {
        return texture.getWidth(null);
    }
    public int getHeight() {
        return texture.getHeight(null);
    }
}

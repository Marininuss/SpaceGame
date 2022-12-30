
import java.util.Random;

public class Trash extends Entity{

    Random rnd;
    String[] textures = {"assets\\trash.png","assets\\trash2.png"};
    Trash(int x, int y, int speed) {
        rnd = new Random();
        this.speed = speed;
        this.x = x;
        this.y = y;
        loadTexture(textures[rnd.nextInt(textures.length)],Main.scale);
    }

    public void update() {
        y += speed;
    }

    @Override
    public boolean isColliding(Entity entity) {
        if(entity.getClass() == SpaceShip.class) {
            if((entity.getX() +20 <= x +texture.getWidth(null) && entity.getX()+20 >= x && entity.getY() +20 <= y +texture.getHeight(null) && entity.getY() + 20 >= y)
                    || (entity.getX()+ 20 + entity.getWidth() - 40 <= x +texture.getWidth(null) && entity.getX()+20 + entity.getWidth() - 40 >= x && entity.getY() + 20 <= y +texture.getHeight(null) && entity.getY() + 20 >= y)
                    || ((entity.getX()+20 <= x +texture.getWidth(null) && entity.getX()+20 >= x && entity.getY() + 20 + entity.getHeight() - 70 <= y +texture.getHeight(null) && entity.getY() + 20 + entity.getHeight() -70 >= y))
                    || (entity.getX()+20 + entity.getWidth() - 40 <= x +texture.getWidth(null) && entity.getX()+20 + entity.getWidth() - 40 >= x && entity.getY() + 20 + entity.getHeight() - 70 <= y +texture.getHeight(null) && entity.getY() + 20 + entity.getHeight() -70 >= y)) {
                return true;
            }
        }
        else {
            return (entity.getX() >= x
                    && entity.getX() <= x +getWidth() && entity.getY() >= y
                    && entity.getY() <= y +getWidth()
                    || entity.getX() + entity.getWidth() >= x
                    && entity.getX() + entity.getWidth() <= x +getWidth()
                    && entity.getY() + entity.getHeight() >= y
                    && entity.getY() + entity.getHeight() <= y +getHeight());
        }
        return false;
    }
}

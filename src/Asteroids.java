import java.util.Random;

public class Asteroids extends Entity{

    Random random;
    String[] textures;
    private int gridX;
    private int gridY;
    int[] textureWidth = {175,105,105,105};
    int width,height;
    Asteroids(int x, int y, int speed) {
        random = new Random();
        textures = new String[]{"Asteroid.png", "Asteroid2.png", "Asteroid3.png", "Asteroid4.png"};
        int randTexture = random.nextInt(textures.length);
        loadTexture("assets\\" + textures[randTexture], Main.scale);
        this.x = x;
        this.y = y;
        this.speed = speed;
        movement = 1;
        width = textureWidth[randTexture];
    }

    public void update() {
        y += movement * speed;
    }

    public void setGridPos(int x, int y) {
        gridX = x;
        gridY = y;
    }
    public int getGridX() {
        return gridX;
    }
    public int getGridY() {
        return gridY;
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
            && entity.getX() <= x +width && entity.getY() >= y
            && entity.getY() <= y +width
            || entity.getX() + entity.getWidth() >= x
            && entity.getX() + entity.getWidth() <= x +width
            && entity.getY() + entity.getHeight() >= y
            && entity.getY() + entity.getHeight() <= y +width);
        }
        return false;
    }

    @Override
    public int getWidth() {
        return width;
    }
    @Override
    public int getHeight() {
        return height;
    }
}

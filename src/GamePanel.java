import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


public class GamePanel extends JPanel implements Runnable{
    public boolean running;
    Thread gameThread;
    KeyHandler keyHandler;
    SpaceShip spaceShip;
    MovingBackground mB1;
    MovingBackground mB2;
    Random random;
    Asteroids[] asteroids;
    boolean collisionShapes = false;
    private final int width = 896;
    private final int height = 896;
    public int score = 0;
    int asteroidSpeed = 5;
    AudioPlayer audioPlayer;
    boolean alive = false;
    boolean spawnNewAsteroids = true;
    private boolean nextLevel = false;
    Trash[] trash;
    private int trashScore = 5;
    int count = 0;
    public GamePanel(){
        random = new Random();
        keyHandler = new KeyHandler();
        running = true;
        gameThread = new Thread(this);
        spaceShip = new SpaceShip(width / 2 - 91/2, height / 2 + 100);
        mB1 = new MovingBackground(2,"assets\\space_background.png",0,-1 * height);
        mB2 = new MovingBackground(2,"assets\\space_background2.png",0,0);
        audioPlayer = new AudioPlayer();
        asteroids = new Asteroids[13];
        trash = new Trash[5];
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        initGame();
        gameThread.start();
        audioPlayer.playMusic();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(mB1.getTexture(), mB1.getX(), mB1.getY(), null);
        g2d.drawImage(mB2.getTexture(), mB2.getX(), mB2.getY(), null);
        g2d.drawImage(spaceShip.getTexture(), spaceShip.getX(), spaceShip.getY(), null);
        if (alive) {
            drawAsteroids(g2d);
            if (collisionShapes) {
                drawCollisionShapes(g2d);
            }
            drawScore(g2d);
            drawTrash(g2d);
        } else {
            drawStartScreen(g2d);
        }
        g2d.dispose();
    }

    // Game loop with 60 fps
    @Override
    public void run() {
        double drawInterval = 1000000000 / 60f;
        double nextDrawTime = System.nanoTime() + drawInterval;
            while (running) {
                try {
                    update(); // sets the new position of the spaceship or of the asteroids
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
                    throw new RuntimeException(e);
                }
                repaint();
                try {
                    double remainingTime = nextDrawTime - System.nanoTime();
                    remainingTime = remainingTime / 1000000;
                    if (remainingTime < 0) {
                        remainingTime = 0;
                    }
                    Thread.sleep((long) remainingTime);

                    nextDrawTime += drawInterval;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    public void update() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        mB1.move();
        mB2.move();
        if (alive) {
            spaceShip.update(keyHandler.keyDown(), keyHandler.keyUp());
            updateAsteroids();
            updateTrash();
        }else updateStartScreen();
    }
    public void initGame() {
        if(alive) {
            spawnNewAsteroids = true;
            spawnAsteroids();
            spaceShip.movement = 0;
        }
    }
    private void spawnAsteroids() {
        score++;
        if(score % 20 == 0 && score != 0 && asteroidSpeed <= 13) {
            asteroidSpeed++;
            nextLevel = true;
        }
        if (!spawnNewAsteroids) return;
        int randAmount = random.nextInt(3) + 2;
        boolean spawnTrash = false;
        if(random.nextInt(8) == 4) {
            randAmount -= 1;
            spawnTrash = true;
        }
        int count = 0;
        Asteroids lastAsteroid = null;
        for (int i = 0; i < asteroids.length; i++) {
            if (asteroids[i] == null) {
                int randX = random.nextInt(250) + 200 * count;
                if (lastAsteroid != null) {
                    if (lastAsteroid.getX() >= randX || lastAsteroid.getX() + lastAsteroid.getWidth() >= randX) {
                        randX += 187;
                    }
                }
                asteroids[i] = new Asteroids(randX, -150, asteroidSpeed);
                lastAsteroid = asteroids[i];
                count++;
            }
            if (count == randAmount) break;
        }
        if (spawnTrash) {
            for (int i = 0; i < trash.length; i++) {
                if (trash[i] == null) {
                    if (lastAsteroid!=null) {
                        trash[i] = new Trash(lastAsteroid.getX() + lastAsteroid.getWidth() + 20, -150, asteroidSpeed);
                        break;
                    }
                }
            }
        }
    }
        private void drawStartScreen(Graphics2D g2d) {
            Font font = new Font("Verdana", Font.BOLD, 60);
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);
            g2d.drawString("Space Game", width / 2 - 200, 100);
            font = new Font("Verdana", Font.ITALIC, 30);
            g2d.setFont(font);
            g2d.drawString("Press space to play", width / 2 - 150, 500);
        }
        private void updateStartScreen() {
            if (keyHandler.keyCode == KeyEvent.VK_SPACE) {
                alive = true;
                score = -1;
                asteroidSpeed = 5;
                spaceShip.movement = 0;
                initGame();
            }
        }
        private void updateAsteroids() {
            for (int i = 0; i < asteroids.length; i++) { // updates all asteroids and check if they reached the screen end
                if (asteroids[i] != null) {
                    asteroids[i].update();
                    if (spawnNewAsteroids) {
                        if (asteroids[i].getY() == ((550 / asteroidSpeed) * asteroidSpeed) -150) {
                            spawnAsteroids();
                            spawnNewAsteroids = false;
                        }
                    }
                    if (asteroids[i].getY() > 896) {
                        asteroids[i] = null;
                        spawnNewAsteroids = true;
                        if(nextLevel) {
                            spawnAsteroids();
                            nextLevel = false;
                        }
                    }
                }
            }
            for (int i = 0; i < asteroids.length; i++) {
                if (asteroids[i] != null) {
                    if (asteroids[i].isColliding(spaceShip)) {
                        if (alive) {
                            audioPlayer.setSound(audioPlayer.EXPLOSION_SOUND);
                            audioPlayer.playSound();
                            alive = false;
                            Arrays.fill(asteroids, null);
                            Arrays.fill(trash,null);
                        }
                    }
                }
            }
        }
        private void updateTrash() {
            for (int i = 0; i < trash.length; i++) {
                if(trash[i] != null) {
                    trash[i].update();

                    if (trash[i].getY() >= height) {
                        trash[i] = null;
                        continue;
                    }
                    if (trash[i].isColliding(spaceShip)) {
                        audioPlayer.setSound(audioPlayer.COIN_SOUND);
                        audioPlayer.playSound();
                        score += trashScore;
                        trash[i] = null;
                    }
                }
            }
        }
        private void drawAsteroids(Graphics2D g2d) {
            for (int i = 0; i < asteroids.length; i++) {
                if (asteroids[i] != null) {
                    g2d.drawImage(asteroids[i].getTexture(), asteroids[i].getX(), asteroids[i].getY(), null);
                }
            }
        }
        private void drawCollisionShapes(Graphics2D g2d) {
            for (int i = 0; i < asteroids.length; i++) {
                if (asteroids[i] != null) {
                g2d.setColor(Color.RED);
                g2d.drawRect(asteroids[i].getX(), asteroids[i].getY(), asteroids[i].getWidth(), asteroids[i].getHeight());
                }
            }
            g2d.setColor(Color.GREEN);
            g2d.drawRect(spaceShip.getX() + 20, spaceShip.getY() + 20, spaceShip.getWidth() - 40, spaceShip.getHeight() - 70);
            g2d.drawLine(0,300,width,300);
        }

        private void drawScore(Graphics2D g2d) {
            Font font = new Font("Verdana", Font.BOLD, 40);
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);
            g2d.drawString(Integer.toString(score), width / 2 - 20, 60);
        }
        private void drawTrash(Graphics2D g2d) {
            for (int i = 0; i < trash.length; i++) {
                if(trash[i] != null) {
                    g2d.drawImage(trash[i].getTexture(), trash[i].getX(), trash[i].getY(), null);
                }
            }
        }
}

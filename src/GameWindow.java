import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame{
    public void init() {
        GamePanel panel = new GamePanel();
        this.setTitle("Space Game");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.BLACK);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
}
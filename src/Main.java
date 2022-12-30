
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {
    public static GameWindow gameWindow;
    public static int scale = 7;

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Main.gameWindow = new GameWindow();
        gameWindow.init();
    }
}

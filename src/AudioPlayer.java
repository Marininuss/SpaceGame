import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {
    public int EXPLOSION_SOUND = 0;
    public int BACKGROUND_MUSIC = 1;
    public int COIN_SOUND = 2;
    private String[] sounds = {"assets\\explosion.wav","assets\\music.wav","assets\\pickUp.wav"};
    Clip clip;
    public void setSound(int i) {
        File file = new File(sounds[i]);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception e) {

        }
    }

    public void playSound() {
        try {
            if (clip != null) {
                clip.start();
            }
        }catch (Exception e) {

        }
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playMusic() {
        setSound(1);
        if (clip != null) {
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
    public void stop() {
        clip.stop();
    }
}

package Sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Music {

    private Clip gameOverSound;
    private Clip eatAppleSound;
    private Clip gameMusicSound;

    public Music() { initSounds(); }


    public void initSounds() {

        try {
            URL url = this.getClass().getClassLoader().getResource("Sound/gameOver.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            gameOverSound = AudioSystem.getClip();
            gameOverSound.open(audioInputStream);

            URL url1 = this.getClass().getClassLoader().getResource("Sound/eatApple.wav");
            AudioInputStream audioInputStream1 = AudioSystem.getAudioInputStream(url1);
            eatAppleSound = AudioSystem.getClip();
            eatAppleSound.open(audioInputStream1);

            URL url2 = this.getClass().getClassLoader().getResource("Sound/gameMusic.wav");
            AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(url2);
            gameMusicSound = AudioSystem.getClip();
            gameMusicSound.open(audioInputStream2);
        }

        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {}

    }

    public void pauseMusic() {
        if(gameMusicSound.isRunning()) {
            gameMusicSound.stop();
        }
        else {
            gameMusicSound.loop(100);
            gameMusicSound.start();
        }
    }

    public void playMusic() {
        gameMusicSound.setMicrosecondPosition(0);
        gameMusicSound.loop(100);
        gameMusicSound.start();
    }

    public void stopMusic() {
        gameMusicSound.stop();
    }

    public void playGameOverSound() {
        gameOverSound.setMicrosecondPosition(0);
        gameOverSound.start();
    }

    public void playEatAppleSound() {
        eatAppleSound.setMicrosecondPosition(0);
        eatAppleSound.start();
    }
}

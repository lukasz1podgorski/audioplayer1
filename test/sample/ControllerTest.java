package sample;

import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @Test
    void initialize() {
    }

    @Test
    void openFile() {
    }

    @Test
    void playFile() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        MediaPlayer.Status status = mediaPlayer.getStatus();

        mediaPlayer.play();
        assertEquals(MediaPlayer.Status.PLAYING,mediaPlayer.getStatus());
    }

    @Test
    void stopFile() {
    }

    @Test
    void pauseFile() {
    }

    @Test
    void updateValues() {
    }

}
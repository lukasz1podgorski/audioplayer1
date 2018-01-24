package sample;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.junit.jupiter.api.Test;
import javafx.scene.media.MediaPlayer.Status;


import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {

    Song song = new Song();
    MediaPlayer mediaPlayer;
    Media media;
    Duration duration;
    Boolean stopRequested = false;

//    @Test
//    void ready() throws InterruptedException {
//
//        new JFXPanel();
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
////                MediaPl
//            }
//        });
//
//        String path = new File("test/sample/03 In the Red.mp3").getAbsolutePath();
//        media = new Media(new File(path).toURI().toString());
//        mediaPlayer = new MediaPlayer(media);
//
//        Thread.sleep(5000);
//
//        Status status = song.getMediaPlayer().getStatus();
//        assertEquals(Status.READY, status);
//
//    }

    @Test
    void play() throws InterruptedException {


        new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                MediaPl
            }
        });

        String path = new File("test/sample/03 In the Red.mp3").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        Thread.sleep(5000);

//
//        mediaPlayer.setOnReady(new Runnable() {
//            @Override
//            public void run() {
//                duration = song.getMediaPlayer().getMedia().getDuration();
//            }
//        });

        mediaPlayer.play();

        Thread.sleep(5000);
        Status status = mediaPlayer.getStatus();

        assertEquals(Status.PLAYING, status);




//
//        String path = new File("test/sample/03 In the Red.mp3").getAbsolutePath();
//        path = path.replace("\\", "/");
//
//        me = new Media(new File(path).toURI().toString());
//        mp = new MediaPlayer(me);
    }


    @Test
    void stop() throws InterruptedException {
        new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                MediaPl
            }
        });

        String path = new File("test/sample/03 In the Red.mp3").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        Thread.sleep(5000);


        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                duration = song.getMediaPlayer().getMedia().getDuration();
            }
        });

        mediaPlayer.play();

        mediaPlayer.stop();

        Thread.sleep(5000);
        Status status = mediaPlayer.getStatus();

        assertEquals(Status.STOPPED, status);

    }

    @Test
    void pause() throws InterruptedException {

        new JFXPanel();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                MediaPl
            }
        });

        String path = new File("test/sample/03 In the Red.mp3").getAbsolutePath();
        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        Thread.sleep(5000);


        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                duration = song.getMediaPlayer().getMedia().getDuration();
            }
        });

        mediaPlayer.play();

        mediaPlayer.pause();

        Thread.sleep(5000);
        Status status = mediaPlayer.getStatus();

        assertEquals(Status.PAUSED, status);


    }

}
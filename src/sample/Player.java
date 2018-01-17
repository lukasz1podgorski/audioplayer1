package sample;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.MapChangeListener;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Player {

    Song song = new Song();
    Controller controller;
    boolean atEndOfSong = false;
    boolean stopRequested = false;
    final boolean repeat = false;
    Duration duration;

    public void listener(){

//        duration = song.getMediaPlayer().getMedia().getDuration();
        song.getMediaPlayer().currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                controller.updateValues();

            }
        });

        song.getMediaPlayer().setOnPlaying(new Runnable() {
            public void run() {
                if (stopRequested) {
//                        mediaPlayer.pause();
                    song.getMediaPlayer().pause();
                    stopRequested = false;
                }
            }
        });

        song.getMediaPlayer().setOnReady(new Runnable() {
            @Override
            public void run() {
                duration = song.getMediaPlayer().getMedia().getDuration();
                controller.updateValues();
            }
        });

        song.getMediaPlayer().setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
        song.getMediaPlayer().setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (!repeat){
                    atEndOfSong=true;
                    stopRequested=true;
                    song.getMediaPlayer().stop();
                }
            }
        });

    }
}

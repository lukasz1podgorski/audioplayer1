package sample;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.MapChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Button btnOpen;
    public Button btnPause;
    public Button btnStop;
    public Button btnPlay;
    public Slider volSlider;
    public Slider timeSlider;
    public ImageView volIMG;
    public Label timeLabel;
    public Label artistLabel;
    public Label titleLabel;
    public Label albumLabel;
    public Label yearLabel;
    public ImageView albumCover;
    FormatTime formatTime = new FormatTime();
    Song song = new Song();
    Player player = new Player();
    Duration duration;
    boolean atEndOfSong = false;
    boolean stopRequested = false;
    final boolean repeat = false;

    public Duration getDuration() {
        return duration;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void openFile(ActionEvent actionEvent) {

        try{
            song.initSong();
            duration = song.getMediaPlayer().getMedia().getDuration();

            song.getMedia().getMetadata().addListener(new MapChangeListener<String, Object>() {
                @Override
                public void onChanged(Change<? extends String, ? extends Object> ch) {
                    if (ch.wasAdded()) {
                        handleMetadata(ch.getKey(), ch.getValueAdded());
                    }
                }
            });

            player.listener();

//            song.getMediaPlayer().currentTimeProperty().addListener(new InvalidationListener() {
//                @Override
//                public void invalidated(Observable observable) {
//                    updateValues();
//
//                }
//            });
//
//            song.getMediaPlayer().setOnPlaying(new Runnable() {
//                public void run() {
//                    if (stopRequested) {
////                        mediaPlayer.pause();
//                        song.getMediaPlayer().pause();
//                        stopRequested = false;
//                    }
//                }
//            });
//
//            song.getMediaPlayer().setOnReady(new Runnable() {
//                @Override
//                public void run() {
//                    duration = song.getMediaPlayer().getMedia().getDuration();
//                    updateValues();
//                }
//            });
//
//            song.getMediaPlayer().setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
//            song.getMediaPlayer().setOnEndOfMedia(new Runnable() {
//                @Override
//                public void run() {
//                    if (!repeat){
//                        atEndOfSong=true;
//                        stopRequested=true;
//                        song.getMediaPlayer().stop();
//                    }
//                }
//            });

            timeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    if (timeSlider.isValueChanging()) {
//                        song.getMediaPlayer().seek(duration.multiply(timeSlider.getValue()/100));
                        song.getMediaPlayer().seek(duration.multiply(timeSlider.getValue()/100));
                    }
                }
            });

//            volSlider.setValue(mediaPlayer.getVolume()*100);
            volSlider.setValue(song.getMediaPlayer().getVolume()*100);
            volSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
//                    mediaPlayer.setVolume(volSlider.getValue()/100);
                    song.getMediaPlayer().setVolume(volSlider.getValue()/100);
                }
            });

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void playFile(ActionEvent actionEvent) {
        song.getMediaPlayer().play();
    }

    public void stopFile(ActionEvent actionEvent) {
        song.getMediaPlayer().stop();
    }

    public void pauseFile(ActionEvent actionEvent) {
        song.getMediaPlayer().pause();
    }

    protected void updateValues() {
        if (timeLabel != null && timeSlider != null && duration != null) {
            Platform.runLater(new Runnable() {
                public void run() {
//                    Duration currentTime = mediaPlayer.getCurrentTime();
                    Duration currentTime = song.getMediaPlayer().getCurrentTime();
                    timeLabel.setText(formatTime.formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                    }
                }
            });
        }
    }

    private void handleMetadata(String key, Object value) {
        if (key.equals("album")) {
            albumLabel.setText(value.toString());
        } else if (key.equals("artist")) {
            artistLabel.setText(value.toString());
        } if (key.equals("title")) {
            titleLabel.setText(value.toString());
        } if (key.equals("year")) {
            yearLabel.setText(value.toString());
        } if (key.equals("image")) {
            albumCover.setImage((Image)value);
        }
    }
}
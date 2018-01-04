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
    MediaPlayer mediaPlayer;
    Media media;
    File musicFile;
    Duration duration;
    boolean atEndOfSong = false;
    boolean stopRequested = false;
    final boolean repeat = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void openFile(ActionEvent actionEvent) {

        try{
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Music Files","*.wav","*.m4a","*.mp3"));
            musicFile = fc.showOpenDialog(null);
            String path = musicFile.getAbsolutePath();
            path = path.replace("\\", "/");

            media = new Media(new File(path).toURI().toString());

            media.getMetadata().addListener(new MapChangeListener<String, Object>() {
                @Override
                public void onChanged(Change<? extends String, ? extends Object> ch) {
                    if (ch.wasAdded()) {
                        handleMetadata(ch.getKey(), ch.getValueAdded());
                    }
                }
            });

            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.stop();
            mediaPlayer.setAutoPlay(false);

            mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    updateValues();
                }
            });

            mediaPlayer.setOnPlaying(new Runnable() {
                public void run() {
                    if (stopRequested) {
                        mediaPlayer.pause();
                        stopRequested = false;
                    }
                }
            });

            mediaPlayer.setOnReady(new Runnable() {
                @Override
                public void run() {
                    duration = mediaPlayer.getMedia().getDuration();
                    updateValues();
                }
            });

            mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    if (!repeat){
                        atEndOfSong=true;
                        stopRequested=true;
                        mediaPlayer.stop();
                    }
                }
            });

            timeSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    if (timeSlider.isValueChanging()) {
                        mediaPlayer.seek(duration.multiply(timeSlider.getValue()/100));
                    }
                }
            });

            volSlider.setValue(mediaPlayer.getVolume()*100);
            volSlider.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volSlider.getValue()/100);
                }
            });

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void playFile(ActionEvent actionEvent) {

        Status status = mediaPlayer.getStatus();
        updateValues();

        if ( status == Status.PAUSED
                || status == Status.READY
                || status == Status.STOPPED)
        {
            if (atEndOfSong) {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                atEndOfSong = false;
            }
            mediaPlayer.play();
            updateValues();
        } else {
            mediaPlayer.pause();
            updateValues();
        }
    }

    public void stopFile(ActionEvent actionEvent) {
        mediaPlayer.stop();
        updateValues();
    }

    public void pauseFile(ActionEvent actionEvent) {
        mediaPlayer.pause();
        updateValues();
    }

    protected void updateValues() {
        if (timeLabel != null && timeSlider != null && duration != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    Duration currentTime = mediaPlayer.getCurrentTime();
                    timeLabel.setText(formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                    }
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int)Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int)Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 -
                    durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds,durationMinutes,
                        durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours,
                        elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",elapsedMinutes,
                        elapsedSeconds);
            }
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
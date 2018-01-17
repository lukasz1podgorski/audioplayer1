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

public class Song {

    public MediaPlayer mediaPlayer;
    public Media media;
    public File musicFile;
    public Duration duration;

    public void initSong() {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Music Files", "*.wav", "*.m4a", "*.mp3"));
        musicFile = fc.showOpenDialog(null);
        String path = musicFile.getAbsolutePath();
        path = path.replace("\\", "/");

        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.stop();
        mediaPlayer.setAutoPlay(false);
        duration = mediaPlayer.getMedia().getDuration();

    }

    public Media getMedia() {
        return media;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public Duration getDuration() {
        return duration;
    }

    //
//    public Media getMedia() {
//        return media;
//    }
//
//    public MediaPlayer getMediaPlayer() {
//        return mediaPlayer;
//    }
}

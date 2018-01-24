package sample;


import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.io.File;

public class Song {

    public static MediaPlayer mediaPlayer;
    public Media media;
    public File musicFile;
    public Duration duration;
    boolean atEndOfSong = false;


    public void initSong() {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Music Files", "*.wav", "*.m4a", "*.mp3"));
        musicFile = fc.showOpenDialog(null);

        String path = musicFile.getAbsolutePath();
        path = path.replace("\\", "/");

        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(false);
        duration = mediaPlayer.getMedia().getDuration();

    }

    public void play(){
        mediaPlayer.play();
    }

    public void stop(){
        mediaPlayer.stop();
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public Media getMedia() {
        return media;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

}

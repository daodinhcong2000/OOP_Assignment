package mrmathami.thegame;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Random;

public class GameMusic {
    private static MediaPlayer startMusic = new MediaPlayer(new Media(GameMusic.class.getResource("/music/start.mp3").toExternalForm()));
    private static MediaPlayer inGameMusic1 = new MediaPlayer(new Media(GameMusic.class.getResource("/music/inGame1.mp3").toExternalForm()));
    private static MediaPlayer inGameMusic2 = new MediaPlayer(new Media(GameMusic.class.getResource("/music/inGame2.mp3").toExternalForm()));
    private static MediaPlayer inGameMusic3 = new MediaPlayer(new Media(GameMusic.class.getResource("/music/inGame3.mp3").toExternalForm()));
    private static MediaPlayer shotMusic = new MediaPlayer(new Media(GameMusic.class.getResource("/music/shot.mp3").toExternalForm()));
    private static MediaPlayer destroyMusic = new MediaPlayer(new Media(GameMusic.class.getResource("/music/destroy.mp3").toExternalForm()));
    private static MediaPlayer loseMusic = new MediaPlayer(new Media(GameMusic.class.getResource("/music/lose.mp3").toExternalForm()));
    private static MediaPlayer winMusic = new MediaPlayer(new Media(GameMusic.class.getResource("/music/winning.mp3").toExternalForm()));
    private static MediaPlayer clickMusic = new MediaPlayer(new Media(GameMusic.class.getResource("/music/click.mp3").toExternalForm()));

    private static final MediaPlayer[] allMusicPlayer = {startMusic, inGameMusic3, inGameMusic2, inGameMusic1, shotMusic, destroyMusic, loseMusic, winMusic, clickMusic};

    public static boolean SFXOn = true;

    public static boolean isAllMute() {
        for (MediaPlayer mediaPlayer : allMusicPlayer) if (!mediaPlayer.isMute()) return false;
        return true;
    }

    public static void setAllMute(boolean value) {
        for (MediaPlayer mediaPlayer : allMusicPlayer) mediaPlayer.setMute(value);
    }

    public static void stopAllMusic() {
        for (MediaPlayer mediaPlayer : allMusicPlayer) mediaPlayer.stop();
    }

    public static void pauseAllMusic() {
        for (MediaPlayer mediaPlayer : allMusicPlayer) mediaPlayer.pause();
    }

    public static void playStartMusic() {
        startMusic.play();
    }

    public static void playInGameMusic() {
        Random random = new Random();
        int r = random.nextInt(3);
        if (r == 0) {
            inGameMusic1.setCycleCount(5);
            inGameMusic1.play();
        }
        else if (r == 1) {
            inGameMusic2.setCycleCount(5);
            inGameMusic2.play();
        }
        else {
            inGameMusic3.setCycleCount(5);
            inGameMusic3.play();
        }
    }

    public static void playShotMusic() {
        if (SFXOn) {
            shotMusic = new MediaPlayer(new Media(GameMusic.class.getResource("/music/shot.mp3").toExternalForm()));
            shotMusic.play();
        }
    }

    public static void playDestroyMusic() {
        if (SFXOn) {
            destroyMusic = new MediaPlayer(new Media(GameMusic.class.getResource("/music/destroy.mp3").toExternalForm()));
            destroyMusic.play();
        }
    }

    public static void playLoseMusic() {
        loseMusic.play();
    }

    public static void playWinMusic() {
        winMusic.play();
    }

    public static void playClickMusic() {
        if (SFXOn) {
            clickMusic = new MediaPlayer(new Media(GameMusic.class.getResource("/music/click.mp3").toExternalForm()));
            clickMusic.play();
        }
    }
}

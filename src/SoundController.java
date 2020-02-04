import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * Created by SLASH on 17/05/2017.
 */
public class SoundController {
    private static MediaPlayer gameMusicPlayer;
    private static MediaPlayer wrongAnswerMusicPlayer;
    private static MediaPlayer gameOverMusic;
    private static MediaPlayer correctAnswerMusicPlayer;
    private static MediaPlayer winningMusic;
    public static void  playBackgroundMusic()
    {   gameMusicPlayer=new MediaPlayer(new Media(new File("backgroundMusic.mp3").toURI().toString()));
        gameMusicPlayer.setCycleCount(10);
        gameMusicPlayer.play();
    }
    public static void  pauseBackgroundMusic()
    {
        gameMusicPlayer.pause();
    }
    public static void  playWrongAnswer()
    {   wrongAnswerMusicPlayer=new MediaPlayer(new Media(new File("wrongAnswer.wav").toURI().toString()));
        wrongAnswerMusicPlayer.play();
    }
    public static void  pauseWrongAnswer()
    {
        wrongAnswerMusicPlayer.pause();
    }
    public static void  playCorrectAnswer()
    {   correctAnswerMusicPlayer=new MediaPlayer(new Media(new File("correctAnswer.mp3").toURI().toString()));
        correctAnswerMusicPlayer.play();
    }
    public static void  pauseCorrectAnswer()
    {
        correctAnswerMusicPlayer.pause();
    }
    public static void  playGameOverMusic()
    {   gameOverMusic=new  MediaPlayer(new Media(new File("gameOver.mp3").toURI().toString()));
        gameOverMusic.play();
    }
    public static void playWinningMusic(){
        winningMusic=new MediaPlayer(new Media(new File("winningsound.mp3").toURI().toString()));
        winningMusic.play();
    }
    public static void  pauseGameOverMusic()
    {
        gameOverMusic.pause();
    }
    public static void handlePause (){
        if (gameMusicPlayer.isMute()) gameMusicPlayer.setMute(false);
        else gameMusicPlayer.setMute(true);
    }
}

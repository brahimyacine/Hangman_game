import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import jdk.nashorn.internal.ir.WhileNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by SLASH on 17/04/2017.
 */
public class Session {
    private int nbMotsRestants;
    private List<Question> questions;
    private List<Question> allQuestions;
    private int score=0;
    private int currentQuestion;
    private Joueur joueur;
    public static MediaPlayer gameMusicPlayer;

    public Session(/*Joueur joueur*/) {
        nbMotsRestants=6;
        score=0;
        currentQuestion=0;
        questions=new ArrayList<>(10) ;
        allQuestions=new ArrayList<>();
        this.joueur=joueur;
    }
    public void launchSession(Stage stage)
    {   Question question;
        String motEntre;
        //Scanner sc=new Scanner(System.in);
        int result=0;

       try{
           this.importWords();
           this.generateWords();
       }
       catch (IOException e)
       {
           e.printStackTrace();
       }
        Controller controller;
        controller=new Controller(stage);
//
        /*String gameMusicFile = "backgroundMusic.mp3";     // For example

        Media gameMusic = new Media(new File(gameMusicFile).toURI().toString());
        gameMusicPlayer = new MediaPlayer(gameMusic);
        gameMusicPlayer.setCycleCount(10);
        gameMusicPlayer.play();*/
        SoundController.playBackgroundMusic();

            controller.control(this,stage);



    }
    public void importWords() throws IOException
    {
        BufferedReader bin=null;
        try
        {
            bin=new BufferedReader(new FileReader("mots.poo"));
            String Line;
            String[] param=new String[3];
            while ((Line=bin.readLine())!=null)
            {
                param=Line.split(";");
                allQuestions.add(new Question(TypeDefinition.valueOf(param[0]),param[1],param[2]));
            }
        }
        finally {
            if(bin!=null) bin.close();
        }
    }
    public void generateWords()
    {
        Collections.shuffle(allQuestions);
        int i=(int)(Math.random()*3);
        questions=allQuestions.subList(i,i+10);
       // questions=allQuestions.subList(0,10);
    }
    public int getNbMotsRestants() {
        return nbMotsRestants;
    }

    public void setNbMotsRestants(int nbMotsRestants) {
        this.nbMotsRestants = nbMotsRestants;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(int currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Question> getAllQuestions() {
        return allQuestions;
    }
    public static void handleVoice (){
        SoundController.pauseBackgroundMusic();
        /*if (gameMusicPlayer.isMute()) gameMusicPlayer.setMute(false);
        else gameMusicPlayer.setMute(true);*/
    }
    public static Stage endSession (){
        //Noyau newNoyau = new Noyau();
       SoundController.pauseBackgroundMusic();
        Stage newStage = new Stage();
        return newStage;
    }

}

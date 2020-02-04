import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mimom on 5/7/2017.
 */

public class Joueur implements Comparable{
    private String pseudo;
    private int highScore;
    private List<Integer> scores;
    private int lastIndex=-1;
    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.scores=new ArrayList<>();
        this.highScore = 0;
    }
    public Joueur(String pseudo,int highScore) {
        this.pseudo = pseudo;
        this.highScore = highScore;
    }
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        if (this.highScore < highScore) this.highScore=highScore;
    }

    public List<Integer> getScores() {
        return scores;
    }

    public void setScores(String[] scores) {

        for (int i=1;i<scores.length;i++)
        {
            this.scores.add(Integer.parseInt(scores[i]));

        }
        Collections.sort(this.scores);

        this.highScore=this.scores.get(this.scores.size()-1);

    }

    public void setScores(List<Integer> scores) {
        this.scores = scores;
    }

    public void saveScore(int score)
    {   if(lastIndex!=-1 && this.scores.indexOf(score)==lastIndex)
        System.out.println("Déja Sauvegardé");
        else
    {
        scores.add(score);
        System.out.println("Score sauvegardé avec succés");
    }
        if(this.highScore<score){
            highScore=score;
        }
        Collections.sort(this.scores);
        lastIndex=this.scores.indexOf(score);
        Noyau.joueurs.refreshScore(this);

    }

    @Override
    public boolean equals(Object obj) {
        Joueur joueur=(Joueur) obj;
        return this.pseudo.equals(joueur.getPseudo());
    }

    @Override
    public String toString() {
        String toreturn="";
        toreturn+=(pseudo+";");
        for (int i:scores)
        {
            toreturn+=i+";";
        }
        toreturn+="\n";
        return toreturn;
    }


    @Override
    public int compareTo(Object joueur) {
        int comparScore =((Joueur)joueur).getHighScore();
        return comparScore-this.highScore;
    }
}

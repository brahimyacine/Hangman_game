/**
 * Created by mimom on 5/7/2017.
 */
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Executable;
import java.util.*;

public class Joueurs {
    private List<Joueur> joueurs;

    public Joueurs() {
        this.joueurs = new ArrayList<>();
        loadJoueurs();
    }
    public void loadJoueurs()
    {
        File in=new File("joueurs.txt");
        BufferedReader bin=null;
        try
        {
            bin=new BufferedReader(new FileReader(in));
            String [] infoJoueur;
            String line;
            while((line=bin.readLine())!=null)
            {
                if (line.length()>1){
                    infoJoueur=line.split(";");
                    System.out.println(Arrays.toString(infoJoueur));
                    Joueur joueur;
                    joueur=new Joueur(infoJoueur[0]);
                    joueur.setScores(infoJoueur);
                    joueurs.add(joueur);

                }

            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try{
                if(bin!=null) bin.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }
    public void refreshScore(Joueur joueur)
    {
        int i=joueurs.indexOf(joueur);
        if(i>=0) {

                joueurs.get(i).setHighScore(joueur.getHighScore());
                joueurs.get(i).setScores(joueur.getScores());
        }

    }
    public void saveJoueurs()
    {

        File in=new File("joueurs.txt");
        BufferedWriter bout=null;
        try
        {
            bout=new BufferedWriter(new FileWriter(in));
            Iterator<Joueur> it=joueurs.iterator();
            Joueur joueur;
            while(it.hasNext())
            {
                joueur=it.next();
                bout.write(joueur.toString());
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try{
                if(bout!=null) bout.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    public void AddJoueur (Joueur joueur){
        File in=new File("joueurs.txt");
        BufferedWriter bout=null;
        try
        {
            bout=new BufferedWriter(new FileWriter(in,true));
            Iterator<Joueur> it=joueurs.iterator();
            Joueur jou;
            while(it.hasNext())
            {
                jou=it.next();
//                bout.write(joueur.toString());
            }
            bout.write(joueur.toString());
            bout.newLine();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally {
            try{
                if(bout!=null) bout.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }
    }


    public void logIn(Joueur joueur)
    {
        int i=joueurs.indexOf(joueur);
        if(i<0)
        {
            joueurs.add(joueur);
        }
        else{
            joueur.setHighScore(joueurs.get(i).getHighScore());
            joueur.setScores(joueurs.get(i).getScores());

        }
    }

    public int exsiste (Joueur joueur){
        int i=joueurs.indexOf(joueur);
        return i;
    }

    public void AddToList (Joueur joueur){
        joueurs.add(joueur);
    }

    public  List<Joueur> triePlayers (){
        List<Joueur> newlist = this.joueurs;
        Collections.sort(newlist);
        return newlist;
    }

    public List<Joueur> getJoueurs() {
        return joueurs;
    }
}

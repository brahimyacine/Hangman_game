//import com.sun.java.swing.plaf.windows.TMSchema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by SLASH on 17/04/2017.
 */
public class Question {

    private TypeDefinition type;
    private String question;
    private List<Case> cases;
    private String reponse;
    private int score=0;
    private int coefficient;

    public Question(TypeDefinition type, String question, String reponse) {
        this.type = type;
        this.cases = new ArrayList<Case>();
        this.reponse = reponse;
        this.genererCases();

       /* this.genererMot(reponse);*/
        this.question = question;

    }

    public void CalculerScore() {
        int nbrCaseMalus = 0,i=1,
                malus = 0;

        for (Case case1 : cases) {
            this.score += case1.getScore();

            if (case1 instanceof Malus) {
                nbrCaseMalus++;
                malus += ((Malus) case1).calculerMalus();
            }
            i++;
        }
        switch (type)
        {
            case ANTONYME:
                            break;
            case SYNONYME: this.score*=2;
                            break;
            case DEFINITION: this.score*=3;
                            break;
        }
        if (nbrCaseMalus > 5) {
            this.score += malus;
        }


    }

    public void genererCases() {
        int nbProposition = (int) (Math.random() * 4),
            nbZeroChance = (int) (Math.random() * 4);

        for (int i = 0; i < nbZeroChance; i++) {

            this.cases.add(new ZeroCase());


        }
        for (int i = 0; i < nbProposition; i++) {
            this.cases.add(new Proposition());
        }
        for (int i = 0; i < reponse.length()-(nbZeroChance+nbProposition); i++) {
            this.cases.add(new MultiCase());
        }
        Collections.shuffle(cases);
        setRealCaractere(); // on remplie les vrai charactere !
    }

    public void setRealCaractere() {
        Case case1;
        for (int i = 0; i < reponse.length(); i++) {
            case1 = cases.get(i);
            case1.setRealLettre(reponse.charAt(i));
        }

    }

    public void genererMot(String mot) {
        Case case1;
        for (int i = 0; i < mot.length(); i++) {
            case1 = cases.get(i);
            case1.setLettre(mot.charAt(i));
        }

    }

    public int isValide(String motEntre) {
        this.genererMot(motEntre);

        boolean valide = true;
        int i = 0;
        while (i < reponse.length()) {
            if (cases.get(i).getLettre() != reponse.charAt(i)) {
                if (cases.get(i) instanceof ZeroCase) {

                    return 0;//passer au mot suivant
                }
                else {
                    if (((MultiCase) cases.get(i)).getNbChances() > 0)//Il lui reste des chances dans la case multichances
                    {
                        valide = false;
                        ((MultiCase) cases.get(i)).decrNbChances();
                        cases.get(i).getTextField().getStyleClass().add("error");
                    } else {
                        return 0;
                    }
                }
            }
            else
            {

                    if (cases.get(i) instanceof MultiCase)//Il lui reste des chances dans la case multichances
                    {

                        cases.get(i).getTextField().getStyleClass().remove("error");

                    }

            }
            i++;

        }
        if (valide) return 1;
        else return -1;//il lui reste des chances


    }//retourne 1 si le mot est valide 0 s'il n'est pas valide -1 s'il reste des chances
    public void afficherTypeCase()  {
        for(int i=0;i<cases.size();i++)
        {   System.out.print("Case"+i+": ");
            if(cases.get(i) instanceof MultiCase)
            {
                System.out.println("MultiCases: "+"NbChancesRestantes:"+((MultiCase)cases.get(i)).getNbChances());
            }
            else
            {
                if(cases.get(i) instanceof Proposition)
                {
                    System.out.println("Proposition: "+((Proposition)cases.get(i)).getPropositions());
                }
                else{
                    System.out.println("ZeroChances");
                }
            }
        }
    }
    public void afficherQuestion() {
        System.out.println(type + ":" + question);
    }

    public TypeDefinition getType() {
        return type;
    }

    public void setType(TypeDefinition type) {
        this.type = type;
    }

    public List<Case> getCases() {
        return cases;
    }

    public void setCases(List<Case> cases) {
        this.cases = cases;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getQuestion() {
        return question;
    }

    public String getReponse() {
        return reponse;
    }
}




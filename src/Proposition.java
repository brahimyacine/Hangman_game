import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by SLASH on 17/04/2017.
 */
public class Proposition extends ZeroCase implements Malus {

    private String propositions ="";



    public Proposition() {
        super.getTextField().getStyleClass().remove("zeroCase");
        super.getTextField().getStyleClass().add("proposition");

    }


    @Override
    public int calculerMalus() {
        if (this.getRealLettre()!=this.getLettre() && this.getLettre()!='²') return -1;
        else return 0;
    }

    public int getScore(){

        if (this.getLettre()==this.getRealLettre() && this.getLettre()!='²') return 2;
        else
        {

            return 0;
        }


    }

    @Override
    public void setRealLettre(char realLettre) {
        super.setRealLettre(realLettre);
        this.propositions=generateProp(realLettre);

    }


    public String generateProp ( char realLettre ){
        List <String> letters ;
        String propo="";
        String proposition = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        int i=0;

        List<String> prp =  Arrays.asList(proposition.split(""));
        Collections.shuffle(prp);
        letters= prp.subList(0,4);
        realLettre=Character.toUpperCase(realLettre);
        while (i<4)
        {
            propo+=letters.get(i);
            i++;
        }
        if (propo.indexOf(realLettre)==-1){
            int pos = (int) (Math.random() * 4);
            char [] tab = propo.toCharArray();
            tab[pos]=realLettre;
            propo=String.valueOf(tab);
        }
        return propo;

    }

    public String getPropositions() {
        return propositions;
    }

    public void setPropositions(String propositions) {
        this.propositions = propositions;
    }


}

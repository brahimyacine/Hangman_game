/**
 * Created by SLASH on 17/04/2017.
 */
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;

public class MultiCase extends Case implements Malus {
    private int nbChances = 3;

    public MultiCase() {
            super.getTextField().getStyleClass().add("multicase");

        }

    public int getNbChances() {
        return nbChances;
    }

    public void setNbChances(int nbChances) {
        this.nbChances = nbChances;
    }

    @Override
    public int calculerMalus() {
        if (this.nbChances==3) return 0;
        else {
            if( nbChances == 2) return -2;
            else  if (nbChances == 1 )return -4;
            else return -6;
        }
    }

    @Override
    public int getScore() {
        if (this.getRealLettre()==this.getLettre()  && this.getLettre()!='²')
        {

            return 1;
        }
        else
        {
            return 0;
        }
    }

    @Override
    public int isValide(char c) {

            if (nbChances>0)
            {
                if (c==super.getRealLettre())
                {
                    super.result=1;
                    super.getTextField().getStyleClass().remove("error");
                }
                else {
                    super.result=-1;
                    if(nbChances>2)
                    super.getTextField().getStyleClass().add("error");
                    nbChances--;
                    if(nbChances==0)result=0;
                }
            }
            else
            {
                result=0;
                super.getTextField().getStyleClass().remove("error");
            }

        return result;
    }

    public void decrNbChances(){
        this.nbChances -= 1;
    }
}

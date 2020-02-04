/**
 * Created by SLASH on 17/04/2017.
 */
public class ZeroCase extends Case {

    public ZeroCase() {
        super.getTextField().getStyleClass().add("zeroCase");


    }

    @Override
    public int getScore() {
        if ( this.getLettre() == this.getRealLettre()  && this.getLettre()!='²')
        {
            return 3 ;
        }
        else return 0;
    }
    @Override
    public int isValide(char c) {


                if (c==super.getRealLettre())
                {
                    super.result=1;
                    super.getTextField().getStyleClass().remove("error");
                }
                else {
                    super.result=0;

                }



        return result;
    }
}

import javafx.scene.control.TextField;

/**
 * Created by SLASH on 17/04/2017.
 */
public abstract class Case {
    private char lettre='²';
    private char realLettre;
    private TextField textField=new TextField();
    private TextField nextTextField;
    protected static int result;
    public char getLettre() {
        return lettre;
    }

    public void setLettre(char lettre) {
        this.lettre = lettre;
    }

    public char getRealLettre() {
        return realLettre;
    }

    public void setRealLettre(char realLettre) {
        this.realLettre = realLettre;

    }

    public abstract int getScore ();
    public abstract int isValide(char c);
    public TextField getTextField() {
        return textField;
    }

    public static int getResult() {
        return result;
    }

    public void setTextField(TextField textField) {

        this.textField = textField;
    }

    public TextField getNextTextField() {
        return nextTextField;
    }

    public void setNextTextField(TextField nextTextField) {
        this.nextTextField = nextTextField;
    }
}

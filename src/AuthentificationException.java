/**
 * Created by SLASH on 17/04/2017.
 */
public class AuthentificationException extends Exception {

    @Override
    public void printStackTrace() {
        System.out.println("Erreur d'authentification le psseudonyme entré n'est pas valide:le premier caractère doit etre" +
                "une lettre. ");
    }
}

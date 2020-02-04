import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by SLASH on 17/04/2017.
 */
public class Noyau extends Application{

    static Stage window;
    static Scene scene;
    static Stage sauv;
    static VBox highScores;
    static VBox userScores;
    static int ryma;
    static Joueurs joueurs=new Joueurs();
    public static void main (String[] args)
    {
        launch(args);
    }
    //Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Acceuil");
        primaryStage.setResizable(false);
        window = primaryStage;
        sauv = primaryStage;

        // ------------- here its like a div where we put the element like the title and the buttons of inscription
        GridPane grid = new GridPane();
        grid.setId("pane");
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(200, 120, 0, 25));

        //------------- here the welcome text
        Label scenetitle = new Label(" BIENVENUE");
        scenetitle.setFont(Font.font("KG Broken Vessels Sketch", FontWeight.NORMAL,40));
        scenetitle.getStyleClass().add("bienvenu");
        //scenetitle.setFill(Color.WHITE);
        grid.add(scenetitle, 1, 2, 2, 1);
        scenetitle.setAlignment(Pos.CENTER);

        //------------- the label of the user -------
        Label userName = new Label("User Name :");
        grid.add(userName, 0, 3);

        //-------------the text area of the user ---------------
        TextField userTextField = new TextField();
        userTextField.getStyleClass().add("userNameInput");
        grid.add(userTextField, 1, 3);


        //------------the button of sign in ---------*/
        Button btn = new Button("SIGN IN");
        Button btn1 = new Button("SIGN UP");
        Button helpButton=new Button("HELP ?");
        Button quitButton=new Button("QUIT");
        btn.getStyleClass().add("welcomeButton");
        btn1.getStyleClass().add("welcomeButton");
        helpButton.getStyleClass().add("helpButton");
        quitButton.getStyleClass().add("helpButton");
        Hover hover1=new Hover(btn,0);Hover hover2=new Hover(btn);
        handleHover(hover1,btn,1);handleHover(hover2,btn,2);
        Hover hover3=new Hover(btn1,0);Hover hover4=new Hover(btn1);
        handleHover(hover3,btn1,1);handleHover(hover4,btn1,2);
        Hover hover5=new Hover(helpButton,0);Hover hover6=new Hover(helpButton);
        handleHover(hover5,helpButton,1);handleHover(hover6,helpButton,2);
        Hover hover7=new Hover(quitButton,0);Hover hover8=new Hover(quitButton);
        handleHover(hover7,quitButton,1);handleHover(hover8,quitButton,2);
        HBox hbBtn = new HBox(9);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.setSpacing(20);
        hbBtn.getChildren().addAll(btn, btn1);
        grid.add(hbBtn, 1, 4);
        VBox vBox=new VBox(10);
        vBox.getChildren().addAll(helpButton,quitButton);
        vBox.setAlignment(Pos.CENTER);
        grid.add(vBox,1,5);
        quitButton.setOnAction(e->{
            joueurs.saveJoueurs();
            primaryStage.close();
        });
        helpButton.setOnAction(e->{
            Stage helpStage=new Stage();
            helpStage.setTitle("Help");
            helpStage.setScene(createHelpScene());
            helpStage.show();
        });

        StackPane layout2 = new StackPane();
        Button btnsc = new Button(" return to acceuil page !");
        layout2.getChildren().add(btnsc);

        Scene scene2 = new Scene(layout2, 500, 500);
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        //Joueurs joueurs = new Joueurs();
        highScores=this.createHighScores(joueurs.getJoueurs());
        Session session=new Session();

        userTextField.setOnKeyReleased(event -> {
            String pseudo = new String();
            Joueur joueur;
            if (event.getCode() == KeyCode.ENTER ){


                /*actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                actiontarget.setText("Sign in button pressed");*/
                Image img = new Image("/correct.png");
                if (userTextField.getText().trim().isEmpty()) {
                    /*Alert fail = new Alert(Alert.AlertType.ERROR);
                    fail.setHeaderText("failure");
                    fail.setHeight(100);
                    fail.setContentText("please tape ur informations to continue");
                    fail.showAndWait();*/
                    ImageView img1 = new ImageView();
                    img1.setImage(img);
                    Notifications not = Notifications.create()

                            .title("sign in erreur !")
                            .text("  please input your information to continue.")
                            .graphic(img1)
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.CENTER)
                            .onAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    System.out.println("clicked on notification");
                                }
                            });
                    not.darkStyle();
                    not.showWarning();
                    // not.position(Pos.valueOf(this.getClass().getResource("style.css").toExternalForm()));


                } else {
                   /* Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Succes");
                    alert.setContentText("Account succesfully created!");
                    alert.showAndWait();*/
                    boolean continu = true;

                    try {

                        pseudo = userTextField.getText();

                        if (pseudo.toLowerCase().charAt(0) < 97 || pseudo.toLowerCase().charAt(0) > 122)//la premier caractere n'est pas une lettre
                        {
                            /*System.out.print("Here");
                            Alert fail = new Alert(Alert.AlertType.ERROR);
                            fail.setHeaderText("failure");
                            fail.setHeight(100);
                            fail.setContentText("the syntaxe of ur name is not valide");
                            fail.showAndWait();*/
                            //Image img = new Image("correct.png",80,80,true,true);
                            Notifications not = Notifications.create()

                                    .title("  failure !")
                                    .text("  the syntaxe of ur name is not valide")
                                    .graphic(null)
                                    .hideAfter(Duration.seconds(5))
                                    .position(Pos.CENTER)
                                    .onAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            System.out.println("clicked on notification");
                                        }
                                    });
                            not.darkStyle();
                            not.showError();
                            continu = false;
                            //userTextField.setStyle("-fx-background-color:red;");
                            throw new AuthentificationException();

                        }

                    } catch (AuthentificationException ae) {
                        ae.printStackTrace();
                    }


                    if (continu) {
                        joueur = new Joueur(pseudo);
                        //joueurs.logIn(joueur);
                        if (joueurs.exsiste(joueur) < 0) {
                           /* Alert fail = new Alert(Alert.AlertType.ERROR);
                            fail.setHeaderText("failure");
                            fail.setHeight(100);
                            fail.setContentText("ur name doesnt existe");
                            fail.showAndWait();*/
                            Notifications not = Notifications.create()

                                    .title("  failure !")
                                    .text("  ur name doesnt existe")
                                    .graphic(null)
                                    .hideAfter(Duration.seconds(5))
                                    .position(Pos.CENTER)
                                    .onAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            System.out.println("clicked on notification");
                                        }
                                    });
                            not.darkStyle();
                            not.showError();
                        } else {
                            // Session session=null;
                            joueurs.logIn(joueur);
                            userScores=this.creatUserScores(joueur);
                            session.setJoueur(joueur);
                            // session=new Session(joueur);

                            primaryStage.setTitle("Hangman");
                            session.launchSession(primaryStage);
                            // window.setScene(scene2);
                        }
                    }

                }

            }
        });

        btn.setOnAction(new EventHandler<ActionEvent>() {
            String pseudo = new String();


            Joueur joueur;

            @Override
            public void handle(ActionEvent e) {

                /*actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                actiontarget.setText("Sign in button pressed");*/
                Image img = new Image("/correct.png");
                if (userTextField.getText().trim().isEmpty()) {
                    /*Alert fail = new Alert(Alert.AlertType.ERROR);
                    fail.setHeaderText("failure");
                    fail.setHeight(100);
                    fail.setContentText("please tape ur informations to continue");
                    fail.showAndWait();*/
                    ImageView img1 = new ImageView();
                    img1.setImage(img);
                    Notifications not = Notifications.create()

                            .title("sign in erreur !")
                            .text("  please input your information to continue.")
                            .graphic(img1)
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.CENTER)
                            .onAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    System.out.println("clicked on notification");
                                }
                            });
                    not.darkStyle();
                    not.showWarning();
                    // not.position(Pos.valueOf(this.getClass().getResource("style.css").toExternalForm()));


                } else {
                   /* Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Succes");
                    alert.setContentText("Account succesfully created!");
                    alert.showAndWait();*/
                    boolean continu = true;

                    try {

                        pseudo = userTextField.getText();

                        if (pseudo.toLowerCase().charAt(0) < 97 || pseudo.toLowerCase().charAt(0) > 122)//la premier caractere n'est pas une lettre
                        {
                            /*System.out.print("Here");
                            Alert fail = new Alert(Alert.AlertType.ERROR);
                            fail.setHeaderText("failure");
                            fail.setHeight(100);
                            fail.setContentText("the syntaxe of ur name is not valide");
                            fail.showAndWait();*/
                            //Image img = new Image("correct.png",80,80,true,true);
                            Notifications not = Notifications.create()

                                    .title("  failure !")
                                    .text("  the syntaxe of ur name is not valide")
                                    .graphic(null)
                                    .hideAfter(Duration.seconds(5))
                                    .position(Pos.CENTER)
                                    .onAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            System.out.println("clicked on notification");
                                        }
                                    });
                            not.darkStyle();
                            not.showError();
                            continu = false;
                            //userTextField.setStyle("-fx-background-color:red;");
                            throw new AuthentificationException();

                        }

                    } catch (AuthentificationException ae) {
                        ae.printStackTrace();
                    }


                    if (continu) {
                        joueur = new Joueur(pseudo);
                        //joueurs.logIn(joueur);
                        if (joueurs.exsiste(joueur) < 0) {
                           /* Alert fail = new Alert(Alert.AlertType.ERROR);
                            fail.setHeaderText("failure");
                            fail.setHeight(100);
                            fail.setContentText("ur name doesnt existe");
                            fail.showAndWait();*/
                            Notifications not = Notifications.create()

                                    .title("  failure !")
                                    .text("  ur name doesnt existe")
                                    .graphic(null)
                                    .hideAfter(Duration.seconds(5))
                                    .position(Pos.CENTER)
                                    .onAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            System.out.println("clicked on notification");
                                        }
                                    });
                            not.darkStyle();
                            not.showError();
                        } else {
                           // Session session=null;
                            joueurs.logIn(joueur);
                            session.setJoueur(joueur);
                           // session=new Session(joueur);
                            userScores=creatUserScores(joueur);

                            primaryStage.setTitle("Hangman");
                            session.launchSession(primaryStage);
                           // window.setScene(scene2);
                        }
                    }

                }
            }
        });

        btn1.setOnAction(new EventHandler<ActionEvent>() {

            String pseudo = new String();


            Joueur joueur;

            @Override
            public void handle(ActionEvent e) {

                /*actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
                actiontarget.setText("Sign in button pressed");*/
                if (userTextField.getText().trim().isEmpty()) {
                    /*Alert fail = new Alert(Alert.AlertType.ERROR);
                    fail.setHeaderText("failure");
                    fail.setHeight(100);
                    fail.setContentText("please tape ur informations to continue");
                    fail.showAndWait();*/
                    Image img = new Image("correct.png",80,80,true,true);
                    Notifications not = Notifications.create()

                            .title("  SIGN in Erreur !")
                            .text("   please input your information to continue.")
                            .graphic(new ImageView(img))
                            .hideAfter(Duration.seconds(5))
                            .position(Pos.CENTER)
                            .onAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    System.out.println("clicked on notification");
                                }
                            });
                    not.darkStyle();
                    not.showWarning();
                } else {
                   /* Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Succes");
                    alert.setContentText("Account succesfully created!");
                    alert.showAndWait();*/
                    boolean continu = true;


                    try {

                        pseudo = userTextField.getText();

                        if (pseudo.toLowerCase().charAt(0) < 97 || pseudo.toLowerCase().charAt(0) > 122)//la premier caractere n'est pas une lettre
                        {
                            /*System.out.print("Here");

                            Alert fail = new Alert(Alert.AlertType.ERROR);
                            fail.setHeaderText("failure");
                            fail.setHeight(100);
                            fail.setContentText("the syntaxe of ur name is not valide");
                            fail.showAndWait();*/
                            //Image img = new Image("correct.png",80,80,true,true);
                            Notifications not = Notifications.create()

                                    .title("  failure !")
                                    .text("   the syntaxe of ur name is not valide")
                                    .graphic(null)
                                    .hideAfter(Duration.seconds(5))
                                    .position(Pos.CENTER)
                                    .onAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            System.out.println("clicked on notification");
                                        }
                                    });
                            not.darkStyle();
                            not.showWarning();
                            continu = false;
                            throw new AuthentificationException();
                        }

                    } catch (AuthentificationException ae) {
                        ae.printStackTrace();
                    }


                    if (continu) {
                        joueur = new Joueur(pseudo);
                        //joueurs.logIn(joueur);
                        if (joueurs.exsiste(joueur) < 0) {
                            joueurs.AddJoueur(joueur);
                            /*Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Succes");
                            alert.setContentText("Account succesfully created!");
                            alert.showAndWait();*/
                            Image img = new Image("correct.png",80,80,true,true);
                            Notifications not = Notifications.create()

                                    .title("  Succes !")
                                    .text("   Account succesfully created!")
                                    .graphic(new ImageView(img))
                                    .hideAfter(Duration.seconds(5))
                                    .position(Pos.CENTER)
                                    .onAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            System.out.println("clicked on notification");
                                        }
                                    });
                            not.darkStyle();
                            not.show();
                            joueurs.AddToList(joueur);
//                            Joueurs joueurs=new Joueurs();
                        } else {
                            //window.setScene(scene2);
                            Alert fail = new Alert(Alert.AlertType.ERROR);
                            fail.setHeaderText("failure");
                            fail.setHeight(100);
                            fail.setContentText("this name existe please test another one");
                            fail.showAndWait();
                        }

                    }

                }
            }
        });

        String url = "background2.png" ;
        Image img = new Image(url);
        scene = new Scene(grid, 1020 , 570);
        BackgroundImage bgImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        grid.setBackground(new Background(bgImg));
        //Scene scene = new Scene(grid, 400, 580);

        primaryStage.setScene(scene);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.show();
        btnsc.setOnAction(e -> window.setScene(scene));
        primaryStage.setOnCloseRequest(e->{
            joueurs.saveJoueurs();
            System.out.println("byebye");
                }
        );

    }
    private class Hover {
        private TranslateTransition tt;

        public Hover(Button label,int a) {
            tt = new TranslateTransition(Duration.millis(100), label);
            tt.setFromX(0f);
            tt.setFromY(0f);
            tt.setByX(5f);
            tt.setByY(5f);
            tt.setCycleCount(1);

        }
        public Hover(Button label) {
            tt = new TranslateTransition(Duration.millis(100), label);
            tt.setFromX(0f);
            tt.setFromY(0f);
            tt.setCycleCount(1);

        }

        public void hover() {
            tt.playFromStart();
        }
    }
    private void handleHover(Hover hover,Button btn,int a)
    {   if(a==1)
    {
        btn.setOnMouseEntered(e->{

            hover.hover();
        });
    }
      else{
        btn.setOnMouseExited(e->{

            hover.hover();
        });
    }

    }

    public  void changeScene (Stage newStage){
        //window.setScene(scene); ;
        window=newStage;
        //window=sauv;
        window.setScene(scene);
        /*this.start();start(window);*/

    }
    public VBox createHighScores (List<Joueur> joueurs){
        VBox toreturn = new VBox();
        Label header = new Label("Top 10 Best Scores ! \n ");
        toreturn.getChildren().add(header);
        Collections.sort(joueurs);
        joueurs=joueurs.subList(0,10);

        toreturn.setAlignment(Pos.CENTER);
        int i = joueurs.size();
        for (int k =0 ;k<i;k++){
            Label newlabel = new Label("   "+(k+1)+"- "+joueurs.get(k).getPseudo()+" : "+joueurs.get(k).getHighScore());
            if (k==0) newlabel.setGraphic(new ImageView(new Image("medal1.png",30,30,true,true)));
            if (k==1) newlabel.setGraphic(new ImageView(new Image("medal2.png",30,30,true,true)));
            if (k==2) newlabel.setGraphic(new ImageView(new Image("medal3.png",30,30,true,true)));
            toreturn.getChildren().add(newlabel);
        }
        return toreturn;
    }
    public VBox creatUserScores (Joueur joueur){
        VBox toreturn = new VBox(10);
        Label header = new Label("  Best "+joueur.getPseudo()+" Scores !");
        header.getStyleClass().add("headerScore");
        ImageView imgv=new ImageView();
        Image cupImage=new Image("cup.png");
        imgv.setImage(cupImage);
        imgv.setFitHeight(50);
        imgv.setPreserveRatio(true);
        HBox hBox=new HBox(1);
        hBox.getChildren().addAll(imgv,header);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        toreturn.getChildren().add(hBox);
        Collections.sort(joueur.getScores());

        toreturn.setAlignment(Pos.TOP_CENTER);
        int i = joueur.getScores().size();

        for (int k =i-1 ;k>0;k--){
            Label newlabel = new Label(""+joueur.getScores().get(k));

            toreturn.getChildren().add(newlabel);

        }
        return toreturn;
    }
    private Scene createHelpScene()
    {   BorderPane layout=new BorderPane();
        Label label=new Label("Le Pendu");
        label.getStyleClass().add("helpLabel");
        layout.setTop(label);
        layout.setAlignment(label,Pos.CENTER);
        Text text=new Text("    Le pendu est un jeu éducatif qui fait deviner des " +
                "mots à un joueur  en lui fournissant trois types d’indications: définition,\n synonyme ou antonyme." +
                "Durant une session de jeu, dix (10) mots  sont proposés au joueur qui essaye de les trouver\n" +
                " séquentiellement  De ce fait, un joueur ne peut passer au mot suivant que s’il " +
                "réussit ou échoue à trouver le mot en cours.\n\n"+
                "   Au commencement du jeu, le joueur voit s’afficher une liste de mots initialement cachés. Chaque" +
                "mot est composé \n d’un ensemble de lettres dissimulées dans des cases. En cliquant sur une case," +
                "le joueur doit pouvoir choisir la lettre qu’il désire \nlui affecter. Nous identifions trois types de " +
                "cases: case zéroChance, case multiChances et case propositions. \n  Une case zéroChance (Grise) ne tolère aucune " +
                "erreur alors qu’une case multiChances (Verte) permet au joueur de se tromper\n deux fois. " +
                " Une case propositions(Jaune) propose au joueur de " +
                "choisir parmi quatre (4) lettres possibles mais ne tolère aucune \nerreur une fois le choix effectué. "+
                "Sur la liste des dix mots proposés durant une session, le joueur peut en rater six au maximum.\n \n" +
                "Le score est calculé selon le type de l’indication, le type des cases composant le mot et le nombre" +
                "de chances consommées \npar le joueur. Le calcul se fait comme suit :\n" +
                "● Une définition a un coefficient de trois (3), un synonyme a un coefficient de deux (2) et " +
                "un antonyme a un coefficient de un (1).\n" +
                "● Une lettre retrouvée dans une case zéroChance vaut trois (3) points.\n" +
                "● Une lettre retrouvée dans une case propositions vaut deux (2) points.\n" +
                "● Une lettre retrouvée dans une case multiChances vaut un (1) point.\n\n" +
                "En plus des points marqués, le joueur peut être sanctionné par des malus si le nombre de cases " +
                "multiChances et propositions \n est supérieur à cinq (5). Dans ce cas, le joueur perd un (1) point s’il" +
                "échoue à trouver une lettre sur une case propositions\n et deux (2) points à chaque fois qu’il échoue à " +
                "la trouver sur une case multiChances. \n"+"  Le joueur peut également Sauvegarder son score actuel , voir ses meilleurs "+
                "scores ou son classement parmi tous les joueurs \net ceci  dans le slider Menu."
        );
        text.setFill(Color.WHITE);
        text.getStyleClass().add("helpText");
        layout.setCenter(text);
        String url = "board.png" ;
        Image img = new Image(url);
        BackgroundImage bgImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        layout.setBackground(new Background(bgImg));
        Scene scene=new Scene(layout,1020,570);
        scene.getStylesheets().add("style.css");
        return scene;
    }

}

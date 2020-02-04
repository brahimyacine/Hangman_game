import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

/**
 * Created by SLASH on 04/05/2017.
 */
public class Controller {
    private Stage stage;
    private View view;
    /* private Question question;*/
    public static int result=0;
    public static Question question;
    public int iCurrentCase=0;
    public String userName;
    public static Popup popup;
    public static Tooltip tooltip;
   // private String prop;
    public Controller( Stage stage/*,Question question,int score*/) {
        //this.view = new View(stage,question,score);
        //this.question = question;
        this.stage=stage;
        iCurrentCase=0;
    }



    public void control(Session session, Stage stage) {
        List<Question> questions = session.getQuestions();
        question = questions.get(session.getCurrentQuestion());

        //int best = session.getJoueur().getHighScore();

        Case currentCase;

        if(iCurrentCase==0) view = new View(stage,session.getJoueur(), questions.get(session.getCurrentQuestion()), session.getScore(),6-session.getNbMotsRestants(),session.getCurrentQuestion()+1);

        currentCase=question.getCases().get(iCurrentCase);
        disable(view,currentCase.getTextField());
        popup=createPopUp(currentCase);
        tooltip = new Tooltip();

       /* Tooltip tooltip ;
        tooltip = this.generateToolTip(currentCase,((Proposition) currentCase).getPropositions());*/
        if(currentCase instanceof MultiCase)
        {

            popup.show(stage);
        }
        if (currentCase instanceof Proposition){
            List<String> prp =  Arrays.asList(((Proposition) currentCase).getPropositions().split(""));
            tooltip.setText(
                    "\nLes propositions sont :\n" +
                            " "+prp.get(0)+" , "+prp.get(1)+" , "+prp.get(2)+" , "+prp.get(3)
            );
           // ddd=true;
            TextField pf = currentCase.getTextField();
            Bounds boundInScreen = pf.localToScreen(pf.getBoundsInLocal());
                tooltip.show( pf, //
                        // popup tooltip on the right, you can adjust these values for different positions
                        boundInScreen.getMaxX() - 80 , //
                        boundInScreen.getMaxY()- 150);
            //tooltip = this.generateToolTip(currentCase,((Proposition) currentCase).getPropositions(),ddd);
            //currentCase.getTextField().setTooltip(tooltip);
        }





        currentCase.getTextField().setOnKeyReleased(event -> {
            Shaker shaker;
            shaker=new Shaker(currentCase.getTextField());
            // disable(view,currentCase.getTextField());
            if(event.getCode().isLetterKey()|| event.getText().charAt(0)=='é' || event.getText().charAt(0)=='è') {
                currentCase.getTextField().setText(event.getText().toUpperCase());

                result = currentCase.isValide(event.getText().toLowerCase().charAt(0));
                currentCase.setLettre(event.getText().toLowerCase().charAt(0));
                if (result == 0) {
                    /*String wrongAnswerMusic = "wrongAnswer.wav";     // For example
                    Media wrongAnswer = new Media(new File(wrongAnswerMusic).toURI().toString());
                    MediaPlayer wrongAnswerMusicPlayer = new MediaPlayer(wrongAnswer);
                    wrongAnswerMusicPlayer.play();*/
                    SoundController.playWrongAnswer();
                    popup.hide();
                    tooltip.hide();
                    shaker.shake();
                    iCurrentCase = 0;

                    System.out.println("Vous vous etes trompé");
                    System.out.println("la vraie réponse était: " + question.getReponse());

                    Alert fail = new Alert(Alert.AlertType.CONFIRMATION);

                    fail.setHeight(100);
                    fail.setContentText(" Your answer is Wrong , the right answer is : "+ question.getReponse());
                    fail.setTitle("incorrect");
                    fail.setHeaderText(null);
                    fail.setGraphic(new ImageView(new Image("wrong.png",60,60,true,true)));
                    fail.setOnShowing(event1 -> {Timeline idlestage = new Timeline( new KeyFrame( Duration.seconds(2), new EventHandler<ActionEvent>()
                    {

                        @Override
                        public void handle( ActionEvent event )
                        {
                            fail.setResult(ButtonType.CLOSE);
                            fail.hide();
                        }
                    } ) );

                        SoundController.playWrongAnswer();
                        idlestage.setCycleCount( 2 );
                        idlestage.play();

                    });
                    fail.showAndWait();


                    session.setCurrentQuestion(session.getCurrentQuestion() + 1);
                    question.CalculerScore();
                    session.setScore(session.getScore() + question.getScore());
                    session.setNbMotsRestants(session.getNbMotsRestants() - 1);
                    if (session.getNbMotsRestants() > 0 && session.getCurrentQuestion() < 10) {
                        view = new View(stage, session.getJoueur(),questions.get(session.getCurrentQuestion()), session.getScore(),6-session.getNbMotsRestants(),session.getCurrentQuestion()+1);
                        this.control(session, stage);
                    } else {
                        popup.hide();
                        if(session.getNbMotsRestants()==0)
                        {   session.getJoueur().setHighScore(session.getScore());
                            SoundController.pauseBackgroundMusic();
                            SoundController.playGameOverMusic();
                            this.terminate();
                            View view = new View();
                            view.fin(session.getJoueur(),"GAME OVER !");
                        }

                    }
                } else {
                    if (result == -1) {
                        /*String wrongAnswerMusic = "wrongAnswer.wav";     // For example
                        Media wrongAnswer = new Media(new File(wrongAnswerMusic).toURI().toString());
                        MediaPlayer wrongAnswerMusicPlayer = new MediaPlayer(wrongAnswer);
                        wrongAnswerMusicPlayer.play();*/
                        SoundController.playWrongAnswer();
                        shaker.shake();
                        System.out.println("Essayez encore une fois");
                        popup.hide();
                        this.control(session, stage);
                        // controller.control(this);
                    } else//result==1
                    {   /*String correctAnswerMusic = "correctAnswer.mp3";     // For example
                        Media correctAnswer = new Media(new File(correctAnswerMusic).toURI().toString());
                        MediaPlayer correctAnswerMusicPlayer = new MediaPlayer(correctAnswer);

                        correctAnswerMusicPlayer.play();*/
                        SoundController.playCorrectAnswer();
                        popup.hide();
                        enable(view, currentCase.getTextField());
                        if (currentCase instanceof Proposition){
                            tooltip.hide();
                        }
                        currentCase.getNextTextField().requestFocus();

                        iCurrentCase++;
                        if (iCurrentCase == view.getCases().size()) {
                            iCurrentCase=0;
                            System.out.println("Bravo ! la réponse était: " + question.getReponse());

                            Alert cor = new Alert(Alert.AlertType.CONFIRMATION);

                            cor.setHeight(100);
                            cor.setContentText(" Your answer is correct !");
                            cor.setTitle("succes !");
                            cor.setHeaderText(null);
                            cor.setGraphic(new ImageView(new Image("correct.png",60,60,true,true)));
                            cor.setOnShowing(event1 -> {Timeline idlestage = new Timeline( new KeyFrame( Duration.seconds(1 ), new EventHandler<ActionEvent>()
                            {

                                @Override
                                public void handle( ActionEvent event )
                                {
                                    cor.setResult(ButtonType.CLOSE);
                                    cor.hide();
                                }
                            } ) );
                                SoundController.playCorrectAnswer();
                                idlestage.setCycleCount( 2 );
                                idlestage.play();
                            });
                            cor.showAndWait();



                            session.setCurrentQuestion(session.getCurrentQuestion() + 1);
                            question.CalculerScore();
                            session.setScore(session.getScore() + question.getScore());
                            if (session.getNbMotsRestants() > 0 && session.getCurrentQuestion() < 10) {
                                view = new View(stage,session.getJoueur(), questions.get(session.getCurrentQuestion()), session.getScore(),6-session.getNbMotsRestants(),session.getCurrentQuestion()+1);
                                this.control(session, stage);
                            } else {
                                session.getJoueur().setHighScore(session.getScore());

                                SoundController.pauseBackgroundMusic();
                                SoundController.playWinningMusic();
                                this.terminate();
                                View view = new View();
                                view.fin(session.getJoueur(),"YOU WON CONGRATULATION!");

                                System.out.println("Merci");
                            }
                        } else {
                            this.control(session, stage);
                        }
                    }
                }
            }
        });
    }
    private void disable(View view, TextField currentTexfField)
    {
        int i;
        TextField tf;
        for(i=view.getCases().indexOf(currentTexfField)+1;i<view.getCases().size();i++)
        {
            tf=view.getCases().get(i);
            tf.setDisable(true);
        }
    }
    private void enable(View view, TextField currentTexfField)
    {
        int i;
        TextField tf;
        for(i=view.getCases().indexOf(currentTexfField)+1;i<view.getCases().size();i++)
        {
            tf=view.getCases().get(i);
            if(tf.isDisabled()==true)
                tf.setDisable(false);

        }
    }
    class Shaker {
        private TranslateTransition tt;

        public Shaker(TextField tf) {
            tt = new TranslateTransition(Duration.millis(50), tf);
            tt.setFromX(0f);
            tt.setByX(10f);
            tt.setCycleCount(6);
            tt.setAutoReverse(true);
        }

        public void shake() {
            tt.playFromStart();
        }
    }
    private Popup createPopUp(Case cs)
    {   TextField tf = cs.getTextField();
        Bounds boundsInScreen = tf.localToScreen(tf.getBoundsInLocal());

        final Popup popup = new Popup();
        popup.setX(boundsInScreen.getMaxX() - 35);
        popup.setY(boundsInScreen.getMaxY() + 25);
        if(cs instanceof MultiCase) {


        popup.getContent().addAll(new Circle(6.5f, 12.5f, 15, Color.CHOCOLATE));
        Label label = new Label("" + ((MultiCase) cs).getNbChances());
        label.getStyleClass().add("chancelabel");
        popup.getContent().add(label);

    }
        return popup;
    }
    public void fin(){

        Label label=new Label("  Merci ! ");
        label.setId("merci");
        String url = "background.png" ;
        Image img = new Image(url);
        BackgroundImage bgImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));

        BorderPane bp=new BorderPane(label);
        bp.setBackground(new Background(bgImg));
        bp.setId("bg");
        bp.setCenter(label);


        bp.setAlignment(label, Pos.CENTER);
        Scene fine = new Scene(bp,1020,570);
        fine.getStylesheets().add("style.css");
        stage.setScene(fine);
    }

    public void terminate (){
        if (popup!=null ){
            if(popup.isShowing())this.popup.hide();
        }
        if (tooltip!=null){
            if(tooltip.isShowing())this.tooltip.hide();
        }
        SoundController.pauseBackgroundMusic();
    }

   /* private Tooltip generateToolTip  (Case currentCase,String prop,Boolean oldValue){
        Tooltip tooltip = new Tooltip(0;)
        List<String> prp =  Arrays.asList(((Proposition) currentCase).getPropositions().split(""));
        tooltip.setText(
                "\nthis is the propositions :\n" +
                        " "+prp.get(0)+" , "+prp.get(1)+" , "+prp.get(2)+" , "+prp.get(3)
        );
        // ddd=true;
        TextField pf = currentCase.getTextField();
        tooltip.show( pf, //
                // popup tooltip on the right, you can adjust these values for different positions
                pf.getScene().getWindow().getX() + pf.getLayoutX() + pf.getWidth() + 10, //
                pf.getScene().getWindow().getY() + pf.getLayoutY() + pf.getHeight());
        //tooltip = this.generateToolTip(currentCase,((Proposition) currentCase).getPropositions(),ddd);
        //currentCase.getTextField().setTooltip(tooltip);
        return tooltip;

}*/
}



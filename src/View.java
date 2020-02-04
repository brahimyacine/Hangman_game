import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SLASH on 04/05/2017.
 */
public class View {
    private Label typeDef_Quest;
    private List<TextField> Cases;
    private ImageView imageView=new ImageView();
    static Boolean show1=false , show2=false;
    static boolean test = true;
    public static   BorderPane borderPane;
    public View(){};
    public View(Stage primaryStage,Joueur joueur, Question question,int Score,int nbMotRate,int current)
    {   int indice=question.getQuestion().indexOf(" ",30);
        if (indice>0)
        {   String quest=question.getQuestion();
            this.typeDef_Quest=new Label(question.getType()+" : "+quest.substring(0,indice)+"\n"+"                           "
                    +quest.substring(indice+1,quest.length()));
        }
        else
        {
            this.typeDef_Quest=new Label(question.getType()+" : "+question.getQuestion());
        }

        Hover hover1=new Hover(getTypeDef_Quest(),0);
        Hover hover2=new Hover(getTypeDef_Quest());
        typeDef_Quest.setOnMouseEntered(e->{

            hover1.hover();
        });
        typeDef_Quest.setOnMouseExited(e->{

            hover2.hover();
        });
        HBox hBox1=new HBox(20);

        Label score=new Label("Score : "+Score+" !");
        hBox1.getChildren().addAll(new Label(joueur.getPseudo().toUpperCase()),score);


        hBox1.setAlignment(Pos.CENTER);
        Cases=new ArrayList<TextField>();
        TextField Case,nextCase;

        HBox hBox=new HBox(20);

        for(int i=0;i<question.getCases().size();i++)
        {
            Case=question.getCases().get(i).getTextField();
            final TextField case1=Case;
            Cases.add(case1);

            Case.setMaxSize(40,40);
            hBox.getChildren().add(Case);
        }
        int i=1;
        for(i=1;i<Cases.size();i++)
        {
            nextCase=Cases.get(i);
            question.getCases().get(i-1).setNextTextField(nextCase);


        }
        question.getCases().get(i-1).setNextTextField(Cases.get(0));
        hBox.setAlignment(Pos.CENTER);
         borderPane=new BorderPane();
        /// typeDef_Quest.setAlignment(Pos.CENTER);
        String url = "background.png" ;
        Image img = new Image(url);
        BackgroundImage bgImg = new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        borderPane.setBackground(new Background(bgImg));
        VBox vBox=new VBox(20);
        vBox.getChildren().addAll(hBox1,typeDef_Quest);
        vBox.setAlignment(Pos.CENTER_LEFT);
        borderPane.setTop(vBox);
        borderPane.setCenter(hBox);
        dessiner(nbMotRate);

        borderPane.setRight(imageView);
        borderPane.setAlignment(imageView,Pos.CENTER_RIGHT);
        borderPane.setAlignment(hBox,Pos.CENTER);
        borderPane.setAlignment(vBox,Pos.CENTER);
        borderPane.setId("bg");

        Scene scene=new Scene(borderPane,1020,570);

        /* ----------------------------- here is the side panel ----------------------------*/



       /* final Text lyric = new Text(lyrics[lyricIndex]);
        lyric.setFill(Color.DARKGREEN);
        lyric.setTextAlignment(TextAlignment.CENTER);
        lyric.setStyle("-fx-font-size: 20;");
        final Button changeLyric = new Button("New Lyrics");
        changeLyric.setMaxWidth(Double.MAX_VALUE);
        changeLyric.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                lyricIndex++;
                lyric.setText(lyrics[lyricIndex % 2]);
            }
        });
        final BorderPane lyricPane = new BorderPane();
        lyricPane.setCenter(lyric);
       // borderPane.setLeft(lyric);
        //borderPane.setBottom(changeLyric);
        lyricPane.setBottom(changeLyric);
        // place the content in the sidebar.*/

        HBox slider = new HBox(0);
        slider.setAlignment(Pos.TOP_CENTER);
        Image drawer = new Image("drawerImg.jpg",295,200,true,true);
        ImageView drawerimg = new ImageView(drawer);
        //,30,30,true,true
        Image user = new Image ("user-icon.png",80,80,true,true);
        ImageView user1 = new ImageView(user);
        //user1.setId("slide");
       /*String img = this.getCases().getClass().getResource("drawerImg.jpg").toExternalForm();
       slider.setStyle("-fx-background-image: url ('"+img+"');");*/
        slider.setPrefSize(295,160);
        slider.getChildren().addAll(user1);
        Label userNam = new Label("      "+joueur.getPseudo().toUpperCase());
        slider.setAlignment(Pos.CENTER);
        slider.getChildren().addAll(userNam);
        slider.setId("slider");


        VBox panel = new VBox(10);
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setSpacing(8);
        panel.setPadding(new Insets(0, 0, 8, 0));


        Button userChange = new Button("      Change User ");
        Image chUser = new Image("change-user.png",30,30,true,true);
        ImageView chUser1 = new ImageView(chUser);
        userChange.setGraphic(chUser1);
        userChange.setAlignment(Pos.BASELINE_CENTER);
        userChange.setId("btnSlider");
        userChange.setPrefSize(295,60);
        Noyau newNoyau = new Noyau();
        userChange.setOnAction(e -> {
            try {
                Noyau.window.close();
                newNoyau.start(Session.endSession());

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        VBox type = new VBox(130);
        type.setAlignment(Pos.TOP_LEFT);
        hBox.setAlignment(Pos.BASELINE_LEFT);
        type.setPadding(new Insets(35, 0, 0, 0));
        type.getChildren().addAll(typeDef_Quest,hBox);
        hBox.requestFocus();
        borderPane.setCenter(type);


        Button HighScores = new Button("      HighScores ");
        HighScores.setId("btnSlider");
        Image hSc = new Image("highScores.png",30,30,true,true);
        ImageView highSc = new ImageView(hSc);
        HighScores.setGraphic(highSc);
        HighScores.setAlignment(Pos.BASELINE_CENTER);
        HighScores.setPrefSize(295,60);

        HighScores.setOnAction(event -> {
            if (test) {

               this.handlePop();
                borderPane.setCenter(Noyau.highScores);
                test=false;
            }
            else {
                if (show1) Controller.popup.show(primaryStage);
                if (show2){
                    Controller.tooltip.setId("tool");
                    Controller.tooltip.show(primaryStage);
                }
                borderPane.setCenter(type);
                test=true;
            }
        });


        Button UserScores = new Button("       User Scores ");
        UserScores.setId("btnSlider");
        Image hScU = new Image("score_user.png",30,30,true,true);
        ImageView highScU = new ImageView(hScU);
        UserScores.setGraphic(highScU);
        UserScores.setAlignment(Pos.BASELINE_CENTER);
        UserScores.setPrefSize(295,60);
        UserScores.setOnAction(event -> {
            if (test) {

                this.handlePop();
                borderPane.setCenter(Noyau.userScores);
                test=false;

            }
            else {
                if (show1) Controller.popup.show(primaryStage);
                if (show2){
                    Controller.tooltip.setId("tool");
                    Controller.tooltip.show(primaryStage);
                }
                borderPane.setCenter(type);
                test=true;
            }
        });


        Button save = new Button("       Save Score ");
        save.setId("btnSlider");
        Image sav = new Image("score_user.png",30,30,true,true);
        ImageView sav1 = new ImageView(sav);
        save.setGraphic(sav1);
        save.setAlignment(Pos.BASELINE_CENTER);
        save.setPrefSize(295,60);
        save.setOnAction(e->joueur.saveScore(Score));


        Button quit = new Button("     Quit the game");
        Image esc = new Image("shutDown.png",30,30,true,true);
        ImageView eschape = new ImageView(esc);
        quit.setGraphic(eschape);
        quit.setAlignment(Pos.BASELINE_CENTER);
        quit.setId("btnSlider");
        quit.setPrefSize(295,60);
        quit.setOnAction(event -> {



            Controller cntr = new Controller(Noyau.window);
            //Noyau.window.setScene(new Scene());
            cntr.fin();
            cntr.terminate();
            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished( e -> Noyau.window.close() );
            delay.play();

            //Noyau.window.close();
        });

        panel.getChildren().addAll(slider,userChange,HighScores,UserScores,save,quit);

        SideBar sidebar = new SideBar(panel);
        sidebar.setAlignment(Pos.TOP_CENTER);
        // VBox.setVgrow(lyricPane, Priority.ALWAYS);
        VBox.setVgrow(sidebar,   Priority.ALWAYS);
        sidebar.setMinWidth(200);
        sidebar.setSpacing(0);
        sidebar.setPadding(new Insets(0, 0, 0, 0));
        borderPane.setLeft(sidebar);



        // create a WebView to show to the right of the SideBar;
        WebView webView = new WebView();
        webView.getEngine().load("http://docs.oracle.com/javafx/2.0/get_started/jfxpub-get_started.htm");
        /*------------------------------ ens of side panel ---------------------------------*/

        HBox hboxD = addHBox(Score, joueur.getPseudo(),joueur.getHighScore(),current,sidebar);
        borderPane.setTop(hboxD);




        scene.getStylesheets().add("style.css");
        //primaryStage.setTitle("HangMan");

        primaryStage.setScene(scene);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        hBox.getChildren().get(0).requestFocus();
    }
    public String getText()
    {   String text="";
        for(TextField Case:Cases)
        {
            text+=Case.getText().charAt(0);
        }
        return text.toLowerCase();
    }
    public Label getTypeDef_Quest() {
        return typeDef_Quest;
    }

    public List<TextField> getCases() {
        return Cases;
    }


    class Hover {
        private TranslateTransition tt;

        public Hover(Label label,int a) {
            tt = new TranslateTransition(Duration.millis(100), label);
            tt.setFromX(0f);
            tt.setFromY(0f);
            tt.setByX(5f);
            tt.setByY(5f);
            tt.setCycleCount(1);

        }
        public Hover(Label label) {
            tt = new TranslateTransition(Duration.millis(100), label);
            tt.setFromX(0f);
            tt.setFromY(0f);
           tt.setCycleCount(1);

        }

        public void hover() {
            tt.playFromStart();
        }
    }
    public void dessiner(int nbMotRate)
    {
        if(nbMotRate!=0)
        {

            Image dessin=new Image("dessin/"+nbMotRate+".png");
            imageView=new ImageView(dessin);
        }
    }

    public HBox addHBox(int Score,String userName,int best,int current,SideBar sideBar) {
        HBox hbox = new HBox();
        //hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(0);
        //hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: transparent;");

        Image menu = new Image("menu.png");
        ImageView menuicon = new ImageView(menu);

        //Button buttonCurrent = new Button(" User ");
        Button buttonCurrent = sideBar.getControlButton();
        buttonCurrent.setId("btnmenu");
        buttonCurrent.setPrefWidth(300);
        buttonCurrent.setPrefHeight(60);
        buttonCurrent.setAlignment(Pos.BASELINE_LEFT);
        buttonCurrent.setGraphic(menuicon);

        Label score=new Label("Score : "+Score);

        Button score1 = new Button(score.getText());
        score1.setPrefWidth(150);
        score1.setAlignment(Pos.BASELINE_LEFT);
        score1.setPrefHeight(66);
        score1.setId("btnmenu");

        Label high=new Label(" Best score : "+best);
        Button high1 = new Button(high.getText());
        high1.setPrefWidth(220);
        high1.setAlignment(Pos.BASELINE_LEFT);
        high1.setPrefHeight(66);
        high1.setId("btnmenu");

        Label mots=new Label("Step : "+current+"/10");
        Button mots1 = new Button(mots.getText());
        mots1.setPrefWidth(160);
        mots1.setAlignment(Pos.BASELINE_LEFT);
        mots1.setPrefHeight(66);
        mots1.setId("btnmenu");

        Image son = new Image("volume.png",60,40,true,true);
        ImageView son1 = new ImageView(son);
        Image mute = new Image("mute.png",60,50,true,true);
        ImageView mute1 = new ImageView(mute);
        Button speaker = new Button(" speaker");
        speaker.setPrefWidth(200);
        speaker.setAlignment(Pos.BASELINE_CENTER);
        speaker.setPrefHeight(66);
        speaker.setId("btnmenu");
        speaker.setGraphic(son1);
        speaker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (speaker.getGraphic()==son1) speaker.setGraphic(mute1);
                else speaker.setGraphic(son1);
                SoundController.handlePause();
            }
        });
        hbox.getChildren().addAll(buttonCurrent,high1,score1,mots1,speaker);

        /*Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);
        hbox.getChildren().addAll(buttonCurrent, buttonProjected);*/


        return hbox;
    }

    public void addStackPane(HBox hb) {
        StackPane stack = new StackPane();
        Rectangle helpIcon = new Rectangle(30.0, 25.0);
        helpIcon.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0, Color.web("#4977A3")),
                        new Stop(0.5, Color.web("#B0C6DA")),
                        new Stop(1,Color.web("#9CB6CF")),}));
        helpIcon.setStroke(Color.web("#D0E6FA"));
        helpIcon.setArcHeight(3.5);
        helpIcon.setArcWidth(3.5);

        Text helpText = new Text("?");
        helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        helpText.setFill(Color.WHITE);
        helpText.setStroke(Color.web("#7080A0"));

        stack.getChildren().addAll(helpIcon, helpText);
        stack.setAlignment(Pos.CENTER_RIGHT);     // Right-justify nodes in stack
        StackPane.setMargin(helpText, new Insets(0, 10, 0, 0)); // Center "?"

        hb.getChildren().add(stack);            // Add to HBox from Example 1-2
        HBox.setHgrow(stack, Priority.ALWAYS);    // Give stack any extra space
    }

    private void handlePop(){
        if (Controller.popup!=null ){
            if(Controller.popup.isShowing()){
                Controller.popup.hide();
                show1=true;
            }
        }
        if (Controller.tooltip!=null){
            if(Controller.tooltip.isShowing()){
                Controller.tooltip.hide();
                show2=true;
            }
        }
    }
    public void fin (Joueur joueur, String status){
        VBox end = new VBox();
        Label end1 = new Label("  "+status);
        Button replay = new Button("   RePlay ");
        replay.setId("btnmenu");
        replay.setOnAction(e->{
            Noyau noy = new Noyau();
            String pseudo = new String();
            Joueurs joueurs = new Joueurs();
            Noyau.highScores=noy.createHighScores(joueurs.getJoueurs());
            Session session=new Session();
            joueurs.logIn(joueur);
            Noyau.userScores=noy.creatUserScores(joueur);
            session.setJoueur(joueur);
            session.launchSession(Noyau.window);
        });
        end.setSpacing(70);
        if (status=="GAME OVER !"){
            dessiner(6);
            borderPane.setRight(imageView);
            borderPane.setAlignment(imageView,Pos.CENTER_RIGHT);
        }
        end1.setTextFill(Color.RED);
        end.getChildren().addAll(end1,replay);
        end.setAlignment(Pos.CENTER);
        borderPane.setCenter(end);

    }

    /*public VBox BestScores (){

    }*/
}

/** Animates a node on and off screen to the left. */
class SideBar extends VBox {
    /** @return a control button to hide and show the sidebar */
    public Button getControlButton() { return controlButton; }
    private final Button controlButton;

    /** creates a sidebar containing a vertical alignment of the given nodes */
    SideBar(Node... nodes) {
        // create a bar to hide and show.
        setAlignment(Pos.CENTER);
        setVisible(false);
        //-fx-padding: 10;
        setStyle(" -fx-background-color: linear-gradient(to bottom, lavenderblush, mistyrose); -fx-border-color: derive(mistyrose, -10%); -fx-border-width: 3;");
        getChildren().addAll(nodes);

        // create a button to hide and show the sidebar.
        controlButton = new Button("   MENU  ");
        Image menu = new Image("menu.png");
        ImageView menuicon = new ImageView(menu);
        controlButton.setGraphic(menuicon);
        controlButton.setId("btnmenu");
        controlButton.setPrefWidth(180);
        controlButton.setPrefHeight(60);
        controlButton.setAlignment(Pos.BASELINE_LEFT);
        //controlButton.setMaxWidth(Double.MAX_VALUE);
        //controlButton.setTooltip(new Tooltip("Play sidebar hide and seek"));

        // apply the animations when the button is pressed.
        controlButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                // create an animation to hide sidebar.
                final double startWidth = getWidth();
                final Animation hideSidebar = new Transition() {
                    { setCycleDuration(Duration.millis(250)); }
                    protected void interpolate(double frac) {
                        final double curWidth = startWidth * (1.0 - frac);
                        setTranslateX(-startWidth + curWidth);
                    }
                };
                hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {
                        setVisible(false);
                        //controlButton.setText("Show");
                    }
                });

                // create an animation to show a sidebar.
                final Animation showSidebar = new Transition() {
                    { setCycleDuration(Duration.millis(250)); }
                    protected void interpolate(double frac) {
                        final double curWidth = startWidth * frac;
                        setTranslateX(-startWidth + curWidth);
                    }
                };
                /*showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent actionEvent) {
                        controlButton.setText("Hide");
                    }
                });*/

                if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
                    if (isVisible()) {
                        hideSidebar.play();
                    } else {
                        setVisible(true);
                        showSidebar.play();
                    }
                }
            }
        });
    }
}



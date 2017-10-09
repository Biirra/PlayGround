package nl.janwillemhuising;


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import nl.janwillemhuising.controllers.OptionsMenu;
import nl.janwillemhuising.model.*;
import nl.janwillemhuising.model.sprite.Sprite;
import nl.janwillemhuising.view.Layer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class Main extends Application {

    static Random rand = new Random();

    Layer playfield;

    List<Henry> allHenrys = new ArrayList<>();
    List<TestBall> allTestBalls = new ArrayList<>();
    List<Follower> allFollowers = new ArrayList<>();
    List<Attractor> allAttractors = new ArrayList<>();

    AnimationTimer gameloop;

    PVector mouseLocation = new PVector(0,0);

    Scene scene;

    Label labelFPS;
    Label mousePos;

    OptionsMenu optionsMenu = new OptionsMenu();


    //----------------------------------
    // for checking the FPS
    //----------------------------------
    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //-----------------------------
        // setting up canvas
        //-----------------------------
        primaryStage.setTitle("NatureOfCode");
        primaryStage.getIcons().add(new Image("https://www.shareicon.net/data/64x64/2016/08/22/818801_plant_512x512.png"));
        BorderPane root = new BorderPane();

        playfield = new Layer(Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);

        // entire game as layers
        Pane layerPane = new Pane();

        layerPane.getChildren().addAll(playfield);

        root.setCenter(layerPane);


        scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        primaryStage.setScene(scene);

        prepareSprites();
        addEventhandlers();
        startSimulation();

        //-------------------------------------
        // creating debug panel
        //--------------------------------------
        GridPane debugPanel = new GridPane();
        debugPanel.setHgap(5);
        debugPanel.setVgap(5);
        debugPanel.setPadding(new Insets(10));

        //debug labels
        labelFPS = new Label();
        mousePos = new Label();

        //adding labels to debug panel
        debugPanel.addRow(0,labelFPS);
        debugPanel.addRow(1,mousePos);

        layerPane.getChildren().add(debugPanel);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void prepareSprites(){
        // add movers
        for( int i = 0; i < Settings.MOVER_COUNT; i++) {
            addHenry();
        }
        for( int i = 0; i < Settings.TESTBALL_COUNT; i++) {
            addTestball();
        }
        for( int i = 0; i < Settings.FOLLOWER_COUNT; i++){
            addFollower();
        }
        for( int i = 0; i < Settings.ATRACTOR_COUNT; i++){
            addAttractor();
        }

    }

    public void startSimulation(){
        gameloop = new AnimationTimer() {

            @Override
            public void handle(long now) {
                //---------------------------
                // stuff to get FPS
                //---------------------------
                long oldFrameTime = frameTimes[frameTimeIndex];
                frameTimes[frameTimeIndex] = now;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                if (frameTimeIndex == 0) {
                    arrayFilled = true;
                }
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
                    labelFPS.setText(String.format("Current frame rate: %.3f", frameRate));
                }

                //------------------------------------
                //  Checking if sprite is still alive
                //------------------------------------
                Iterator<Follower> allFollowersIter = allFollowers.iterator();
                while ( allFollowersIter.hasNext() ) {
                    Sprite follower = allFollowersIter.next();
                    System.out.println(follower);
                    if (!follower.isAlive())
                    {
                        follower.setVisible(false);
                        allFollowersIter.remove();
                    }
                }
                Iterator<Attractor> allAttractorIter = allAttractors.iterator();
                while ( allAttractorIter.hasNext() ) {
                    Sprite attractor = allAttractorIter.next();
                    if(!attractor.isAlive()){
                        attractor.setVisible(false);
                        allAttractorIter.remove();
                    }
                }
                //------------------------------
                // setting forces and what not
                //------------------------------
                allHenrys.forEach(henry -> {
                });
                allTestBalls.forEach(testBall -> {
                });
                allFollowers.forEach(follower -> {
                    float c = 0.01F;
                    float normal = 1F;
                    float frictionMag = c*normal;

                    PVector friction = PVector.copy(follower.getVelocity());
                    friction.mult(-1);
                    friction.normalize();
                    friction.mult(frictionMag);

                    //follower.applyForce(friction);
                });
                allAttractors.forEach(attractor -> {
                    System.out.println(attractor);
                    allFollowers.forEach(follower -> {
                        follower.seek(attractor.getLocation());
                    });
                });


                //--------------------------
                // moving
                //--------------------------
                allHenrys.forEach(Sprite::move);
                allTestBalls.forEach(Sprite::move);
                allTestBalls.forEach(Sprite::checkEdges);
                allFollowers.forEach(Sprite::move);

                //--------------------------
                // display
                //--------------------------
                allAttractors.forEach(Sprite::display);
                allHenrys.forEach(Sprite::display);
                allTestBalls.forEach(Sprite::display);
                allFollowers.forEach(Sprite::display);
                //System.out.println(playfield.getChildren());
            }
        };

        gameloop.start();
    }

    //------------------------------
    // Creating Sprites
    //------------------------------
    public void addFollower(){
        Layer layer = playfield;

        // random location
        float x = Utils.randomFloat(0,(float)Settings.SCENE_WIDTH);
        float y = Utils.randomFloat(0,(float)Settings.SCENE_HEIGHT);

        float mass = Utils.randomFloat(0.4F, 5);

        PVector location = new PVector( x,y);
        PVector velocity = new PVector( 0,0);
        PVector acceleration = new PVector( 0,0);

        // create sprite and add to layer
        Follower follower = new Follower( layer, location, velocity, acceleration, mass);

        allFollowers.add(follower);
    }

    public void addHenry(){
        Layer layer = playfield;

        // random location
        float x = 50;
        float y = Utils.randomFloat(0,(float)Settings.SCENE_HEIGHT);

        float mass = 3;

        // create Henry data
        PVector location = new PVector( x,y);
        PVector velocity = new PVector( 0,0);
        PVector acceleration = new PVector( 0,0);


        // create sprite and add to layer
        Henry henry = new Henry( layer, location, velocity, acceleration, mass);

        allHenrys.add(henry);
    }

    public void addTestball(){
        Layer layer = playfield;

        // random location
        float x = Utils.randomFloat(0,(float)Settings.SCENE_WIDTH);
        float y = Utils.randomFloat(0,(float)Settings.SCENE_HEIGHT);

        float mass = 3;

        // create Henry data
        PVector location = new PVector( x,y);
        PVector velocity = new PVector( 0,0);
        PVector acceleration = new PVector( 0,0);


        // create sprite and add to layer
        TestBall testBall = new TestBall( layer, location, velocity, acceleration, mass);

        allTestBalls.add(testBall);
    }
    public void addAttractor(){

        Layer layer = playfield;

        // random location
        float x = Utils.randomFloat(0,(float)Settings.SCENE_WIDTH);
        float y = Utils.randomFloat(0,(float)Settings.SCENE_HEIGHT);

        float mass = 3;

        // create Henry data
        PVector location = new PVector( x,y);
        PVector velocity = new PVector( 0,0);
        PVector acceleration = new PVector( 0,0);

        Attractor attractor = new Attractor(layer, location, velocity, acceleration, mass);

        allAttractors.add(attractor);
    }
    //-----------------------------------
    //  Removing Sprite
    //-----------------------------------
    public void removeAttractor(Sprite sprite){

        allAttractors.remove(sprite);
    }

    //-----------------------------------
    //  Adding Event Handlers
    //-----------------------------------

    private void addEventhandlers(){
        // capture mouse position
        scene.addEventFilter(MouseEvent.ANY, e -> {
            mouseLocation.set((float)e.getX(), (float)e.getY());
            mousePos.setText(String.format("Current MousePos X: %.0f  /  Y: %.0f", mouseLocation.getX(), mouseLocation.getY()));
        });
        addOptionSpawnSprite();
        enableOptionMenu();
    }

    //-------------------------------------
    // Enable options menu for set scene
    //-------------------------------------
    public void enableOptionMenu(){
        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.isSecondaryButtonDown()){
                    optionsMenu.show(mousePos, event.getScreenX(), event.getScreenY());
                }
                event.consume();
            }
        };
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
    }
    //-------------------------------------
    // Adding the menu items to Scene
    //-------------------------------------

    public void addOptionSpawnSprite(){
        Menu parentMenu = new Menu("Spawn Sprite >");
        MenuItem childMenuItem1 = new MenuItem("Attractor");
        MenuItem childMenuItem2 = new MenuItem("Follower");
        parentMenu.getItems().addAll(childMenuItem1, childMenuItem2);
        childMenuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addAttractor();
            }
        });
        childMenuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addFollower();
            }
        });

        optionsMenu.addMenuItem(parentMenu);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

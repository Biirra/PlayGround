package sample;


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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.sprite.Car;
import sample.sprite.SpaceShip;
import sample.sprite.Sprite;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Main extends Application {

    static Random rand = new Random();

    Layer playfield;


    AnimationTimer gameloop;

    PVector mouseLocation = new PVector(0,0);
    KeyInputHandler keyInputHandler = new KeyInputHandler();

    Scene scene;

    Label labelFPS;
    Label mousePos;

    OptionsMenu optionsMenu = new OptionsMenu();

    List<SpaceShip> allSpaceShips = new ArrayList<>();
    List<Car> allCars = new ArrayList<>();

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
        for(int i = 0; i < Settings.SPACESHIP_COUNT; i++){
            addSpaceShip();
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
//                Iterator<Follower> allFollowersIter = allFollowers.iterator();
//                while ( allFollowersIter.hasNext() ) {
//                    Sprite follower = allFollowersIter.next();
//                    if (!follower.isAlive())
//                    {
//                        follower.setVisible(false);
//                        allFollowersIter.remove();
//                    }
//                }
                //------------------------------
                // setting forces and what not
                //------------------------------
//                allFollowers.forEach(follower -> {
//                    float c = 0.01F;
//                    float normal = 1F;
//                    float frictionMag = c*normal;
//
//                    PVector friction = PVector.copy(follower.getVelocity());
//                    friction.mult(-1);
//                    friction.normalize();
//                    friction.mult(frictionMag);
//
//                    //follower.applyForce(friction);
//                });


                //--------------------------
                // moving
                //--------------------------
                allSpaceShips.forEach(spaceShip -> {
                    //handeling userinput
                    spaceShip.glide(keyInputHandler);
                });

                allSpaceShips.forEach(Sprite::rotate);
                allSpaceShips.forEach(Sprite::move);
                //--------------------------
                // display
                //--------------------------
                allSpaceShips.forEach(Sprite::display);
            }
        };

        gameloop.start();
    }

    //------------------------------
    // Creating Sprites
    //------------------------------
    public void addSpaceShip(){
        PVector location = new PVector((float)Settings.SCENE_WIDTH/2, (float)Settings.SCENE_HEIGHT/2);
        PVector velocity = new PVector(0,0);
        PVector acceleration = new PVector(0,0);
        float mass = Settings.DEFAULT_MASS;

        SpaceShip spaceShip = new SpaceShip(playfield, location, velocity, acceleration, mass);
        allSpaceShips.add(spaceShip);
    }
    public void addCar(){
        PVector location = new PVector((float)Settings.SCENE_WIDTH/2, (float)Settings.SCENE_HEIGHT/2);
        PVector velocity = new PVector(0,0);
        PVector acceleration = new PVector(0,0);
        float mass = Settings.DEFAULT_MASS*3;

        Car car = new Car(playfield, location, velocity, acceleration, mass);
        allCars.add(car);
    }
    //-----------------------------------
    //  Removing Sprite
    //-----------------------------------


    //-----------------------------------
    //  Adding Event Handlers
    //-----------------------------------

    private void addEventhandlers(){
        // capture mouse position
        scene.addEventFilter(MouseEvent.ANY, e -> {
            mouseLocation.set((float)e.getX(), (float)e.getY());
            mousePos.setText(String.format("Current MousePos X: %.0f  /  Y: %.0f", mouseLocation.x, mouseLocation.y));
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            keyInputHandler.pressKey(event);
        });
        scene.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            keyInputHandler.releaseKey(event);
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

            }
        });
        childMenuItem2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        optionsMenu.addMenuItem(parentMenu);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

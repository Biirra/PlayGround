package sample.sprite;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import sample.*;

/**
 * Created by JanWillem Huising on 6-7-2017.
 */
public abstract class Sprite extends Region {
    PVector location;
    PVector velocity;
    PVector acceleration;

    float angle = 0;
    float aVelocity = 0;
    float aAcceleration = 0.001F;

    float maxForce = Settings.SPRITE_MAX_FORCE;
    float maxSpeed = 3;

    Node view;

    float w;
    float h;
    float m;

    float centerX;
    float centerY;

    float theta = 0;

    Layer layer = null;

    float g;  // universal gravitational constant

    MouseGestures mouseGestures = new MouseGestures();

    OptionsMenu optionsMenu = new OptionsMenu();

    boolean alive;


    boolean dragable = false;

    public Sprite(Layer layer, PVector location, PVector velocity, PVector acceleration, float m) {
        this.alive = true;
        this.layer = layer;

        this.location = location;
        this.velocity = velocity;
        this.acceleration = acceleration;

        this.m = m;
        this.w = m * 8;
        this.h = m * 8;

        this.view = createView();

        g = 2F;

        getChildren().add(view);
        layer.getChildren().add(this);

        addOptionMouseCotrole();
        addOptionRemoveSprite();
        enableOptionMenu();
    }

    public abstract Node createView();

    public void move() {

        // set velocity depending on acceleration
        velocity.add(acceleration);

        // change location depending on velocity
        location.add(velocity);


        if(!isDragable()){
            angle = (float) velocity.heading2D();
        }else{

        }
        // clear acceleration;
        acceleration.mult(0);
    }

    public void display() {

        relocate(location.getX() - centerX, location.getY() - centerY);

        setRotate(Math.toDegrees(angle));

    }

    public void checkEdges() {
        if (location.getX() > Settings.SCENE_WIDTH) {
            location.setX((float) Settings.SCENE_WIDTH);
            velocity.setX(velocity.getX() * -1);
        } else if (location.getX() < 0) {
            velocity.setX(velocity.getX() * -1);
            location.setX(0);
        }

        if (location.getY() + 15 > Settings.SCENE_HEIGHT) {
            location.setY((float) Settings.SCENE_WIDTH);
            velocity.setY(velocity.getY() * -1);
        } else if (location.getY() < 0) {
            velocity.setY(velocity.getY() * -1);
            location.setY(0);
        }
    }

    public boolean isInside(Liquid li) {
        if (location.getX() > li.getX() && location.getX() < li.getX() + li.getW() && location.getY() > li.getY() && location.getY() < li.getY() + li.getH()) {
            return true;
        } else {
            return false;
        }
    }

    public void drag(Liquid li) {
        float speed = velocity.mag();
        float dragMagnitude = li.getC() * speed * speed;


        PVector drag = PVector.copy(velocity);
        drag.mult(-1);
        drag.normalize();

        drag.mult(dragMagnitude);

        applyForce(drag);
    }

    public void lookAt(PVector target) {
        PVector desired = PVector.sub(target, location);
        desired.normalize();

        //PVector steer = PVector.sub(desired, velocity);
        angle = 36;
    }

    public void seek(PVector target) {

        PVector desired = PVector.sub(target, location);

        float d = desired.mag();
        desired.normalize();

        if (d < Settings.SPRITE_SLOW_DOWN_DISTANCE) {
            float m = Utils.map(d, 0, Settings.SPRITE_SLOW_DOWN_DISTANCE, 0, maxSpeed);
            desired.mult(m);

        } else {
            desired.mult(Settings.SPRITE_MAX_SPEED);
        }

        PVector steer = PVector.sub(desired, velocity);
        steer.limit(maxForce);

        applyForce(steer);

    }

    public void gravitateTo(Sprite s) {

        PVector force = PVector.sub(s.location, location);

        float distance = force.mag();
        force.normalize();
        distance = Shortcut.constrainFloat(distance, 5, 25);
        float strength = (g * m * s.m) / (distance * distance);
        force.mult(strength);

        applyForce(force);

    }

    public void gravitate(Sprite s) {
        PVector force = PVector.sub(location, s.location);

        float distance = force.mag();
        force.normalize();
        distance = Shortcut.constrainFloat(distance, 5, 25);
        float strength = (g * m * s.m) / (distance * distance);
        force.mult(strength);

        s.applyForce(force);
    }

    public void orbit(PVector location, float radius, float speed) {
        float x = location.getX() + (radius * (float) Math.cos(theta));
        float y = location.getY() + (radius * (float) Math.sin(theta));
        setLocation(x, y);
        theta += speed;
    }

    public void applyForce(PVector force) {
        PVector f = PVector.div(force, m);
        acceleration.add(f);
    }

    //------------------------------------
    // adding event handlers
    //------------------------------------
    //-------------------------------------
    // Enable options menu for set scene
    //-------------------------------------
    public void enableOptionMenu() {
        Sprite sprite = this;
        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if(event.isSecondaryButtonDown()){
                    sprite.getOptionsMenu().show(sprite, event.getScreenX(), event.getScreenY());
                }
                event.consume();
            }
        };
        sprite.addEventHandler(MouseEvent.MOUSE_PRESSED, handler);
    }
    //-------------------------------------
    // Adding the menu items to sprite
    //-------------------------------------
    public void addOptionMouseCotrole() {
        Sprite sprite = this;
        CheckMenuItem item1 = new CheckMenuItem("Sprite becomes draggable");
        item1.setSelected(false);
        item1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                sprite.setDragable(item1.isSelected());
                if(sprite.isDragable()){
                    mouseGestures.makeDraggable(sprite);
                }else{
                    mouseGestures.makeUnDraggable(sprite);
                }
                System.out.println(sprite.isDragable());
            }
        });
        getOptionsMenu().addMenuItem(item1);
    }
    public void addOptionRemoveSprite(){
        System.out.println("Ik werkt eerst ");
        MenuItem menuItem = new MenuItem("Remove Sprite");
        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("ik werkt");
                setAlive(false);
            }
        });
        getOptionsMenu().addMenuItem(menuItem);
    }
    //------------------------------------
    // Setters
    //------------------------------------
    public void setLocation(float x, float y) {
        location.setX(x);
        location.setY(y);
    }

    public void setLocationOffset(float x, float y) {
        location.setX(location.getX() + x);
        location.setY(location.getY() + y);
    }
    public void setAlive(Boolean alive){
        this.alive = alive;
    }
    public void setDragable(boolean dragable) {
        this.dragable = dragable;
    }

    //------------------------------------
    // Getters
    //------------------------------------
    public PVector getLocation() {
        return location;
    }

    public PVector getVelocity() {
        return velocity;
    }

    public float getM() {
        return m;
    }

    public float getH() {
        return h;
    }

    public float getW() {
        return w;
    }
    public OptionsMenu getOptionsMenu(){
        return optionsMenu;
    }
    public boolean isAlive(){
        System.out.println("i am alive");
        return alive;
    }



    @Override
    public String toString() {
        return "velocity x: " + velocity.getX() + " y: " + velocity.getY() + " - location x: " + location.getX() + " y: " + location.getY() + " - acceleration x: " + acceleration.getX() + " y: " + acceleration.getY();
    }

    public boolean isDragable() {
        return this.dragable;
    }


}

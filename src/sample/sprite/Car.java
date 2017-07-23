package sample.sprite;

import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import sample.KeyInputHandler;
import sample.Layer;
import sample.PVector;
import sample.Settings;


/**
 * Created by JanWillem Huising on 23-7-2017.
 */
public class Car extends Sprite{
    public Car(Layer layer, PVector location, PVector velocity, PVector acceleration, float m){
        super(layer, location, velocity, acceleration, m);
    }
    public void drive(KeyInputHandler keyInputHandler){

        if(keyInputHandler.isUpKey()){
            if(keyInputHandler.isLeftKey()){
                angle -= Settings.DEFAULT_TURNSPEED;
            }
            if(keyInputHandler.isRightKey()){
                angle += Settings.DEFAULT_TURNSPEED;
            }
            float theta = getAngle();
            PVector f = new PVector((float)Math.cos(theta),(float)Math.sin(theta));
            f.mult(speed);
            applyForce(f);
        }
        if(keyInputHandler.isDownKey()){
            if(keyInputHandler.isLeftKey()){
                angle -= Settings.DEFAULT_TURNSPEED;
            }
            if(keyInputHandler.isRightKey()){
                angle += Settings.DEFAULT_TURNSPEED;
            }
            float theta = getAngle();
            PVector f = new PVector((float)Math.cos(theta),(float)Math.sin(theta));
            f.mult(-speed);
            applyForce(f);
        }
    }
    public Node createView(){
        Rectangle rect = new Rectangle(m*2, m/2);
        return rect;
    }
}

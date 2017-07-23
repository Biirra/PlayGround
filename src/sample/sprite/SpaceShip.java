package sample.sprite;

import javafx.scene.Node;
import sample.*;

/**
 * Created by JanWillem Huising on 10-7-2017.
 */
public class SpaceShip extends Sprite {
    public SpaceShip(Layer layer, PVector location, PVector velocity, PVector acceleration, float m){
        super(layer,location,velocity,acceleration,m);
    }
    public void glide(KeyInputHandler keyInputHandler){
        if(keyInputHandler.isUpKey()){
            thrust();
        }
        if(keyInputHandler.isLeftKey()){
            aAcceleration -= Settings.DEFAULT_TURNSPEED;
        }
        if(keyInputHandler.isRightKey()){
            aAcceleration += Settings.DEFAULT_TURNSPEED;
        }
    }
    public void thrust(){
        float theta = getAngle();
        PVector f = new PVector((float)Math.cos(theta),(float)Math.sin(theta));
        f.mult(speed);
        applyForce(f);
    }

    @Override
    public Node createView(){
        return Utils.createArrowImageView(getM()*10);
    }
}

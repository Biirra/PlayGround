package nl.janwillemhuising.model;

import javafx.scene.Node;
import nl.janwillemhuising.Utils;
import nl.janwillemhuising.model.sprite.Sprite;
import nl.janwillemhuising.view.Layer;

/**
 * Created by JanWillem Huising on 10-7-2017.
 */
public class Henry extends Sprite {
    public Henry(Layer layer, PVector location, PVector velocity, PVector acceleration, float m){
        super(layer, location,velocity,acceleration,m);
    }
    @Override
    public Node createView(){

        return Utils.createArrowImageView((int)getW());
    }
}

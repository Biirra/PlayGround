package nl.janwillemhuising.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.janwillemhuising.model.sprite.Sprite;
import nl.janwillemhuising.view.Layer;

/**
 * Created by JanWillem Huising on 10-7-2017.
 */
public class TestBall extends Sprite {
    public TestBall(Layer layer, PVector location, PVector velocity, PVector acceleration, float m){
        super(layer, location,velocity, acceleration, m);
    }
    @Override
    public Node createView() {
        Rectangle rect = new Rectangle(getM()*8, getM()*8, Color.RED);
        return rect;
    }
}

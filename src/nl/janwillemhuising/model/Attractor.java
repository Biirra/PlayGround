package nl.janwillemhuising.model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.janwillemhuising.model.sprite.Sprite;
import nl.janwillemhuising.view.Layer;

/**
 * Created by JanWillem Huising on 8-7-2017.
 */
public class Attractor extends Sprite {

    public Attractor(Layer layer, PVector location, PVector velocity, PVector acceleration, float m){
        super(layer,location,velocity,acceleration,m);
    }
    @Override
    public Node createView() {

        double radius = getW() / 2;

        Circle circle = new Circle( radius);

        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.GREEN);
        circle.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.3));

        return circle;
    }
}

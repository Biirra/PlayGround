package sample.background;

import javafx.scene.image.Image;
import sample.PVector;

/**
 * Created by JanWillem Huising on 17-7-2017.
 */
public class Grass extends Tile{
    public Grass(PVector location){
        super(location);
    }
    public Image createView(){
        return null;
    }
}

package nl.janwillemhuising.model.background;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nl.janwillemhuising.Settings;
import nl.janwillemhuising.model.PVector;

/**
 * Created by JanWillem Huising on 17-7-2017.
 */
public abstract class Tile extends ImageView{

    PVector location;
    double width;
    double height;

    public Tile(PVector location){
        this.location.set(location);
        this.width = Settings.PREFERED_TILE_WIDTH;
        this.height = Settings.PREFERED_TILE_HEIGTH;

    }
    public Tile(double width, double height, PVector location){
        this.width = width;
        this.height = height;
    }
    public abstract Image createView();
}

package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by JanWillem Huising on 7-7-2017.
 */
public class Liquid {

    float x,y,w,h;
    float c;    // coeficient

    GraphicsContext gc;

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public float getC() {
        return c;
    }

    public float getX(){
        return this.x;
    }

    public float getY() {
        return y;
    }

    public Liquid(GraphicsContext gc_, float x_, float y_, float w_, float h_, float c_){
        this.gc = gc_;
        this.x = x_;
        this.y = y_;
        this.w = w_;
        this.h = h_;
        this.c = c_;
    }

    public void display(){
        gc.setFill(Color.BLUE);
        gc.fillRect(x,y,w,h);
    }
}

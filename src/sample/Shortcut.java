package sample;

/**
 * Created by JanWillem Huising on 8-7-2017.
 */
public class Shortcut {

    public static float constrainFloat(float var, float min, float max){
        float f = var;
        if(var < min ){
            f = min;
        }else if(var > max){
            f = max;
        }
        return f;
    }
}

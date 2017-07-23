package sample;

import javafx.scene.input.KeyEvent;

/**
 * Created by JanWillem Huising on 23-7-2017.
 */
public class KeyInputHandler {
    boolean upKey = false;
    boolean downKey = false;
    boolean leftKey = false;
    boolean rightKey = false;

    public void pressKey(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                setUpKey(true);
                break;
            case DOWN:
                setDownKey(true);
                break;
            case LEFT:
                setLeftKey(true);
                break;
            case RIGHT:
                setRightKey(true);
                break;
        }
    }
    public void releaseKey(KeyEvent event){
        switch (event.getCode()){
            case UP:
                setUpKey(false);
                break;
            case DOWN:
                setDownKey(false);
                break;
            case LEFT:
                setLeftKey(false);
                break;
            case RIGHT:
                setRightKey(false);
                break;
        }
    }

    public boolean isUpKey() {
        return upKey;
    }

    public void setUpKey(boolean upKey) {
        this.upKey = upKey;
    }

    public boolean isDownKey() {
        return downKey;
    }

    public void setDownKey(boolean downKey) {
        this.downKey = downKey;
    }

    public boolean isLeftKey() {
        return leftKey;
    }

    public void setLeftKey(boolean leftKey) {
        this.leftKey = leftKey;
    }

    public boolean isRightKey() {
        return rightKey;
    }

    public void setRightKey(boolean rightKey) {
        this.rightKey = rightKey;
    }


}

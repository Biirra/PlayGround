package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import sample.sprite.Sprite;

import java.util.*;

/**
 * Created by JanWillem Huising on 15-7-2017.
 */
public class OptionsMenu extends ContextMenu{
    ArrayList<MenuItem> optionList = new ArrayList<MenuItem>();

    public OptionsMenu() {

    }
    public ContextMenu getContextMenu(){
        return this;
    }
    public void addMenuItem(MenuItem item){
        this.getItems().clear();
        optionList.add(item);
        optionList.forEach(MenuItem ->{
            this.getItems().add(MenuItem);
        });
    }


}

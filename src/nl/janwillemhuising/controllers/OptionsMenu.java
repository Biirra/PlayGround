package nl.janwillemhuising.controllers;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

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

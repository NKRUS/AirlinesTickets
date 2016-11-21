package ru.nk.tickets.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by NK on 13.11.2016.
 */
public class PlaceClass {
    private IntegerProperty class_id;
    private StringProperty class_title;

    public PlaceClass(){
        class_id = new SimpleIntegerProperty();
        class_title = new SimpleStringProperty();
    }

    public int getClass_id() {
        return class_id.get();
    }

    public IntegerProperty class_idProperty() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id.set(class_id);
    }

    public String getClass_title() {
        return class_title.get();
    }

    public StringProperty class_titleProperty() {
        return class_title;
    }

    public void setClass_title(String class_title) {
        this.class_title.set(class_title);
    }


    @Override
    public String toString() {
        return getClass_title();
    }
}

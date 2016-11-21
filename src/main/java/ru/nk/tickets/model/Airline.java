package ru.nk.tickets.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by NK on 13.11.2016.
 */
public class Airline {
    private IntegerProperty airline_id;
    private StringProperty name;
    private StringProperty short_name;

    public Airline(){
        airline_id = new SimpleIntegerProperty();
        name = new SimpleStringProperty();
        short_name = new SimpleStringProperty();
    }

    public int getAirline_id() {
        return airline_id.get();
    }

    public IntegerProperty airline_idProperty() {
        return airline_id;
    }

    public void setAirline_id(int airline_id) {
        this.airline_id.set(airline_id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getShort_name() {
        return short_name.get();
    }

    public StringProperty short_nameProperty() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name.set(short_name);
    }
}

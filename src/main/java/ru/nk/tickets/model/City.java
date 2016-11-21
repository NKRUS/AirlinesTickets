package ru.nk.tickets.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by NK on 13.11.2016.
 */
public class City {
    private IntegerProperty city_id;
    private StringProperty city;

    public City(){
        city_id = new SimpleIntegerProperty();
        city = new SimpleStringProperty();
    }

    public int getCity_id() {
        return city_id.get();
    }

    public IntegerProperty city_idProperty() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id.set(city_id);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    @Override
    public String toString() {
        return getCity();
    }
}

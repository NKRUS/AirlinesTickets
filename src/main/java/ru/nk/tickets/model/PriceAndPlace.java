package ru.nk.tickets.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by NK on 22.11.2016.
 */
public class PriceAndPlace {
    private IntegerProperty flight_id;
    private IntegerProperty class_id;
    private DoubleProperty price;
    private IntegerProperty free_places_count;

    public PriceAndPlace(){
        flight_id = new SimpleIntegerProperty();
        class_id = new SimpleIntegerProperty();
        price = new SimpleDoubleProperty();
        free_places_count = new SimpleIntegerProperty();
    }

    public int getFlight_id() {
        return flight_id.get();
    }

    public IntegerProperty flight_idProperty() {
        return flight_id;
    }

    public void setFlight_id(int flight_id) {
        this.flight_id.set(flight_id);
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

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public int getFree_places_count() {
        return free_places_count.get();
    }

    public IntegerProperty free_places_countProperty() {
        return free_places_count;
    }

    public void setFree_places_count(int free_places_count) {
        this.free_places_count.set(free_places_count);
    }

    @Override
    public String toString() {
        return "PriceAndPlace{" +
                "flight_id=" + flight_id +
                ", class_id=" + class_id +
                ", price=" + price +
                ", free_places_count=" + free_places_count +
                '}';
    }
}

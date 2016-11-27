package ru.nk.tickets.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by NK on 27.11.2016.
 */
public class SoldTickets {
    private SimpleObjectProperty<Flight> flight;
    private IntegerProperty class_id;
    private IntegerProperty sold_tickets;
    private IntegerProperty free_places_count;


    public SoldTickets() {
        flight = new SimpleObjectProperty<>();
        class_id = new SimpleIntegerProperty();
        sold_tickets = new SimpleIntegerProperty();
        free_places_count = new SimpleIntegerProperty();
    }

    public Flight getFlight() {
        return flight.get();
    }

    public SimpleObjectProperty<Flight> flightProperty() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight.set(flight);
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

    public int getSold_tickets() {
        return sold_tickets.get();
    }

    public IntegerProperty sold_ticketsProperty() {
        return sold_tickets;
    }

    public void setSold_tickets(int sold_tickets) {
        this.sold_tickets.set(sold_tickets);
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
}

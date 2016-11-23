package ru.nk.tickets.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Created by NK on 22.11.2016.
 */
public class OrderDetails {
    private IntegerProperty id;
    private IntegerProperty order_id;
    private IntegerProperty flight_id;
    private IntegerProperty class_id;
    private IntegerProperty number_of_tickets;

    public OrderDetails(){
        id = new SimpleIntegerProperty();
        order_id = new SimpleIntegerProperty();
        flight_id = new SimpleIntegerProperty();
        class_id = new SimpleIntegerProperty();
        number_of_tickets = new SimpleIntegerProperty();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getOrder_id() {
        return order_id.get();
    }

    public IntegerProperty order_idProperty() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id.set(order_id);
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

    public int getNumber_of_tickets() {
        return number_of_tickets.get();
    }

    public IntegerProperty number_of_ticketsProperty() {
        return number_of_tickets;
    }

    public void setNumber_of_tickets(int number_of_tickets) {
        this.number_of_tickets.set(number_of_tickets);
    }
}

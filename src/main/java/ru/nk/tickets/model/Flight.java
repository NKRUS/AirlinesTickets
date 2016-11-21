package ru.nk.tickets.model;

import javafx.beans.property.*;

import java.sql.Date;
import java.sql.Time;

/**
 * Created by NK on 13.11.2016.
 */
public class Flight {
    private IntegerProperty flight_id;
    private StringProperty flight_number;
    private IntegerProperty airlines_id;
    private IntegerProperty departure_city_id;
    private IntegerProperty arrival_city_id;
    private SimpleObjectProperty<Date> departure_date;
    private SimpleObjectProperty<Time> departure_time;
    private SimpleObjectProperty<Date> arrival_date;
    private SimpleObjectProperty<Time> arrival_time;

    public Flight(){
        flight_id = new SimpleIntegerProperty();
        flight_number = new SimpleStringProperty();
        airlines_id = new SimpleIntegerProperty();
        departure_city_id = new SimpleIntegerProperty();
        arrival_city_id = new SimpleIntegerProperty();
        departure_date = new SimpleObjectProperty<Date>();
        departure_time = new SimpleObjectProperty<Time>();
        arrival_date = new SimpleObjectProperty<Date>();
        arrival_time = new SimpleObjectProperty<Time>();
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

    public String getFlight_number() {
        return flight_number.get();
    }

    public StringProperty flight_numberProperty() {
        return flight_number;
    }

    public void setFlight_number(String flight_number) {
        this.flight_number.set(flight_number);
    }

    public int getAirlines_id() {
        return airlines_id.get();
    }

    public IntegerProperty airlines_idProperty() {
        return airlines_id;
    }

    public void setAirlines_id(int airlines_id) {
        this.airlines_id.set(airlines_id);
    }

    public int getDeparture_city_id() {
        return departure_city_id.get();
    }

    public IntegerProperty departure_city_idProperty() {
        return departure_city_id;
    }

    public void setDeparture_city_id(int departure_city_id) {
        this.departure_city_id.set(departure_city_id);
    }

    public int getArrival_city_id() {
        return arrival_city_id.get();
    }

    public IntegerProperty arrival_city_idProperty() {
        return arrival_city_id;
    }

    public void setArrival_city_id(int arrival_city_id) {
        this.arrival_city_id.set(arrival_city_id);
    }

    public Date getDeparture_date() {
        return departure_date.get();
    }

    public SimpleObjectProperty<Date> departure_dateProperty() {
        return departure_date;
    }

    public void setDeparture_date(Date departure_date) {
        this.departure_date.set(departure_date);
    }

    public Time getDeparture_time() {
        return departure_time.get();
    }

    public SimpleObjectProperty<Time> departure_timeProperty() {
        return departure_time;
    }

    public void setDeparture_time(Time departure_time) {
        this.departure_time.set(departure_time);
    }

    public Date getArrival_date() {
        return arrival_date.get();
    }

    public SimpleObjectProperty<Date> arrival_dateProperty() {
        return arrival_date;
    }

    public void setArrival_date(Date arrival_date) {
        this.arrival_date.set(arrival_date);
    }

    public Time getArrival_time() {
        return arrival_time.get();
    }

    public SimpleObjectProperty<Time> arrival_timeProperty() {
        return arrival_time;
    }

    public void setArrival_time(Time arrival_time) {
        this.arrival_time.set(arrival_time);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "flight_id=" + flight_id +
                ", flight_number=" + flight_number +
                ", airlines_id=" + airlines_id +
                ", departure_city_id=" + departure_city_id +
                ", arrival_city_id=" + arrival_city_id +
                ", departure_date=" + departure_date +
                ", departure_time=" + departure_time +
                ", arrival_date=" + arrival_date +
                ", arrival_time=" + arrival_time +
                '}';
    }
}

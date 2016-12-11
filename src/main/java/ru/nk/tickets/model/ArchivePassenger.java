package ru.nk.tickets.model;

import javafx.beans.property.*;

import java.sql.Date;

/**
 * Created by NK on 10.12.2016.
 */
public class ArchivePassenger {
    private SimpleObjectProperty<Passenger> passenger;
    private StringProperty flight_number;
    private StringProperty airline;
    private StringProperty departure_city;
    private StringProperty arrival_city;
    private SimpleObjectProperty<Date> departure_date;

    public ArchivePassenger() {
        passenger = new SimpleObjectProperty<>();
        flight_number = new SimpleStringProperty();
        airline = new SimpleStringProperty();
        departure_city = new SimpleStringProperty();
        arrival_city = new SimpleStringProperty();
        departure_date = new SimpleObjectProperty<>();
    }

    public Passenger getPassenger() {
        return passenger.get();
    }

    public SimpleObjectProperty<Passenger> passengerProperty() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger.set(passenger);
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

    public String getAirline() {
        return airline.get();
    }

    public StringProperty airlineProperty() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline.set(airline);
    }

    public String getDeparture_city() {
        return departure_city.get();
    }

    public StringProperty departure_cityProperty() {
        return departure_city;
    }

    public void setDeparture_city(String departure_city) {
        this.departure_city.set(departure_city);
    }

    public String getArrival_city() {
        return arrival_city.get();
    }

    public StringProperty arrival_cityProperty() {
        return arrival_city;
    }

    public void setArrival_city(String arrival_city) {
        this.arrival_city.set(arrival_city);
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
}
